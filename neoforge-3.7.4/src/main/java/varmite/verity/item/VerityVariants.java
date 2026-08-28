/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 */
package varmite.verity.item;

import java.util.Set;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class VerityVariants {
    private static final Set<String> VALID_VARIANTS = Set.of("crazy_talking_0", "default", "default_sleep_0", "default_talking_1", "hurt_0", "neutral_0", "noface", "serious_1", "serious_2", "serious_3", "serious_angry", "smile2", "smile4", "smiling_evil_0", "smiling_evil_1", "talking_0");

    private VerityVariants() {
    }

    public static String fromStack(ItemStack stack) {
        CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY);
        if (customData.contains("VerityVariant")) {
            return VerityVariants.sanitize(customData.copyTag().getString("VerityVariant"));
        }
        return "default";
    }

    public static ResourceLocation entityTexture(String variant) {
        String safeVariant = VerityVariants.sanitize(variant);
        if (!VALID_VARIANTS.contains(safeVariant)) {
            safeVariant = "default";
        }
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)("textures/entity/" + safeVariant + ".png"));
    }

    private static String sanitize(String variant) {
        if (variant == null || variant.isBlank()) {
            return "default";
        }
        if (variant.endsWith(".png")) {
            variant = variant.substring(0, variant.length() - 4);
        }
        return variant;
    }
}

