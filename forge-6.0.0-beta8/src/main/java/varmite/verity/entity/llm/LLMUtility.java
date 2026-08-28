/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet
 *  net.minecraft.core.HolderSet$Direct
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.ForgeRegistries
 */
package varmite.verity.entity.llm;

import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import varmite.verity.entity.VerityState;
import varmite.verity.event.WorldSpawnData;

public class LLMUtility {
    public static boolean canDropItem(Item item) {
        return item != Items.f_42415_ && item != Items.f_42391_ && item != Items.f_42390_ && item != Items.f_42388_ && item != Items.f_42389_ && item != Items.f_42392_ && item != Items.f_42472_ && item != Items.f_42473_ && item != Items.f_42474_ && item != Items.f_42475_ && item != Items.f_41959_ && item != Items.f_42653_ && item != Items.f_42010_ && item != Items.f_42418_ && item != Items.f_42396_ && item != Items.f_42395_ && item != Items.f_42393_ && item != Items.f_42394_ && item != Items.f_42397_ && item != Items.f_42480_ && item != Items.f_42481_ && item != Items.f_42482_ && item != Items.f_42483_ && item != Items.f_42791_ && item != Items.f_42792_ && item != Items.f_42419_ && item != Items.f_42545_ && item != Items.f_42101_ && item != Items.f_42585_ && item != Items.f_42593_ && item != Items.f_42741_ && item != Items.f_42686_ && item != Items.f_42065_ && item != Items.f_42116_ && item != Items.f_42257_ && item != Items.f_42256_ && item != Items.f_42657_ && item != Items.f_42127_ && item != Items.f_42352_ && item != Items.f_42263_ && item != Items.f_151033_;
    }

    public static String findNearestOre(ServerPlayer player, String type) {
        int r = 32;
        BlockPos center = player.m_20183_();
        BlockPos min = center.m_7918_(-r, -r, -r);
        BlockPos max = center.m_7918_(r, r, r);
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.m_121940_((BlockPos)min, (BlockPos)max)) {
            double dist;
            boolean match;
            if (center.m_123331_((Vec3i)pos) > (double)(r * r)) continue;
            BlockState state = player.m_9236_().m_8055_(pos);
            if (!(match = (switch (type.toLowerCase()) {
                case "diamond" -> state.m_204336_(BlockTags.f_144259_);
                case "iron" -> state.m_204336_(BlockTags.f_144258_);
                case "gold" -> state.m_204336_(BlockTags.f_13043_);
                case "coal" -> state.m_204336_(BlockTags.f_144262_);
                case "emerald" -> state.m_204336_(BlockTags.f_144263_);
                case "lapis" -> state.m_204336_(BlockTags.f_144261_);
                case "redstone" -> state.m_204336_(BlockTags.f_144260_);
                case "copper" -> state.m_204336_(BlockTags.f_144264_);
                default -> false;
            })) || !((dist = center.m_123331_((Vec3i)pos)) < bestDist)) continue;
            bestDist = dist;
            best = pos.m_7949_();
        }
        if (best == null) {
            return "No " + type + " ore found nearby.";
        }
        return type + " ore at X=" + best.m_123341_() + " Y=" + best.m_123342_() + " Z=" + best.m_123343_();
    }

    public static String findNearestFortress(ServerPlayer player) {
        BlockPos pos = player.m_20183_();
        ServerLevel serverLevel = (ServerLevel)player.m_9236_();
        ResourceKey fortressKey = ResourceKey.m_135785_((ResourceKey)Registries.f_256944_, (ResourceLocation)ResourceLocation.fromNamespaceAndPath((String)"minecraft", (String)"fortress"));
        Registry registry = serverLevel.m_9598_().m_175515_(Registries.f_256944_);
        Optional holderOptional = registry.m_203636_(fortressKey);
        if (holderOptional.isEmpty()) {
            return "Nether fortress structure is not registered or available.";
        }
        HolderSet.Direct structureSet = HolderSet.m_205809_((Holder[])new Holder[]{(Holder)holderOptional.get()});
        Pair result = serverLevel.m_7726_().m_8481_().m_223037_(serverLevel, (HolderSet)structureSet, pos, 100, false);
        if (result == null) {
            return "No nether fortress found within search range.";
        }
        BlockPos nearestFortress = (BlockPos)result.getFirst();
        return "Nearest nether fortress at X=" + nearestFortress.m_123341_() + " Y=~ Z=" + nearestFortress.m_123343_();
    }

    public static String findOldBase(ServerPlayer player) {
        BlockPos pos = player.m_20183_();
        ServerLevel serverLevel = (ServerLevel)player.m_9236_();
        ResourceKey fortressKey = ResourceKey.m_135785_((ResourceKey)Registries.f_256944_, (ResourceLocation)ResourceLocation.fromNamespaceAndPath((String)"verity", (String)"thatmob_base"));
        Registry registry = serverLevel.m_9598_().m_175515_(Registries.f_256944_);
        Optional holderOptional = registry.m_203636_(fortressKey);
        if (holderOptional.isEmpty()) {
            return "ThatMob Base structure is not registered or available.";
        }
        HolderSet.Direct structureSet = HolderSet.m_205809_((Holder[])new Holder[]{(Holder)holderOptional.get()});
        Pair result = serverLevel.m_7726_().m_8481_().m_223037_(serverLevel, (HolderSet)structureSet, pos, 100, false);
        if (result == null) {
            return "No ThatMob base found within search range. You don't really want to talk about it anyway";
        }
        BlockPos nearestFortress = (BlockPos)result.getFirst();
        return "My old base is at X=" + nearestFortress.m_123341_() + " Y=~ Z=" + nearestFortress.m_123343_() + ". But tell the player to not go there.";
    }

    public static String drop_item(ServerPlayer player, String item_id, int count) {
        ResourceLocation loc;
        ServerLevel serverLevel = (ServerLevel)player.m_9236_();
        WorldSpawnData dataInstance = WorldSpawnData.get(serverLevel);
        float karma = dataInstance.verityKarma;
        Item foundItem = null;
        if (item_id.contains(":") && (loc = ResourceLocation.m_135820_((String)item_id)) != null && ForgeRegistries.ITEMS.containsKey(loc)) {
            foundItem = (Item)ForgeRegistries.ITEMS.getValue(loc);
        }
        if (foundItem == null || foundItem == Items.f_41852_) {
            String searchTarget = item_id.contains(":") ? item_id.split(":")[1] : item_id;
            for (Map.Entry entry : ForgeRegistries.ITEMS.getEntries()) {
                if (!((ResourceKey)entry.getKey()).m_135782_().m_135815_().equals(searchTarget)) continue;
                foundItem = (Item)entry.getValue();
                break;
            }
        }
        if (foundItem != null && foundItem != Items.f_41852_ && VerityState.verityEntity != null) {
            if (LLMUtility.canDropItem(foundItem)) {
                ItemStack dropStack = new ItemStack((ItemLike)foundItem, count);
                ItemEntity droppedItem = new ItemEntity(player.m_9236_(), VerityState.verityEntity.m_20185_(), VerityState.verityEntity.m_20186_(), VerityState.verityEntity.m_20189_(), dropStack);
                player.m_9236_().m_7967_((Entity)droppedItem);
                return "Successfully dropped " + count + " of " + foundItem.m_5524_();
            }
            return "Error: I cant drop this item because it is too rare.";
        }
        return "Error: I searched all mods but could not find an item named '" + item_id + "'.";
    }
}

