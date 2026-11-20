/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import varmite.verity.ollama.ArchiveExtractor;
import varmite.verity.ollama.OllamaRuntime;

public final class OllamaInstaller {
    private static final String RELEASES = "https://github.com/ollama/ollama/releases";
    private static final Pattern VERSION_IN_TAG_URL = Pattern.compile("/releases/tag/(v[^/?#]+)");
    private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15L)).followRedirects(HttpClient.Redirect.NORMAL).build();
    private static final HttpClient NO_REDIRECTS = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15L)).followRedirects(HttpClient.Redirect.NEVER).build();

    private OllamaInstaller() {
    }

    public static String assetName() throws IOException {
        String arch = OllamaRuntime.arch();
        return switch (OllamaRuntime.os()) {
            default -> throw new IncompatibleClassChangeError();
            case OllamaRuntime.Os.WINDOWS -> {
                switch (arch) {
                    case "amd64": 
                    case "arm64": {
                        yield "ollama-windows-" + arch + ".zip";
                    }
                }
                throw OllamaInstaller.unsupported(arch);
            }
            case OllamaRuntime.Os.MAC -> "ollama-darwin.tgz";
            case OllamaRuntime.Os.LINUX -> {
                switch (arch) {
                    case "amd64": 
                    case "arm64": {
                        yield "ollama-linux-" + arch + ".tar.zst";
                    }
                }
                throw OllamaInstaller.unsupported(arch);
            }
            case OllamaRuntime.Os.OTHER -> throw new IOException("Ollama has no build for " + System.getProperty("os.name") + ".");
        };
    }

    private static IOException unsupported(String arch) {
        return new IOException("Ollama has no build for " + System.getProperty("os.name") + " on " + arch + " processors.");
    }

    public static Path install(Listener listener, BooleanSupplier cancelled) throws IOException {
        String asset = OllamaInstaller.assetName();
        Path root = OllamaRuntime.managedDir();
        Path downloads = root.resolve("downloads");
        Files.createDirectories(downloads, new FileAttribute[0]);
        listener.stage("Finding the latest Ollama release");
        Release release = OllamaInstaller.latestRelease(asset);
        long needed = release.size > 0L ? release.size * 7L / 2L : 0x180000000L;
        long free = Files.getFileStore(downloads).getUsableSpace();
        if (free < needed) {
            throw new IOException(String.format(Locale.ROOT, "Not enough disk space: Ollama needs about %.1f GB free, you have %.1f GB.", (double)needed / 1.0E9, (double)free / 1.0E9));
        }
        Path archive = downloads.resolve(asset);
        if (!Files.isRegularFile(archive, new LinkOption[0]) || release.sha256 == null || !OllamaInstaller.sha256(archive, null, cancelled).equals(release.sha256)) {
            listener.stage("Downloading Ollama " + release.version);
            OllamaInstaller.download(release.url, archive, release.size, listener, cancelled);
            if (release.sha256 != null) {
                listener.stage("Verifying download");
                String actual = OllamaInstaller.sha256(archive, listener, cancelled);
                if (!actual.equalsIgnoreCase(release.sha256)) {
                    Files.deleteIfExists(archive);
                    throw new IOException("The Ollama download was corrupted (checksum mismatch). Please try again.");
                }
            }
        }
        listener.stage("Unpacking Ollama");
        Path staging = root.resolve("staging");
        OllamaInstaller.deleteTree(staging);
        ArchiveExtractor.extract(archive, staging, listener::bytes, cancelled);
        if (OllamaRuntime.executableIn(staging).isEmpty()) {
            throw new IOException("The Ollama archive did not contain " + OllamaRuntime.executableName() + ".");
        }
        OllamaRuntime.stopStartedServer();
        Path current = OllamaRuntime.managedInstallDir();
        OllamaInstaller.deleteTree(current);
        Files.move(staging, current, StandardCopyOption.ATOMIC_MOVE);
        Files.writeString(root.resolve("version.txt"), (CharSequence)release.version, new OpenOption[0]);
        Files.deleteIfExists(archive);
        return OllamaRuntime.executableIn(current).orElseThrow(() -> new IOException("Ollama was unpacked but the executable is missing."));
    }

    public static void uninstall() throws IOException {
        Path root = OllamaRuntime.managedDir();
        int attempt = 1;
        while (true) {
            try {
                OllamaInstaller.deleteTree(root);
                return;
            }
            catch (IOException e) {
                if (attempt >= 5) {
                    throw new IOException("Could not delete " + String.valueOf(root) + " - close anything using Ollama and try again. (" + e.getMessage() + ")", e);
                }
                OllamaInstaller.sleep(500L * (long)attempt);
                ++attempt;
            }
        }
    }

    private static Release latestRelease(String asset) throws IOException {
        Matcher m;
        String version = null;
        HttpResponse<Void> latest = OllamaInstaller.send(NO_REDIRECTS, HttpRequest.newBuilder(URI.create("https://github.com/ollama/ollama/releases/latest")).timeout(Duration.ofSeconds(30L)).method("HEAD", HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.discarding());
        Optional<String> location = latest.headers().firstValue("Location");
        if (location.isPresent() && (m = VERSION_IN_TAG_URL.matcher(location.get())).find()) {
            version = m.group(1);
        }
        Object base = version != null ? "https://github.com/ollama/ollama/releases/download/" + version + "/" : "https://github.com/ollama/ollama/releases/latest/download/";
        String sha = null;
        HttpResponse<String> sums = OllamaInstaller.send(HTTP, HttpRequest.newBuilder(URI.create((String)base + "sha256sum.txt")).timeout(Duration.ofSeconds(30L)).GET().build(), HttpResponse.BodyHandlers.ofString());
        if (sums.statusCode() == 200) {
            for (String line : sums.body().split("\\R")) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 2 || !parts[1].replace("./", "").replace("*", "").equals(asset)) continue;
                sha = parts[0].toLowerCase(Locale.ROOT);
            }
        }
        String url = (String)base + asset;
        long size = -1L;
        HttpResponse<Void> head = OllamaInstaller.send(HTTP, HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(30L)).method("HEAD", HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.discarding());
        if (head.statusCode() == 200) {
            size = head.headers().firstValueAsLong("Content-Length").orElse(-1L);
        } else if (head.statusCode() == 404) {
            throw new IOException("This Ollama release has no " + asset + " download.");
        }
        return new Release(version != null ? version : "latest", url, size, sha);
    }

    private static void download(String url, Path target, long expectedSize, Listener listener, BooleanSupplier cancelled) throws IOException {
        Path part = target.resolveSibling(String.valueOf(target.getFileName()) + ".part");
        IOException last = null;
        for (int attempt = 1; attempt <= 5; ++attempt) {
            long have;
            long l = have = Files.isRegularFile(part, new LinkOption[0]) ? Files.size(part) : 0L;
            if (expectedSize > 0L && have >= expectedSize) break;
            HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(url)).GET();
            if (have > 0L) {
                request.header("Range", "bytes=" + have + "-");
            }
            try {
                boolean resumed;
                HttpResponse<InputStream> response = OllamaInstaller.send(HTTP, request.build(), HttpResponse.BodyHandlers.ofInputStream());
                int status = response.statusCode();
                boolean bl = resumed = status == 206;
                if (status != 200 && !resumed) {
                    response.body().close();
                    throw new IOException("Download failed: HTTP " + status);
                }
                if (!resumed) {
                    have = 0L;
                }
                long total = expectedSize > 0L ? expectedSize : response.headers().firstValueAsLong("Content-Length").orElse(-1L) + have;
                try (InputStream in = response.body();
                     OutputStream out = Files.newOutputStream(part, StandardOpenOption.CREATE, resumed ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);){
                    int n;
                    byte[] buffer = new byte[65536];
                    long done = have;
                    long lastReport = 0L;
                    while ((n = in.read(buffer)) >= 0) {
                        if (cancelled.getAsBoolean()) {
                            throw new InterruptedIOException("Cancelled");
                        }
                        out.write(buffer, 0, n);
                        done += (long)n;
                        long now = System.nanoTime();
                        if (now - lastReport <= 100000000L) continue;
                        lastReport = now;
                        listener.bytes(done, total);
                    }
                    listener.bytes(done, total);
                }
                last = null;
                break;
            }
            catch (InterruptedIOException e) {
                throw e;
            }
            catch (IOException e) {
                last = e;
                listener.stage("Connection dropped, resuming (" + attempt + "/5)");
                OllamaInstaller.sleep(Math.min(8000L, 1000L * (long)attempt * (long)attempt));
                continue;
            }
        }
        if (last != null) {
            throw last;
        }
        if (expectedSize > 0L && Files.size(part) != expectedSize) {
            throw new IOException("The Ollama download is incomplete. Please try again.");
        }
        Files.move(part, target, StandardCopyOption.REPLACE_EXISTING);
    }

    private static String sha256(Path file, Listener listener, BooleanSupplier cancelled) throws IOException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
        long total = Files.size(file);
        try (InputStream in = Files.newInputStream(file, new OpenOption[0]);){
            int n;
            byte[] buffer = new byte[65536];
            long done = 0L;
            long lastReport = 0L;
            while ((n = in.read(buffer)) >= 0) {
                if (cancelled.getAsBoolean()) {
                    throw new InterruptedIOException("Cancelled");
                }
                digest.update(buffer, 0, n);
                if (listener == null || (done += (long)n) - lastReport <= 0x1000000L) continue;
                lastReport = done;
                listener.bytes(done, total);
            }
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    private static <T> HttpResponse<T> send(HttpClient client, HttpRequest request, HttpResponse.BodyHandler<T> handler) throws IOException {
        try {
            return client.send(request, handler);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted");
        }
    }

    private static void sleep(long ms) throws InterruptedIOException {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("Interrupted");
        }
    }

    static void deleteTree(Path dir) throws IOException {
        if (!Files.exists(dir, new LinkOption[0])) {
            return;
        }
        try (Stream<Path> walk = Files.walk(dir, new FileVisitOption[0]);){
            for (Path p : walk.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(p);
            }
        }
    }

    public static interface Listener {
        public void stage(String var1);

        public void bytes(long var1, long var3);
    }

    private record Release(String version, String url, long size, String sha256) {
    }
}

