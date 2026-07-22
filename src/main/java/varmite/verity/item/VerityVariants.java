/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  varmite.verity.item.VerityVariants
 */
package varmite.verity.item;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/*
 * Exception performing whole class analysis ignored.
 */
public final class VerityVariants {
    private static final List<String> VALID_VARIANTS = List.of("crazy_talking", "happy", "happy_sleep", "happy_talking", "hurt", "neutral", "noface", "serious_1", "serious_2", "serious_3", "serious_talking", "evil", "evil_talking", "smiling_evil", "crazy", "neutral_talking");

    private VerityVariants() {
    }

    public static String fromStack(ItemStack stack) {
        CompoundTag tag;
        if (stack.m_41782_() && (tag = stack.m_41783_()).m_128441_("VerityVariant")) {
            return VerityVariants.sanitize((String)tag.m_128461_("VerityVariant"));
        }
        return "happy";
    }

    public static ResourceLocation entityTexture(String variant) {
        String safeVariant = VerityVariants.sanitize((String)variant);
        if (!VALID_VARIANTS.contains(safeVariant)) {
            safeVariant = "happy";
        }
        return ResourceLocation.fromNamespaceAndPath((String)"verity", (String)("textures/entity/" + safeVariant + ".png"));
    }

    private static String sanitize(String variant) {
        if (variant != null && !variant.isBlank()) {
            if (variant.endsWith(".png")) {
                variant = variant.substring(0, variant.length() - 4);
            }
            return variant;
        }
        return "happy";
    }
}

