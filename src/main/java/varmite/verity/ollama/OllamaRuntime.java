/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import varmite.verity.ollama.OllamaApi;

public final class OllamaRuntime {
    private static Process serverProcess;
    private static boolean shutdownHookAdded;

    private OllamaRuntime() {
    }

    public static Os os() {
        String name = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (name.contains("win")) {
            return Os.WINDOWS;
        }
        if (name.contains("mac") || name.contains("darwin")) {
            return Os.MAC;
        }
        if (name.contains("linux") || name.contains("nux") || name.contains("bsd")) {
            return Os.LINUX;
        }
        return Os.OTHER;
    }

    public static String arch() {
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        if (arch.contains("aarch64") || arch.contains("arm64")) {
            return "arm64";
        }
        if (arch.contains("amd64") || arch.contains("x86_64") || arch.equals("x64")) {
            return "amd64";
        }
        return arch;
    }

    public static Path managedDir() {
        String home = System.getProperty("user.home", ".");
        Path base = switch (OllamaRuntime.os()) {
            case Os.WINDOWS -> {
                String local = System.getenv("LOCALAPPDATA");
                if (local != null && !local.isBlank()) {
                    yield Paths.get(local, new String[0]);
                }
                yield Paths.get(home, "AppData", "Local");
            }
            case Os.MAC -> Paths.get(home, "Library", "Application Support");
            default -> {
                String xdg = System.getenv("XDG_DATA_HOME");
                if (xdg != null && !xdg.isBlank()) {
                    yield Paths.get(xdg, new String[0]);
                }
                yield Paths.get(home, ".local", "share");
            }
        };
        return base.resolve("Verity").resolve("ollama");
    }

    static Path managedInstallDir() {
        return OllamaRuntime.managedDir().resolve("current");
    }

    public static String executableName() {
        return OllamaRuntime.os() == Os.WINDOWS ? "ollama.exe" : "ollama";
    }

    static Optional<Path> executableIn(Path installDir) {
        for (Path candidate : List.of(installDir.resolve(OllamaRuntime.executableName()), installDir.resolve("bin").resolve(OllamaRuntime.executableName()))) {
            if (!Files.isRegularFile(candidate, new LinkOption[0])) continue;
            return Optional.of(candidate);
        }
        return Optional.empty();
    }

    public static boolean hasManagedInstall() {
        return OllamaRuntime.executableIn(OllamaRuntime.managedInstallDir()).isPresent();
    }

    public static Optional<Path> findExecutable() {
        Optional<Path> managed = OllamaRuntime.executableIn(OllamaRuntime.managedInstallDir());
        if (managed.isPresent()) {
            return managed;
        }
        for (Path candidate : OllamaRuntime.candidates()) {
            if (!Files.isRegularFile(candidate, new LinkOption[0]) || OllamaRuntime.os() != Os.WINDOWS && !Files.isExecutable(candidate)) continue;
            return Optional.of(candidate);
        }
        return Optional.empty();
    }

