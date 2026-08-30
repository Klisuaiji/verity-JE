package varmite.verity.client.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

/**
 * Ported from the official 6.0.0-beta.8 distribution to NeoForge 1.21.1.
 *
 * Loads Bedrock-model "poly_mesh" geometry (assets/verity/meshes/entity/*.json) into
 * flat interleaved vertex buffers: 4 vertices per poly, {@value #STRIDE} floats each
 * (position xyz, uv, normal xyz). Consumed by the sphere/entity renderers that need
 * real triangle geometry instead of cube boxes.
 *
 * This class touches no Forge/NeoForge API — only Minecraft resource loading and Gson —
 * so it ported over essentially unchanged.
 */
public final class BedrockPolyMesh {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final int STRIDE = 8;
    public static final int VERTICES_PER_FACE = 4;
    private static final Map<ResourceLocation, Map<String, float[]>> CACHE = new ConcurrentHashMap<>();

    private BedrockPolyMesh() {
    }

    /** Baked geometry keyed by bone name, or an empty map if the model is missing/broken. */
    public static Map<String, float[]> forModel(ResourceLocation modelLocation) {
        return CACHE.computeIfAbsent(modelLocation, BedrockPolyMesh::load);
    }

    public static void clearCache() {
        CACHE.clear();
    }

    private static Map<String, float[]> load(ResourceLocation modelLocation) {
        Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(modelLocation);
        if (resource.isEmpty()) {
            LOGGER.error("Could not find Bedrock model {} while looking for poly_mesh geometry", modelLocation);
            return Map.of();
        }
        try (Reader reader = resource.get().openAsReader()) {
            JsonObject root = GsonHelper.parse(reader);
            Map<String, float[]> meshes = new HashMap<>();
            for (JsonElement geometryElement : GsonHelper.getAsJsonArray(root, "minecraft:geometry", new JsonArray())) {
                JsonObject geometry = geometryElement.getAsJsonObject();
                JsonObject description = GsonHelper.getAsJsonObject(geometry, "description", new JsonObject());
                float textureWidth = GsonHelper.getAsFloat(description, "texture_width", 16.0f);
                float textureHeight = GsonHelper.getAsFloat(description, "texture_height", 16.0f);
                for (JsonElement boneElement : GsonHelper.getAsJsonArray(geometry, "bones", new JsonArray())) {
                    JsonObject bone = boneElement.getAsJsonObject();
                    if (!bone.has("poly_mesh")) {
                        continue;
                    }
                    float[] baked = bakeBone(GsonHelper.getAsJsonObject(bone, "poly_mesh"), textureWidth, textureHeight);
                    if (baked.length <= 0) {
                        continue;
                    }
                    meshes.put(GsonHelper.getString(bone, "name"), baked);
                }
            }
            LOGGER.info("Loaded poly_mesh geometry for {} bone(s) from {}", meshes.size(), modelLocation);
            return meshes;
        } catch (Exception e) {
            LOGGER.error("Failed to read poly_mesh geometry from {}", modelLocation, e);
            return Map.of();
        }
    }

    private static float[] bakeBone(JsonObject polyMesh, float textureWidth, float textureHeight) {
        float[][] positions = readVectors(polyMesh, "positions", 3);
        float[][] normals = readVectors(polyMesh, "normals", 3);
        float[][] uvs = readVectors(polyMesh, "uvs", 2);
        JsonArray polys = GsonHelper.getAsJsonArray(polyMesh, "polys", new JsonArray());
        boolean normalizedUvs = GsonHelper.getAsBoolean(polyMesh, "normalized_uvs", false);
        float uScale = normalizedUvs ? 1.0f : 1.0f / textureWidth;
        float vScale = normalizedUvs ? 1.0f : 1.0f / textureHeight;
        float[] out = new float[polys.size() * VERTICES_PER_FACE * STRIDE];
        int cursor = 0;
        for (JsonElement polyElement : polys) {
            JsonArray poly = polyElement.getAsJsonArray();
            int[] corners;
            switch (poly.size()) {
                case 4 -> corners = new int[]{3, 2, 1, 0};
                case 3 -> corners = new int[]{2, 1, 0, 0};
                default -> corners = null;
            }
            if (corners == null) {
                continue;
            }
            for (int corner : corners) {
                JsonArray vertex = poly.get(corner).getAsJsonArray();
                float[] position = positions[vertex.get(0).getAsInt()];
                float[] normal = normals[vertex.get(1).getAsInt()];
                float[] uv = uvs[vertex.get(2).getAsInt()];
                out[cursor++] = -position[0] / 16.0f;
                out[cursor++] = position[1] / 16.0f;
                out[cursor++] = position[2] / 16.0f;
                out[cursor++] = uv[0] * uScale;
                out[cursor++] = 1.0f - uv[1] * vScale;
                out[cursor++] = -normal[0];
                out[cursor++] = normal[1];
                out[cursor++] = normal[2];
            }
        }
        return cursor == out.length ? out : Arrays.copyOf(out, cursor);
    }

    /** Reads a vector array that may be either nested [[x,y],...] or flat [x,y,x,y,...]. */
    private static float[][] readVectors(JsonObject polyMesh, String key, int componentCount) {
        JsonArray array = GsonHelper.getAsJsonArray(polyMesh, key, new JsonArray());
        if (array.isEmpty()) {
            return new float[0][];
        }
        if (array.get(0).isJsonArray()) {
            float[][] vectors = new float[array.size()][];
            for (int i = 0; i < array.size(); ++i) {
                JsonArray vector = array.get(i).getAsJsonArray();
                float[] values = new float[vector.size()];
                for (int j = 0; j < vector.size(); ++j) {
                    values[j] = vector.get(j).getAsFloat();
                }
                vectors[i] = values;
            }
            return vectors;
        }
        float[][] vectors = new float[array.size() / componentCount][];
        for (int i = 0; i < vectors.length; ++i) {
            float[] values = new float[componentCount];
            for (int j = 0; j < componentCount; ++j) {
                values[j] = array.get(i * componentCount + j).getAsFloat();
            }
            vectors[i] = values;
        }
        return vectors;
    }
}
