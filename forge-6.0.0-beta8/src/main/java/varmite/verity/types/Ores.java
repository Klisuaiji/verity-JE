/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.block.Block
 */
package varmite.verity.types;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum Ores {
    DIAMOND((TagKey<Block>)BlockTags.f_144259_),
    IRON((TagKey<Block>)BlockTags.f_144258_),
    GOLD((TagKey<Block>)BlockTags.f_13043_),
    COAL((TagKey<Block>)BlockTags.f_144262_),
    EMERALD((TagKey<Block>)BlockTags.f_144263_),
    LAPIS((TagKey<Block>)BlockTags.f_144261_),
    REDSTONE((TagKey<Block>)BlockTags.f_144260_),
    COPPER((TagKey<Block>)BlockTags.f_144264_);


    private Ores(TagKey<Block> blockTag) {
    }

    public static Ores fromString(String name) {
        return Ores.valueOf(name.toUpperCase());
    }
}