    private static List<Path> candidates() {
        ArrayList<Path> list = new ArrayList<Path>();
        String path = System.getenv("PATH");
        if (path != null) {
            for (String dir : path.split(File.pathSeparator)) {
                if (dir.isBlank()) continue;
                try {
                    list.add(Paths.get(dir.trim().replace("\"", ""), new String[0]).resolve(OllamaRuntime.executableName()));
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
            }
        }
        String home = System.getProperty("user.home", ".");
        switch (OllamaRuntime.os()) {
            case WINDOWS: {
                String programFiles;
                String local = System.getenv("LOCALAPPDATA");
                if (local != null) {
                    list.add(Paths.get(local, "Programs", "Ollama", "ollama.exe"));
                }
                if ((programFiles = System.getenv("ProgramFiles")) == null) break;
                list.add(Paths.get(programFiles, "Ollama", "ollama.exe"));
                break;
            }
            case MAC: {
                list.add(Paths.get("/Applications/Ollama.app/Contents/Resources/ollama", new String[0]));
                list.add(Paths.get(home, "Applications", "Ollama.app", "Contents", "Resources", "ollama"));
                list.add(Paths.get("/opt/homebrew/bin/ollama", new String[0]));
                list.add(Paths.get("/usr/local/bin/ollama", new String[0]));
                break;
            }
            default: {
                list.add(Paths.get("/usr/local/bin/ollama", new String[0]));
                list.add(Paths.get("/usr/bin/ollama", new String[0]));
                list.add(Paths.get(home, ".local", "bin", "ollama"));
                list.add(Paths.get("/snap/bin/ollama", new String[0]));
            }
        }
        return list;
    }

    public static boolean isLocalEndpoint(String baseUrl) {
        try {
            String host = URI.create(OllamaApi.normalize(baseUrl)).getHost();
            return host == null || host.equals("127.0.0.1") || host.equalsIgnoreCase("localhost") || host.equals("0.0.0.0") || host.equals("::1");
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isRunning(String baseUrl) {
        return OllamaApi.version(baseUrl, Duration.ofSeconds(2L)).isPresent();
    }

    public static synchronized boolean ensureRunning(String baseUrl, Duration wait) {
        if (OllamaRuntime.isRunning(baseUrl)) {
            return true;
        }
        if (!OllamaRuntime.isLocalEndpoint(baseUrl)) {
            return false;
        }
        Optional<Path> exe = OllamaRuntime.findExecutable();
        if (exe.isEmpty()) {
            return false;
        }
        try {
            OllamaRuntime.start(exe.get(), baseUrl);
        }
        catch (IOException e) {
            return false;
        }
        long deadline = System.currentTimeMillis() + wait.toMillis();
        while (System.currentTimeMillis() < deadline) {
            if (OllamaRuntime.isRunning(baseUrl)) {
                return true;
            }
            if (serverProcess != null && !serverProcess.isAlive()) {
                return OllamaRuntime.isRunning(baseUrl);
            }
            try {
                Thread.sleep(400L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return OllamaRuntime.isRunning(baseUrl);
    }

    private static void start(Path exe, String baseUrl) throws IOException {
        int port;
        Files.createDirectories(OllamaRuntime.managedDir(), new FileAttribute[0]);
        ProcessBuilder builder = new ProcessBuilder(exe.toAbsolutePath().toString(), "serve");
        builder.directory(exe.getParent().toFile());
        URI uri = URI.create(OllamaApi.normalize(baseUrl));
        int n = port = uri.getPort() == -1 ? 11434 : uri.getPort();
        if (port != 11434) {
            builder.environment().put("OLLAMA_HOST", "127.0.0.1:" + port);
        }
        builder.redirectErrorStream(true);
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(OllamaRuntime.managedDir().resolve("server.log").toFile()));
        serverProcess = builder.start();
        if (!shutdownHookAdded) {
            shutdownHookAdded = true;
            Runtime.getRuntime().addShutdownHook(new Thread(OllamaRuntime::stopStartedServer, "Verity-Ollama-Shutdown"));
        }
    }

    public static synchronized boolean startedByVerity() {
        return serverProcess != null && serverProcess.isAlive();
    }

    public static synchronized void stopStartedServer() {
        Process process = serverProcess;
        serverProcess = null;
        if (process == null || !process.isAlive()) {
            return;
        }
        process.descendants().forEach(ProcessHandle::destroy);
        process.destroy();
    }

    public static synchronized void stopManagedServers() {
        OllamaRuntime.stopStartedServer();
        Path dir = OllamaRuntime.managedInstallDir().toAbsolutePath().normalize();
        List<ProcessHandle> running = ProcessHandle.allProcesses().filter(p -> OllamaRuntime.runsFrom(p, dir)).toList();
        running.forEach(ProcessHandle::destroy);
        long deadline = System.currentTimeMillis() + 10000L;
        for (ProcessHandle p2 : running) {
            try {
                p2.onExit().get(Math.max(1L, deadline - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
            }
            catch (TimeoutException e) {
                p2.destroyForcibly();
            }
            catch (ExecutionException e) {
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private static boolean runsFrom(ProcessHandle process, Path dir) {
        try {
            return process.info().command().map(c -> Paths.get(c, new String[0]).toAbsolutePath().normalize().startsWith(dir)).orElse(false);
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public static enum Os {
        WINDOWS,
        MAC,
        LINUX,
        OTHER;

    }
}

