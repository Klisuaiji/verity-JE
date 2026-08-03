/*
 * Ported from Verity 6.1 (Forge 1.20.1) to NeoForge 1.21.1.
 *
 * Shared world-query helpers used by the langchain4j @Tool implementations.
 * All Forge-only APIs were swapped for their NeoForge / vanilla equivalents:
 *   ForgeRegistries.ITEMS       -> BuiltInRegistries.ITEM
 *   ResourceLocation.m_135820_  -> ResourceLocation.tryParse
 *   SRG field/method names      -> official Mojang mappings
 */
package varmite.verity.entity.LLM;

import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import varmite.verity.entity.VerityState;
import varmite.verity.event.WorldSpawnData;

public class LLMUtility {

    /** Items Verity refuses to hand out (too rare / creative-only). */
    public static boolean canDropItem(Item item) {
        return item != Items.DIAMOND
                && item != Items.DIAMOND_AXE
                && item != Items.DIAMOND_PICKAXE
                && item != Items.DIAMOND_SWORD
                && item != Items.DIAMOND_SHOVEL
                && item != Items.DIAMOND_HOE
                && item != Items.DIAMOND_HELMET
                && item != Items.DIAMOND_CHESTPLATE
                && item != Items.DIAMOND_LEGGINGS
                && item != Items.DIAMOND_BOOTS
                && item != Items.DIAMOND_BLOCK
                && item != Items.DIAMOND_HORSE_ARMOR
                && item != Items.DIAMOND_ORE
                && item != Items.NETHERITE_INGOT
                && item != Items.NETHERITE_AXE
                && item != Items.NETHERITE_PICKAXE
                && item != Items.NETHERITE_SWORD
                && item != Items.NETHERITE_SHOVEL
                && item != Items.NETHERITE_HOE
                && item != Items.NETHERITE_HELMET
                && item != Items.NETHERITE_CHESTPLATE
                && item != Items.NETHERITE_LEGGINGS
                && item != Items.NETHERITE_BOOTS
                && item != Items.NETHERITE_BLOCK
                && item != Items.ANCIENT_DEBRIS
                && item != Items.NETHERITE_SCRAP
                && item != Items.ENDER_EYE
                && item != Items.END_PORTAL_FRAME
                && item != Items.BLAZE_ROD
                && item != Items.ELYTRA
                && item != Items.NETHER_STAR
                && item != Items.BEACON
                && item != Items.COMMAND_BLOCK
                && item != Items.CHAIN_COMMAND_BLOCK
                && item != Items.REPEATING_COMMAND_BLOCK
                && item != Items.COMMAND_BLOCK_MINECART
                && item != Items.BARRIER
                && item != Items.STRUCTURE_BLOCK
                && item != Items.STRUCTURE_VOID
                && item != Items.LIGHT;
    }

    public static String findNearestOre(ServerPlayer player, String type) {
        int r = 32;
        BlockPos center = player.blockPosition();
        BlockPos min = center.offset(-r, -r, -r);
        BlockPos max = center.offset(r, r, r);
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (center.distSqr((Vec3i) pos) > (double) (r * r)) {
                continue;
            }
            BlockState state = player.level().getBlockState(pos);
            boolean match = switch (type.toLowerCase()) {
                case "diamond" -> state.is(BlockTags.DIAMOND_ORES);
                case "iron" -> state.is(BlockTags.IRON_ORES);
                case "gold" -> state.is(BlockTags.GOLD_ORES);
                case "coal" -> state.is(BlockTags.COAL_ORES);
                case "emerald" -> state.is(BlockTags.EMERALD_ORES);
                case "lapis" -> state.is(BlockTags.LAPIS_ORES);
                case "redstone" -> state.is(BlockTags.REDSTONE_ORES);
                case "copper" -> state.is(BlockTags.COPPER_ORES);
                default -> false;
            };
            if (!match) {
                continue;
            }
            double dist = center.distSqr((Vec3i) pos);
            if (dist >= bestDist) {
                continue;
            }
            bestDist = dist;
            best = pos.immutable();
        }

        if (best == null) {
            return "No " + type + " ore found nearby.";
        }
        return type + " ore at X=" + best.getX() + " Y=" + best.getY() + " Z=" + best.getZ();
    }

    public static String findNearestFortress(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        ServerLevel serverLevel = (ServerLevel) player.level();
        ResourceKey<Structure> fortressKey = ResourceKey.create(
                Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("minecraft", "fortress"));
        Registry<Structure> registry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
        Optional<Holder.Reference<Structure>> holderOptional = registry.getHolder(fortressKey);
        if (holderOptional.isEmpty()) {
            return "Nether fortress structure is not registered or available.";
        }
        HolderSet.Direct<Structure> structureSet = HolderSet.direct(holderOptional.get());
        Pair<BlockPos, Holder<Structure>> result = serverLevel.getChunkSource().getGenerator()
                .findNearestMapStructure(serverLevel, structureSet, pos, 100, false);
        if (result == null) {
            return "No nether fortress found within search range.";
        }
        BlockPos nearestFortress = result.getFirst();
        return "Nearest nether fortress at X=" + nearestFortress.getX() + " Y=~ Z=" + nearestFortress.getZ();
    }

    public static String drop_item(ServerPlayer player, String item_id, int count) {
        ServerLevel serverLevel = (ServerLevel) player.level();
        WorldSpawnData dataInstance = WorldSpawnData.get(serverLevel);
        float karma = dataInstance.verityKarma;

        Item foundItem = null;
        if (item_id.contains(":")) {
            ResourceLocation loc = ResourceLocation.tryParse(item_id);
            if (loc != null && BuiltInRegistries.ITEM.containsKey(loc)) {
                foundItem = BuiltInRegistries.ITEM.get(loc);
            }
        }
        if (foundItem == null || foundItem == Items.AIR) {
            String searchTarget = item_id.contains(":") ? item_id.split(":")[1] : item_id;
            for (Map.Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
                if (!entry.getKey().location().getPath().equals(searchTarget)) {
                    continue;
                }
                foundItem = entry.getValue();
                break;
            }
        }

        if (foundItem != null && foundItem != Items.AIR && VerityState.verityEntity != null) {
            if (canDropItem(foundItem)) {
                ItemStack dropStack = new ItemStack((ItemLike) foundItem, count);
                ItemEntity droppedItem = new ItemEntity(
                        player.level(),
                        VerityState.verityEntity.getX(),
                        VerityState.verityEntity.getY(),
                        VerityState.verityEntity.getZ(),
                        dropStack);
                player.level().addFreshEntity(droppedItem);
                return "Successfully dropped " + count + " of " + foundItem.getDescription().getString();
            }
            return "Error: I cant drop this item because it is too rare.";
        }
        return "Error: I searched all mods but could not find an item named '" + item_id + "'.";
    }
}
