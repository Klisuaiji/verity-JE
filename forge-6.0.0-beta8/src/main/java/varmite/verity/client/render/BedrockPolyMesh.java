/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.util.GsonHelper
 *  org.slf4j.Logger
 */
package varmite.verity.client.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
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

public final class BedrockPolyMesh {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final int STRIDE = 8;
    public static final int VERTICES_PER_FACE = 4;
    private static final Map<ResourceLocation, Map<String, float[]>> CACHE = new ConcurrentHashMap<ResourceLocation, Map<String, float[]>>();

    private BedrockPolyMesh() {
    }

    public static Map<String, float[]> forModel(ResourceLocation modelLocation) {
        return CACHE.computeIfAbsent(modelLocation, BedrockPolyMesh::load);
    }

    public static void clearCache() {
        CACHE.clear();
    }

    private static Map<String, float[]> load(ResourceLocation modelLocation) {
        HashMap<String, float[]> hashMap;
        block11: {
            Optional resource = Minecraft.m_91087_().m_91098_().m_213713_(modelLocation);
            if (resource.isEmpty()) {
                LOGGER.error("Could not find Bedrock model {} while looking for poly_mesh geometry", (Object)modelLocation);
                return Map.of();
            }
            BufferedReader reader = ((Resource)resource.get()).m_215508_();
            try {
                JsonObject root = GsonHelper.m_13859_((Reader)reader);
                HashMap<String, float[]> meshes = new HashMap<String, float[]>();
                for (JsonElement geometryElement : GsonHelper.m_13832_((JsonObject)root, (String)"minecraft:geometry", (JsonArray)new JsonArray())) {
                    JsonObject geometry = geometryElement.getAsJsonObject();
                    JsonObject description = GsonHelper.m_13841_((JsonObject)geometry, (String)"description", (JsonObject)new JsonObject());
                    float textureWidth = GsonHelper.m_13820_((JsonObject)description, (String)"texture_width", (float)16.0f);
                    float textureHeight = GsonHelper.m_13820_((JsonObject)description, (String)"texture_height", (float)16.0f);
                    for (JsonElement boneElement : GsonHelper.m_13832_((JsonObject)geometry, (String)"bones", (JsonArray)new JsonArray())) {
                        float[] baked;
                        JsonObject bone = boneElement.getAsJsonObject();
                        if (!bone.has("poly_mesh") || (baked = BedrockPolyMesh.bakeBone(GsonHelper.m_13930_((JsonObject)bone, (String)"poly_mesh"), textureWidth, textureHeight)).length <= 0) continue;
                        meshes.put(GsonHelper.m_13906_((JsonObject)bone, (String)"name"), baked);
                    }
                }
                LOGGER.info("Loaded poly_mesh geometry for {} bone(s) from {}", (Object)meshes.size(), (Object)modelLocation);
                hashMap = meshes;
                if (reader == null) break block11;
            }
            catch (Throwable throwable) {
                try {
                    if (reader != null) {
                        try {
                            reader.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    LOGGER.error("Failed to read poly_mesh geometry from {}", (Object)modelLocation, (Object)e);
                    return Map.of();
                }
            }
            reader.close();
        }
        return hashMap;
    }

    private static float[] bakeBone(JsonObject polyMesh, float textureWidth, float textureHeight) {
        float[][] positions = BedrockPolyMesh.readVectors(polyMesh, "positions", 3);
        float[][] normals = BedrockPolyMesh.readVectors(polyMesh, "normals", 3);
        float[][] uvs = BedrockPolyMesh.readVectors(polyMesh, "uvs", 2);
        JsonArray polys = GsonHelper.m_13832_((JsonObject)polyMesh, (String)"polys", (JsonArray)new JsonArray());
        boolean normalizedUvs = GsonHelper.m_13855_((JsonObject)polyMesh, (String)"normalized_uvs", (boolean)false);
        float uScale = normalizedUvs ? 1.0f : 1.0f / textureWidth;
        float vScale = normalizedUvs ? 1.0f : 1.0f / textureHeight;
        float[] out = new float[polys.size() * 4 * 8];
        int cursor = 0;
        for (JsonElement polyElement : polys) {
            int[] corners;
            JsonArray poly = polyElement.getAsJsonArray();
            switch (poly.size()) {
                case 4: {
                    int[] nArray = new int[4];
                    nArray[0] = 3;
                    nArray[1] = 2;
                    nArray[2] = 1;
                    int[] nArray2 = nArray;
                    nArray[3] = 0;
                    break;
                }
                case 3: {
                    int[] nArray = new int[4];
                    nArray[0] = 2;
                    nArray[1] = 1;
                    nArray[2] = 0;
                    int[] nArray2 = nArray;
                    nArray[3] = 0;
                    break;
                }
                default: {
                    int[] nArray2 = corners = null;
                }
            }
            if (corners == null) continue;
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

    private static float[][] readVectors(JsonObject polyMesh, String key, int componentCount) {
        JsonArray array = GsonHelper.m_13832_((JsonObject)polyMesh, (String)key, (JsonArray)new JsonArray());
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

