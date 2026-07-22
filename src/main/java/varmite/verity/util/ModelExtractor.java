/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.fml.loading.FMLPaths
 *  varmite.verity.util.ModelExtractor
 */
package varmite.verity.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.neoforged.fml.loading.FMLPaths;

public class ModelExtractor {
    public static Path getOrExtractModel() {
        Path configDir = FMLPaths.CONFIGDIR.get().resolve("verity");
        Path modelDir = configDir.resolve("sherpa-model");
        if (Files.exists(modelDir.resolve("am"), new LinkOption[0])) {
            return modelDir;
        }
        System.out.println("[Verity] First launch detected. Unpacking local STT model...");
        try {
            Files.createDirectories(modelDir, new FileAttribute[0]);
            InputStream is = ModelExtractor.class.getResourceAsStream("/assets/verity/sherpa-model.zip");
            if (is == null) {
                throw new RuntimeException("sherpa-model.zip not found in JAR!");
            }
            try (ZipInputStream zis = new ZipInputStream(is);){
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    Path target = modelDir.resolve(entry.getName());
                    if (entry.isDirectory()) {
                        Files.createDirectories(target, new FileAttribute[0]);
                        continue;
                    }
                    Files.createDirectories(target.getParent(), new FileAttribute[0]);
                    Files.copy(zis, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            System.out.println("[Verity] Local STT model successfully unpacked.");
        }
        catch (Exception e) {
            System.err.println("[Verity] Failed to extract Sherpa model!");
            e.printStackTrace();
        }
        return modelDir;
    }
}

