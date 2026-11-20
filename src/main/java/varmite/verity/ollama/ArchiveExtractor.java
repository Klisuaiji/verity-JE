/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.ollama;

import io.airlift.compress.zstd.ZstdInputStream;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ArchiveExtractor {
    private static final int BLOCK = 512;

    private ArchiveExtractor() {
    }

    public static void extract(Path archive, Path destination, Progress progress, BooleanSupplier cancelled) throws IOException {
        block8: {
            String name = archive.getFileName().toString().toLowerCase(Locale.ROOT);
            long total = Files.size(archive);
            Files.createDirectories(destination, new FileAttribute[0]);
            try (CountingInputStream counted = new CountingInputStream(Files.newInputStream(archive, new OpenOption[0]), total, progress, cancelled);){
                BufferedInputStream in = new BufferedInputStream(counted, 65536);
                if (name.endsWith(".zip")) {
                    ArchiveExtractor.extractZip(in, destination);
                    break block8;
                }
                if (name.endsWith(".tar.zst") || name.endsWith(".tzst")) {
                    ArchiveExtractor.extractTar(new BufferedInputStream((InputStream)new ZstdInputStream((InputStream)in), 65536), destination);
                    break block8;
                }
                if (name.endsWith(".tgz") || name.endsWith(".tar.gz")) {
                    ArchiveExtractor.extractTar(new BufferedInputStream(new GZIPInputStream((InputStream)in, 65536), 65536), destination);
                    break block8;
                }
                throw new IOException("Unsupported archive type: " + String.valueOf(archive.getFileName()));
            }
        }
    }

    private static void extractZip(InputStream in, Path destination) throws IOException {
        try (ZipInputStream zip = new ZipInputStream(in);){
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                Path target = ArchiveExtractor.safeResolve(destination, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(target, new FileAttribute[0]);
                    continue;
                }
                Files.createDirectories(target.getParent(), new FileAttribute[0]);
                Files.copy(zip, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void extractTar(InputStream in, Path destination) throws IOException {
        byte[] header = new byte[512];
        String longName = null;
        String longLink = null;
        String paxPath = null;
        String paxLink = null;
        Long paxSize = null;
        block27: while (ArchiveExtractor.readFully(in, header)) {
            String prefix;
            if (ArchiveExtractor.isZeroBlock(header)) {
                return;
            }
            Object name = ArchiveExtractor.cString(header, 0, 100);
            int mode = (int)ArchiveExtractor.parseOctal(header, 100, 8);
            long size = ArchiveExtractor.parseNumber(header, 124, 12);
            char type = (char)header[156];
            String linkName = ArchiveExtractor.cString(header, 157, 100);
            String magic = ArchiveExtractor.cString(header, 257, 6);
            if (magic.startsWith("ustar") && !(prefix = ArchiveExtractor.cString(header, 345, 155)).isEmpty()) {
                name = prefix + "/" + (String)name;
            }
            if (paxSize != null) {
                size = paxSize;
            }
            switch (type) {
                case 'L': {
                    longName = ArchiveExtractor.readString(in, size);
                    continue block27;
                }
                case 'K': {
                    longLink = ArchiveExtractor.readString(in, size);
                    continue block27;
                }
                case 'x': {
                    String pax = ArchiveExtractor.readString(in, size);
                    Iterator<String> iterator = ArchiveExtractor.parsePax(pax).iterator();
                    while (true) {
                        if (!iterator.hasNext()) continue block27;
                        String record = iterator.next();
                        int eq = record.indexOf(61);
                        if (eq < 0) continue;
                        String key = record.substring(0, eq);
                        String value = record.substring(eq + 1);
                        switch (key) {
                            case "path": {
                                paxPath = value;
                                break;
                            }
                            case "linkpath": {
                                paxLink = value;
                                break;
                            }
                            case "size": {
                                paxSize = Long.parseLong(value.trim());
                                break;
                            }
                        }
                    }
                }
                case 'g': {
                    ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
                    continue block27;
                }
            }
            if (paxPath != null) {
                name = paxPath;
            } else if (longName != null) {
                name = longName;
            }
            if (paxLink != null) {
                linkName = paxLink;
            } else if (longLink != null) {
                linkName = longLink;
            }
            paxLink = null;
            paxPath = null;
            longLink = null;
            longName = null;
            paxSize = null;
            if (((String)name).isEmpty() || ((String)name).equals("./") || ((String)name).equals(".")) {
                ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
                continue;
            }
            Path target = ArchiveExtractor.safeResolve(destination, (String)name);
            switch (type) {
                case '5': {
                    Files.createDirectories(target, new FileAttribute[0]);
                    ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
                    continue block27;
                }
                case '2': {
                    Files.createDirectories(target.getParent(), new FileAttribute[0]);
                    ArchiveExtractor.safeResolve(target.getParent(), linkName, destination);
                    Files.deleteIfExists(target);
                    Files.createSymbolicLink(target, target.getFileSystem().getPath(linkName, new String[0]), new FileAttribute[0]);
                    ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
                    continue block27;
                }
                case '1': {
                    Files.createDirectories(target.getParent(), new FileAttribute[0]);
                    Path source = ArchiveExtractor.safeResolve(destination, linkName);
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
                    continue block27;
                }
                case '\u0000': 
                case '0': 
                case '7': {
                    Files.createDirectories(target.getParent(), new FileAttribute[0]);
                    Files.deleteIfExists(target);
                    try (BoundedInputStream body = new BoundedInputStream(in, size);){
                        Files.copy(body, target, new CopyOption[0]);
                    }
                    ArchiveExtractor.skip(in, ArchiveExtractor.padded(size) - size);
                    ArchiveExtractor.applyMode(target, mode);
                    continue block27;
                }
            }
            ArchiveExtractor.skip(in, ArchiveExtractor.padded(size));
        }
        return;
    }

    private static void applyMode(Path file, int mode) {
        if (!FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
            return;
        }
        EnumSet<PosixFilePermission> perms = EnumSet.noneOf(PosixFilePermission.class);
        PosixFilePermission[] order = new PosixFilePermission[]{PosixFilePermission.OTHERS_EXECUTE, PosixFilePermission.OTHERS_WRITE, PosixFilePermission.OTHERS_READ, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.GROUP_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ};
        for (int bit = 0; bit < order.length; ++bit) {
            if ((mode & 1 << bit) == 0) continue;
            perms.add(order[bit]);
        }
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        try {
            Files.setPosixFilePermissions(file, perms);
        }
        catch (IOException | UnsupportedOperationException exception) {
            // empty catch block
        }
    }

    private static Path safeResolve(Path destination, String entryName) throws IOException {
        return ArchiveExtractor.safeResolve(destination, entryName, destination);
    }

    private static Path safeResolve(Path base, String entryName, Path mustStayIn) throws IOException {
        String cleaned = entryName.replace('\\', '/');
        while (cleaned.startsWith("./")) {
            cleaned = cleaned.substring(2);
        }
        if (cleaned.startsWith("/") || cleaned.matches("^[A-Za-z]:.*")) {
            throw new IOException("Archive entry uses an absolute path: " + entryName);
        }
        Path resolved = base.resolve(cleaned).normalize();
        if (!resolved.startsWith(mustStayIn.normalize())) {
            throw new IOException("Archive entry escapes the install folder: " + entryName);
        }
        return resolved;
    }

    private static Iterable<String> parsePax(String pax) {
        int length;
        ArrayList<String> records = new ArrayList<String>();
        byte[] bytes = pax.getBytes(StandardCharsets.UTF_8);
        for (int pos = 0; pos < bytes.length; pos += length) {
            int space;
            for (space = pos; space < bytes.length && bytes[space] != 32; ++space) {
            }
            if (space >= bytes.length) break;
            try {
                length = Integer.parseInt(new String(bytes, pos, space - pos, StandardCharsets.UTF_8).trim());
            }
            catch (NumberFormatException e) {
                break;
            }
            if (length <= 0 || pos + length > bytes.length) break;
            int start = space + 1;
            int end = pos + length - 1;
            records.add(new String(bytes, start, Math.max(0, end - start), StandardCharsets.UTF_8));
        }
        return records;
    }

    private static boolean isZeroBlock(byte[] block) {
        for (byte b : block) {
            if (b == 0) continue;
            return false;
        }
        return true;
    }

    private static String cString(byte[] buf, int offset, int length) {
        int end;
        for (end = offset; end < offset + length && buf[end] != 0; ++end) {
        }
        return new String(buf, offset, end - offset, StandardCharsets.UTF_8);
    }

    private static long parseOctal(byte[] buf, int offset, int length) {
        long value = 0L;
        for (int i = offset; i < offset + length; ++i) {
            byte b = buf[i];
            if (b == 0 || b == 32) {
                if (value == 0L) continue;
                break;
            }
            if (b < 48 || b > 55) break;
            value = (value << 3) + (long)(b - 48);
        }
        return value;
    }

    private static long parseNumber(byte[] buf, int offset, int length) {
        if ((buf[offset] & 0x80) != 0) {
            long value = buf[offset] & 0x7F;
            for (int i = offset + 1; i < offset + length; ++i) {
                value = value << 8 | (long)(buf[i] & 0xFF);
            }
            return value;
        }
        return ArchiveExtractor.parseOctal(buf, offset, length);
    }

    private static long padded(long size) {
        return (size + 512L - 1L) / 512L * 512L;
    }

    private static String readString(InputStream in, long size) throws IOException {
        if (size > 0x100000L) {
            throw new IOException("Unreasonably large tar header record: " + size);
        }
        byte[] data = new byte[(int)size];
        if (!ArchiveExtractor.readFully(in, data)) {
            throw new EOFException("Truncated tar header record");
        }
        ArchiveExtractor.skip(in, ArchiveExtractor.padded(size) - size);
        return ArchiveExtractor.cString(data, 0, data.length);
    }

    private static boolean readFully(InputStream in, byte[] buf) throws IOException {
        int n;
        for (int read = 0; read < buf.length; read += n) {
            n = in.read(buf, read, buf.length - read);
            if (n >= 0) continue;
            if (read == 0) {
                return false;
            }
            throw new EOFException("Archive ended in the middle of a tar block");
        }
        return true;
    }

    private static void skip(InputStream in, long count) throws IOException {
        int n;
        byte[] scratch = new byte[8192];
        for (long left = count; left > 0L; left -= (long)n) {
            n = in.read(scratch, 0, (int)Math.min((long)scratch.length, left));
            if (n >= 0) continue;
            throw new EOFException("Archive ended early");
        }
    }

    private static final class CountingInputStream
    extends FilterInputStream {
        private final long total;
        private final Progress progress;
        private final BooleanSupplier cancelled;
        private long count;
        private long lastReport;

        CountingInputStream(InputStream in, long total, Progress progress, BooleanSupplier cancelled) {
            super(in);
            this.total = total;
            this.progress = progress;
            this.cancelled = cancelled;
        }

        @Override
        public int read() throws IOException {
            int b = super.read();
            if (b >= 0) {
                this.advance(1L);
            }
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int n = super.read(b, off, len);
            if (n > 0) {
                this.advance(n);
            }
            return n;
        }

        private void advance(long n) throws IOException {
            this.count += n;
            if (this.count - this.lastReport >= 0x100000L || this.count == this.total) {
                this.lastReport = this.count;
                if (this.cancelled != null && this.cancelled.getAsBoolean()) {
                    throw new InterruptedIOException("Cancelled");
                }
                if (this.progress != null) {
                    this.progress.onProgress(this.count, this.total);
                }
            }
        }
    }

    public static interface Progress {
        public void onProgress(long var1, long var3);
    }

    private static final class BoundedInputStream
    extends FilterInputStream {
        private long remaining;

        BoundedInputStream(InputStream in, long size) {
            super(in);
            this.remaining = size;
        }

        @Override
        public int read() throws IOException {
            if (this.remaining <= 0L) {
                return -1;
            }
            int b = super.read();
            if (b >= 0) {
                --this.remaining;
            }
            return b;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (this.remaining <= 0L) {
                return -1;
            }
            int n = super.read(b, off, (int)Math.min((long)len, this.remaining));
            if (n < 0) {
                throw new EOFException("Archive ended inside a file");
            }
            this.remaining -= (long)n;
            return n;
        }

        @Override
        public void close() {
        }
    }
}

