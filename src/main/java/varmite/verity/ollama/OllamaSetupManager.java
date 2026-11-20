/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import varmite.verity.Verity;
import varmite.verity.VerityConfig;
import varmite.verity.ollama.OllamaApi;
import varmite.verity.ollama.OllamaInstaller;
import varmite.verity.ollama.OllamaModels;
import varmite.verity.ollama.OllamaRuntime;
import varmite.verity.types.AiProvider;

public final class OllamaSetupManager {
    private static final Snapshot IDLE = new Snapshot(Phase.IDLE, "", "", 0L, 0L, 0.0, 0, 0L);
    private static final ExecutorService WORKER = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "Verity-Ollama-Setup");
        t.setDaemon(true);
        return t;
    });
    private static final AtomicReference<Snapshot> STATE = new AtomicReference<Snapshot>(IDLE);
    private static final AtomicBoolean CANCEL = new AtomicBoolean(false);
    private static final AtomicInteger QUEUED = new AtomicInteger(0);
    private static final AtomicReference<String> ACTIVE_MODEL = new AtomicReference<>(null);
    private static volatile Consumer<Runnable> mainThread = Runnable::run;
    private static volatile Consumer<Snapshot> onFinished = s -> {};
    private static long sampleBytes;
    private static long sampleNanos;
    private static double smoothedSpeed;

    private OllamaSetupManager() {
    }

    public static void setMainThreadExecutor(Consumer<Runnable> executor) {
        mainThread = executor;
    }

    public static void setOnFinished(Consumer<Snapshot> listener) {
        onFinished = listener;
    }

    public static Snapshot snapshot() {
        return STATE.get();
    }

    public static boolean isBusy() {
        return STATE.get().phase() == Phase.RUNNING || QUEUED.get() > 0;
    }

    public static Optional<String> activeModel() {
        return Optional.ofNullable(ACTIVE_MODEL.get());
    }

    public static void cancel() {
        CANCEL.set(true);
    }

    public static String endpoint() {
        return OllamaApi.normalize(Optional.ofNullable((String)VerityConfig.AI_ENDPOINT.get()).filter(s -> !s.isBlank()).orElse(AiProvider.OLLAMA.getDefaultUrl()));
    }

    public static void installOllama() {
        OllamaSetupManager.submit("Setting up Ollama", OllamaSetupManager::ensureOllama);
    }

    public static void downloadModel(String tag) {
        ACTIVE_MODEL.set(tag);
        OllamaSetupManager.submit("Downloading " + tag, () -> {
            ACTIVE_MODEL.set(tag);
            OllamaSetupManager.ensureOllama();
            String url = OllamaSetupManager.endpoint();
            OllamaSetupManager.update("Downloading " + tag, "Contacting Ollama", 0L, 0L);
            OllamaSetupManager.resetSpeed();
            OllamaApi.pull(url, tag, (status, done, total) -> OllamaSetupManager.update("Downloading " + tag, OllamaSetupManager.describePull(status), done, total), CANCEL::get);
            OllamaModels.invalidate(url);
            OllamaModels.installed(url, true);
            mainThread.accept(() -> OllamaSetupManager.selectModel(tag));
            OllamaSetupManager.update(tag + " is ready", "Selected as Verity's AI model", 1L, 1L);
        });
    }

    public static void deleteModel(String tag) {
        ACTIVE_MODEL.set(tag);
        OllamaSetupManager.submit("Deleting " + tag, () -> {
            ACTIVE_MODEL.set(tag);
            String url = OllamaSetupManager.endpoint();
            OllamaSetupManager.removeModel(url, tag, "Deleting " + tag, "Removing model files");
            OllamaModels.invalidate(url);
            OllamaModels.installed(url, true);
            OllamaSetupManager.update(tag + " deleted", "Removed from this computer", 1L, 1L);
        });
    }

    public static void uninstallOllama(boolean deleteModels) {
        OllamaSetupManager.submit("Removing Ollama", () -> {
            String url = OllamaSetupManager.endpoint();
            if (!OllamaRuntime.hasManagedInstall()) {
                throw new IllegalStateException("Verity did not install the Ollama on this computer. Remove it with its own uninstaller.");
            }
            if (deleteModels) {
                if (!OllamaRuntime.isLocalEndpoint(url)) {
                    throw new IllegalStateException("The AI URL points to another computer (" + url + "); its models were not touched.");
                }
                OllamaSetupManager.update("Removing Ollama", "Starting Ollama to delete the models", 0L, 0L);
                if (!OllamaRuntime.ensureRunning(url, Duration.ofSeconds(45L))) {
                    throw new IllegalStateException("Ollama did not start, so its models could not be deleted. Nothing was removed.");
                }
                List<OllamaApi.InstalledModel> models = OllamaApi.listModels(url, Duration.ofSeconds(10L));
                for (int i = 0; i < models.size(); ++i) {
                    String name = models.get(i).name();
                    OllamaSetupManager.removeModel(url, name, "Removing Ollama", "Deleting " + name + " (" + (i + 1) + "/" + models.size() + ")");
                }
            }
            OllamaSetupManager.update("Removing Ollama", "Stopping the Ollama server", 0L, 0L);
            OllamaRuntime.stopManagedServers();
            OllamaSetupManager.update("Removing Ollama", "Deleting program files", 0L, 0L);
            OllamaInstaller.uninstall();
            OllamaModels.invalidate(url);
            OllamaSetupManager.update("Ollama removed", deleteModels ? "The program and all models were deleted" : "Downloaded models were kept for any other Ollama", 1L, 1L);
        });
    }

    private static void removeModel(String url, String tag, String title, String detail) throws Exception {
        OllamaSetupManager.update(title, detail, 0L, 0L);
        try {
            OllamaApi.unload(url, tag);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        OllamaApi.delete(url, tag);
        mainThread.accept(() -> {
            if (VerityConfig.AI_PROVIDER.get() == AiProvider.OLLAMA && OllamaModels.withTag((String)VerityConfig.AI_MODEL.get()).equalsIgnoreCase(OllamaModels.withTag(tag))) {
                VerityConfig.AI_MODEL.set("");
                VerityConfig.SPEC.save();
            }
        });
    }

    public static void selectModel(String tag) {
        VerityConfig.AI_PROVIDER.set(AiProvider.OLLAMA);
        VerityConfig.AI_MODEL.set(tag);
        VerityConfig.SPEC.save();
    }

    private static void submit(String title, Job job) {
        QUEUED.incrementAndGet();
        if (STATE.get().phase() != Phase.RUNNING) {
            STATE.set(new Snapshot(Phase.RUNNING, title, "Waiting", 0L, 0L, 0.0, QUEUED.get() - 1, 0L));
        }
        WORKER.submit(() -> {
            Snapshot result;
            QUEUED.decrementAndGet();
            CANCEL.set(false);
            STATE.set(new Snapshot(Phase.RUNNING, title, "Starting", 0L, 0L, 0.0, QUEUED.get(), 0L));
            try {
                job.run();
                Snapshot last = STATE.get();
                result = new Snapshot(Phase.DONE, last.title(), last.detail(), Math.max(last.done(), 1L), Math.max(last.total(), 1L), 0.0, QUEUED.get(), System.currentTimeMillis());
            }
            catch (InterruptedIOException e) {
                result = new Snapshot(Phase.CANCELLED, title, "Cancelled", 0L, 0L, 0.0, QUEUED.get(), System.currentTimeMillis());
            }
            catch (Exception e) {
                Verity.LOGGER.error("[Verity Ollama] {} failed", (Object)title, (Object)e);
                String message = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
                result = new Snapshot(Phase.FAILED, title + " failed", message, 0L, 0L, 0.0, QUEUED.get(), System.currentTimeMillis());
            }
            finally {
                if (QUEUED.get() == 0) {
                    ACTIVE_MODEL.set(null);
                }
            }
            STATE.set(result);
            Snapshot finished = result;
            mainThread.accept(() -> onFinished.accept(finished));
        });
    }

    private static void ensureOllama() throws Exception {
        String url = OllamaSetupManager.endpoint();
        OllamaSetupManager.update("Setting up Ollama", "Looking for Ollama", 0L, 0L);
        if (OllamaRuntime.isRunning(url)) {
            return;
        }
        if (!OllamaRuntime.isLocalEndpoint(url)) {
            throw new IllegalStateException("The AI URL points to another computer (" + url + "). Start Ollama there, or clear the AI URL.");
        }
        if (OllamaRuntime.findExecutable().isEmpty()) {
            OllamaSetupManager.resetSpeed();
            OllamaInstaller.install(new OllamaInstaller.Listener(){
                private String stage = "";

                @Override
                public void stage(String text) {
                    this.stage = text;
                    OllamaSetupManager.resetSpeed();
                    Snapshot s = STATE.get();
                    OllamaSetupManager.update("Installing Ollama", text, s.done(), s.total());
                }

                @Override
                public void bytes(long done, long total) {
                    OllamaSetupManager.update("Installing Ollama", this.stage, done, total);
                }
            }, CANCEL::get);
        }
        OllamaSetupManager.update("Starting Ollama", "Launching the Ollama server", 0L, 0L);
        if (!OllamaRuntime.ensureRunning(url, Duration.ofSeconds(45L))) {
            throw new IllegalStateException("Ollama is installed but its server did not start. See " + String.valueOf(OllamaRuntime.managedDir().resolve("server.log")));
        }
    }

    private static String describePull(String status) {
        if (status == null || status.isBlank()) {
            return "Downloading";
        }
        if (status.startsWith("pulling ") && !status.equals("pulling manifest")) {
            return "Downloading model files";
        }
        return switch (status) {
            case "pulling manifest" -> "Reading model manifest";
            case "verifying sha256 digest" -> "Verifying download";
            case "writing manifest" -> "Finishing up";
            case "removing any unused layers", "removing unused layers" -> "Cleaning up";
            case "success" -> "Done";
            default -> Character.toUpperCase(status.charAt(0)) + status.substring(1);
        };
    }

    private static synchronized void resetSpeed() {
        sampleBytes = -1L;
        sampleNanos = 0L;
        smoothedSpeed = 0.0;
    }

    private static synchronized void update(String title, String detail, long done, long total) {
        long now = System.nanoTime();
        if (sampleBytes < 0L || done < sampleBytes) {
            sampleBytes = done;
            sampleNanos = now;
        } else if (now - sampleNanos >= 400000000L) {
            double seconds = (double)(now - sampleNanos) / 1.0E9;
            double instant = (double)(done - sampleBytes) / seconds;
            smoothedSpeed = smoothedSpeed <= 0.0 ? instant : smoothedSpeed * 0.7 + instant * 0.3;
            sampleBytes = done;
            sampleNanos = now;
        }
        STATE.set(new Snapshot(Phase.RUNNING, title, detail, done, total, smoothedSpeed, QUEUED.get(), 0L));
    }

    public record Snapshot(Phase phase, String title, String detail, long done, long total, double bytesPerSecond, int queued, long finishedAtMs) {
        public float fraction() {
            return this.total > 0L ? (float)Math.min(1.0, Math.max(0.0, (double)this.done / (double)this.total)) : -1.0f;
        }

        public long etaSeconds() {
            return this.total > 0L && this.bytesPerSecond > 1.0 ? (long)((double)(this.total - this.done) / this.bytesPerSecond) : -1L;
        }
    }

    public static enum Phase {
        IDLE,
        RUNNING,
        DONE,
        FAILED,
        CANCELLED;

    }

    private static interface Job {
        public void run() throws Exception;
    }
}

