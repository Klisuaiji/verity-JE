package varmite.verity.types;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum Ores {
    DIAMOND(BlockTags.DIAMOND_ORES),
    IRON(BlockTags.IRON_ORES),
    GOLD(BlockTags.GOLD_ORES),
    COAL(BlockTags.COAL_ORES),
    EMERALD(BlockTags.EMERALD_ORES),
    LAPIS(BlockTags.LAPIS_ORES),
    REDSTONE(BlockTags.REDSTONE_ORES),
    COPPER(BlockTags.COPPER_ORES);

    private final TagKey<Block> blockTag;

    Ores(TagKey<Block> blockTag) {
        this.blockTag = blockTag;
    }

    public TagKey<Block> getBlockTag() {
        return this.blockTag;
    }

    /**
     * @return the matching ore, or {@code null} when {@code name} is not a known ore.
     *         (6.1 callers null-check the result, so never throw here.)
     */
    public static Ores fromString(String name) {
        if (name == null) {
            return null;
        }
        try {
            return Ores.valueOf(name.trim().toUpperCase(java.util.Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
