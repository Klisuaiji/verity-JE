/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.players.PlayerList
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.StructureTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.animal.Chicken
 *  net.minecraft.world.entity.animal.Cow
 *  net.minecraft.world.entity.animal.Pig
 *  net.minecraft.world.entity.animal.Sheep
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.GameRules$BooleanValue
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.ServerChatEvent
 *  net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
 *  net.neoforged.neoforge.event.entity.item.ItemExpireEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDropsEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.neoforged.neoforge.event.tick.ServerTickEvent$Post
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package varmite.verity.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import varmite.verity.VerityConfig;
import varmite.verity.entity.AI.AiAPI;
import varmite.verity.entity.ModEntities;
import varmite.verity.entity.custom.BoxEntity;
import varmite.verity.entity.custom.VerityDemonEntity;
import varmite.verity.entity.custom.VerityEntity;
import varmite.verity.event.WorldSpawnData;
import varmite.verity.item.ModItems;
import varmite.verity.network.PlayTtsPayload;
import varmite.verity.sounds.ModSounds;

@EventBusSubscriber(modid="verity")
public class ModEvents {
    public static boolean hasSpawned = false;
    static double ENTITY_RADIUS = 32.0;
    static VerityEntity verityEntity = null;
    private static final List<ScheduledTask> PENDING_TASKS = new ArrayList<ScheduledTask>();
    private static final List<ScheduledTask> ACTIVE_TASKS = new ArrayList<ScheduledTask>();

    @SubscribeEvent
    public static void onDespawn(ItemExpireEvent event) {
        ServerLevel serverLevel;
        Player p;
        Level level;
        if (event.getEntity().getItem().is((Item)ModItems.VERITY_ITEM.get()) && (level = event.getEntity().level()) instanceof ServerLevel && (p = (serverLevel = (ServerLevel)level).getNearestPlayer((Entity)event.getEntity(), 256.0)) != null) {
            p.getInventory().add(new ItemStack((ItemLike)ModItems.VERITY_ITEM.get()));
            serverLevel.playSound(null, p.blockPosition(), SoundEvents.GHAST_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
            p.sendSystemMessage((Component)Component.literal((String)"<Verity> Ayo chat why u lettin me despawn like that"));
            PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)p, (CustomPacketPayload)new PlayTtsPayload(p.getId(), "Ayo chat why u lettin me despawn like that"), (CustomPacketPayload[])new CustomPacketPayload[0]);
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.is((Item)ModItems.FLASHLIGHT.get())) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            if (!event.getLevel().isClientSide()) {
                Player p = event.getEntity();
                boolean isOn = false;
                if (stack.has(DataComponents.CUSTOM_DATA)) {
                    isOn = ((CustomData)stack.get(DataComponents.CUSTOM_DATA)).copyTag().getBoolean("FlashlightOn");
                }
                boolean newState = !isOn;
                CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putBoolean("FlashlightOn", newState));
                p.setItemInHand(event.getHand(), stack);
                p.getInventory().setChanged();
                if (newState) {
                    p.level().playSound(null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.PLAYERS, 1.0f, 1.0f);
                } else {
                    p.level().playSound(null, p.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player p = (Player)livingEntity;
            if (!(p instanceof ServerPlayer)) {
                return;
            }
            ServerPlayer player = (ServerPlayer)p;
            if (event.getSource().getEntity() instanceof VerityDemonEntity && VerityConfig.CAN_CRASH.getAsBoolean()) {
                player.connection.disconnect((Component)Component.literal((String)("Farewell, " + p.getName().getString())));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropsDrop(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Iterator iterator = event.getDrops().iterator();
            while (iterator.hasNext()) {
                ItemEntity itemEntity = (ItemEntity)iterator.next();
                if (!itemEntity.getItem().is(ModItems.FLASHLIGHT)) continue;
                iterator.remove();
            }
        }
    }

    public static void schedule(Runnable action, int delayInTicks) {
        PENDING_TASKS.add(new ScheduledTask(action, delayInTicks));
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (!PENDING_TASKS.isEmpty()) {
            ACTIVE_TASKS.addAll(PENDING_TASKS);
            PENDING_TASKS.clear();
        }
        Iterator<ScheduledTask> iterator = ACTIVE_TASKS.iterator();
        while (iterator.hasNext()) {
            ScheduledTask st = iterator.next();
            --st.ticksRemaining;
            if (st.ticksRemaining > 0) continue;
            st.task.run();
            iterator.remove();
        }
    }

    @SubscribeEvent
    public static void entitySpawnEvent(EntityJoinLevelEvent event) {
        Random rand = new Random();
        boolean shouldKillEntity = rand.nextBoolean();
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (event.getEntity().getType() == ModEntities.VERITY_ENTITY.get()) {
            hasSpawned = true;
            verityEntity = (VerityEntity)event.getEntity();
        } else {
            Entity entity = event.getEntity();
            if (entity instanceof Villager) {
                Villager v = (Villager)entity;
                v.discard();
            } else {
                entity = event.getEntity();
                if (entity instanceof Cow) {
                    Cow c = (Cow)entity;
                    c.kill();
                } else {
                    entity = event.getEntity();
                    if (entity instanceof Sheep) {
                        Sheep s = (Sheep)entity;
                        if (!shouldKillEntity) {
                            return;
                        }
                        s.kill();
                    } else {
                        entity = event.getEntity();
                        if (entity instanceof Pig) {
                            Pig p = (Pig)entity;
                            if (!shouldKillEntity) {
                                return;
                            }
                            p.kill();
                        } else {
                            entity = event.getEntity();
                            if (entity instanceof Chicken) {
                                Chicken c = (Chicken)entity;
                                if (!shouldKillEntity) {
                                    return;
                                }
                                c.kill();
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return;
        }
        Entity entity = event.getTarget();
        if (entity instanceof VerityEntity) {
            VerityEntity vEntity = (VerityEntity)entity;
            if (!player.getMainHandItem().isEmpty()) {
                return;
            }
            if (((Boolean)vEntity.getEntityData().get(VerityEntity.IS_TALKING)).booleanValue()) {
                if (!event.getLevel().isClientSide()) {
                    player.sendSystemMessage((Component)Component.literal((String)"\u00a7cYou can't do this while he's talking."));
                    player.level().playSound(null, player.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0f, 0.9f);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.FAIL);
                return;
            }
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
            if (!event.getLevel().isClientSide()) {
                MinecraftServer server = vEntity.getServer();
                if (server != null) {
                    ((GameRules.BooleanValue)server.getGameRules().getRule(GameRules.RULE_SENDCOMMANDFEEDBACK)).set(false, server);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "/stopsound @a * verity:verity_disc");
                }
                ItemStack stack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)stack, tag -> tag.putString("VerityVariant", vEntity.getVariant()));
                vEntity.discard();
                hasSpawned = false;
                vEntity.level().playSound(null, vEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                player.setItemInHand(hand, stack);
                player.swing(hand, true);
            }
        } else {
            entity = event.getTarget();
            if (entity instanceof BoxEntity) {
                BoxEntity bEntity = (BoxEntity)entity;
                if (((Boolean)bEntity.getEntityData().get(BoxEntity.HAS_CLICKED)).booleanValue()) {
                    return;
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
                if (!event.getLevel().isClientSide()) {
                    bEntity.triggerOpen();
                    player.swing(hand, true);
                    bEntity.getEntityData().set(BoxEntity.HAS_CLICKED, true);
                    player.level().playSound(null, bEntity.blockPosition(), ModSounds.BOX_CLICK.get(), SoundSource.BLOCKS, 0.7f, 1.0f);
                    ModEvents.schedule(() -> {
                        Level level = event.getLevel();
                        VerityEntity verity = (VerityEntity)ModEntities.VERITY_ENTITY.get().spawn((ServerLevel)level, null, null, bEntity.blockPosition(), MobSpawnType.MOB_SUMMONED, true, true);
                        if (verity != null) {
                            verityEntity = verity;
                            verity.moveTo((double)bEntity.blockPosition().getX() + 0.5, bEntity.blockPosition().getY(), (double)bEntity.blockPosition().getZ() + 0.5, 0.0f, 0.0f);
                            verity.triggerBoxDrop();
                            ServerLevel verityLevel = (ServerLevel)verity.level();
                            verityLevel.sendParticles((ParticleOptions)ParticleTypes.CLOUD, bEntity.getX(), bEntity.getY() + 1.0, bEntity.getZ(), 20, 0.25, 0.25, 0.25, 0.02);
                        }
                        player.level().playSound(null, bEntity.blockPosition(), ModSounds.BOX_OPEN.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        bEntity.discard();
                    }, 50);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound(null, verityEntity.blockPosition(), ModSounds.IMPACT_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 65);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound(null, verityEntity.blockPosition(), ModSounds.IMPACT_0.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 85);
                    ModEvents.schedule(() -> {
                        if (verityEntity != null) {
                            verityEntity.level().playSound(null, verityEntity.blockPosition(), ModSounds.IMPACT_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                        }
                    }, 100);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerFirstJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        if (!player.getInventory().contains(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()))) {
            player.getInventory().add(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
        if (level2.dimension() != Level.OVERWORLD) {
            return;
        }
        WorldSpawnData data = WorldSpawnData.get(level2);
        if (!data.hasSpawnedEntity) {
            event.getEntity().sendSystemMessage((Component)Component.literal((String)"In order for Verity to have a voice, you must go to your Groq page and authorise the text to speech settings.").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
            BlockPos safeSpawnPos = ModEvents.findNearestLand(level2, player.blockPosition());
            ModEntities.BOX_ENTITY.get().spawn(level2, null, null, safeSpawnPos, MobSpawnType.MOB_SUMMONED, true, true);
            data.hasSpawnedEntity = true;
            data.setDirty();
            ModEvents.schedule(() -> {
                PlayerList playerList = player.getServer().getPlayerList();
                MutableComponent chatComponent = Component.literal((String)"When Verity kills you, you will be kicked. If you want to turn this off, go to config.").withStyle(ChatFormatting.RED);
                playerList.broadcastSystemMessage((Component)chatComponent, false);
            }, 1200);
            ModEvents.schedule(() -> {
                PlayerList playerList = player.getServer().getPlayerList();
                MutableComponent chatComponent = Component.literal((String)"To speak to Verity, include the word 'verity' in any part of your sentence in chat.\n\nYou can also change your keybind to use your voice to talk to him now!").withStyle(ChatFormatting.RED);
                playerList.broadcastSystemMessage((Component)chatComponent, false);
            }, 200);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        if (!event.getEntity().getInventory().contains(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()))) {
            event.getEntity().addItem(new ItemStack((ItemLike)ModItems.FLASHLIGHT.get()));
        }
    }

    private static BlockPos findNearestLand(ServerLevel level, BlockPos center) {
        int radius = 15;
        BlockPos bestPos = null;
        double shortestDist = Double.MAX_VALUE;
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                double dist;
                BlockPos searchPos = center.offset(x, 0, z);
                BlockPos topPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, searchPos);
                BlockPos groundPos = topPos.below();
                BlockState groundState = level.getBlockState(groundPos);
                if (!groundState.getFluidState().isEmpty() || !((dist = center.distSqr((Vec3i)topPos)) > 3.0) || !(dist < shortestDist)) continue;
                shortestDist = dist;
                bestPos = topPos;
            }
        }
        if (bestPos == null) {
            return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, center.offset(3, 0, 3));
        }
        return bestPos;
    }

    @SubscribeEvent
    public static void onPlayerBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand;
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(hand = event.getHand());
        if (stack.getItem() == ModItems.VERITY_ITEM.get()) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess((boolean)event.getLevel().isClientSide()));
            if (!event.getLevel().isClientSide()) {
                String variantToSpawn = "default";
                CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                if (customData.contains("VerityVariant")) {
                    variantToSpawn = customData.copyTag().getString("VerityVariant");
                }
                player.swing(hand, true);
                stack.shrink(1);
                player.level().playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 0.8f);
                Direction face = event.getFace();
                BlockPos spawnPos = face != null ? event.getPos().relative(face) : event.getPos().above();
                ServerLevel level = (ServerLevel)event.getLevel();
                VerityEntity spawnedVerity = (VerityEntity)ModEntities.VERITY_ENTITY.get().spawn(level, spawnPos, MobSpawnType.MOB_SUMMONED);
                if (spawnedVerity != null) {
                    spawnedVerity.setVariant(variantToSpawn);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        if (!hasSpawned) {
            return;
        }
        if (((Boolean)VerityConfig.REQUIRE_VERITY.get()).booleanValue() && !event.getRawText().toLowerCase().contains("verity")) {
            return;
        }
        String message = event.getMessage().getString();
        ServerPlayer player = event.getPlayer();
        if (verityEntity != null && !verityEntity.isRemoved()) {
            verityEntity.startTalking(80);
        }
        ServerLevel serverLevel = (ServerLevel)player.level();
        long currentDay = serverLevel.getDayTime() / 24000L;
        WorldSpawnData spawnData = WorldSpawnData.get(serverLevel);
        float currentKarma = spawnData.verityKarma;
        CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verityEntity, message, currentDay, currentKarma)).thenAccept(aiResponse -> {
            if (aiResponse == null || aiResponse.startsWith("Error")) {
                player.getServer().execute(() -> ModEvents.send(player, "AI connection error. You might need to replace your API Key."));
                return;
            }
            try {
                String cleanAiResponse = ModEvents.extractJson(aiResponse);
                JsonObject obj = JsonParser.parseString((String)cleanAiResponse).getAsJsonObject();
                if (obj.has("karma_change")) {
                    float karmaChange = obj.get("karma_change").getAsFloat();
                    player.getServer().execute(() -> {
                        WorldSpawnData data = WorldSpawnData.get(serverLevel);
                        data.verityKarma += karmaChange;
                        data.setDirty();
                    });
                }
                JsonArray actions = obj.has("actions") ? obj.getAsJsonArray("actions") : new JsonArray();
                boolean isJustAnswering = true;
                if (obj.has("action") && !obj.get("action").getAsString().equals("answer")) {
                    isJustAnswering = false;
                    JsonObject legacyAction = new JsonObject();
                    legacyAction.addProperty("action", obj.get("action").getAsString());
                    if (obj.has("args")) {
                        legacyAction.add("args", obj.get("args"));
                    }
                    actions.add((JsonElement)legacyAction);
                } else if (actions.size() > 0) {
                    for (int i = 0; i < actions.size(); ++i) {
                        if (actions.get(i).getAsJsonObject().get("action").getAsString().equals("answer")) continue;
                        isJustAnswering = false;
                        break;
                    }
                }
                if (isJustAnswering) {
                    String reply = obj.has("message") ? obj.get("message").getAsString() : "I'm not sure how to respond.";
                    String expression = obj.has("variant") && verityEntity != null ? obj.get("variant").getAsString() : "default";
                    player.getServer().execute(() -> {
                        if (verityEntity != null && !verityEntity.isRemoved()) {
                            verityEntity.setVariant(expression);
                            PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)verityEntity, (CustomPacketPayload)new PlayTtsPayload(verityEntity.getId(), reply), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        } else {
                            PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)player, (CustomPacketPayload)new PlayTtsPayload(player.getId(), reply), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        }
                        ModEvents.send(player, reply);
                    });
                    return;
                }
                player.getServer().execute(() -> {
                    StringBuilder combinedData = new StringBuilder();
                    StringBuilder toolsUsed = new StringBuilder();
                    for (int i = 0; i < actions.size(); ++i) {
                        Object data;
                        String action;
                        JsonObject actionObj = actions.get(i).getAsJsonObject();
                        String string = action = actionObj.has("action") ? actionObj.get("action").getAsString() : "answer";
                        if (action.equals("answer")) continue;
                        JsonObject args = actionObj.has("args") ? actionObj.getAsJsonObject("args") : new JsonObject();
                        switch (action) {
                            case "get_biome": {
                                Holder<Biome> biome = player.level().getBiome(player.blockPosition());
                                data = player.level().registryAccess().registryOrThrow(Registries.BIOME).getKey((Biome)biome.value()).getPath().replace("_", " ");
                                break;
                            }
                            case "get_coords": {
                                BlockPos p = player.blockPosition();
                                data = "X=" + p.getX() + " Y=" + p.getY() + " Z=" + p.getZ();
                                break;
                            }
                            case "get_inventory": {
                                ArrayList<String> items = new ArrayList<String>();
                                for (ItemStack inventoryStack : player.getInventory().items) {
                                    if (inventoryStack.isEmpty()) continue;
                                    items.add(inventoryStack.getCount() + "x " + inventoryStack.getHoverName().getString());
                                }
                                data = items.toString();
                                break;
                            }
                            case "get_nearby_entities": {
                                AABB box = player.getBoundingBox().inflate(ENTITY_RADIUS);
                                data = player.level().getEntitiesOfClass(LivingEntity.class, box, e -> e != player).stream().map(e -> e.getName().getString()).toList().toString();
                                break;
                            }
                            case "get_nearest_ore_location": {
                                String oreType = args.has("ore") ? args.get("ore").getAsString() : "diamond";
                                data = ModEvents.findNearestOre(player, oreType);
                                break;
                            }
                            case "get_nearest_village": {
                                BlockPos pos = player.blockPosition();
                                HolderSet.Named<Structure> villages = (HolderSet.Named)player.level().registryAccess().registryOrThrow(Registries.STRUCTURE).getTagOrEmpty(StructureTags.VILLAGE);
                                BlockPos nearestVillage = ((ServerLevel)player.level()).findNearestMapStructure(villages.key(), pos, 150, false);
                                if (nearestVillage == null) {
                                    data = "No village found within search range.";
                                    break;
                                }
                                data = "Nearest village at X=" + nearestVillage.getX() + " Y=~ Z=" + nearestVillage.getZ();
                                break;
                            }
                            case "get_own_coords": {
                                VerityEntity found = player.level().getEntitiesOfClass(VerityEntity.class, new AABB(player.blockPosition()).inflate(256.0)).stream().findFirst().orElse(null);
                                if (found == null) {
                                    data = "I don't know where I am right now.";
                                    break;
                                }
                                data = "My coords are: X=" + found.blockPosition().getX() + " Y=" + found.blockPosition().getY() + " Z=" + found.blockPosition().getZ();
                                break;
                            }
                            case "play_sound": {
                                String soundId = args.has("sound_id") ? args.get("sound_id").getAsString() : "minecraft:block.stone.place";
                                ResourceLocation soundLoc = ResourceLocation.parse((String)soundId);
                                SoundEvent sound = (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(soundLoc);
                                if (sound != null && verityEntity != null) {
                                    player.level().playSound(null, verityEntity.blockPosition(), sound, SoundSource.NEUTRAL, 1.0f, 1.0f);
                                    data = "Successfully played the sound.";
                                    break;
                                }
                                data = "Error: That sound does not exist or I am not in the world.";
                                break;
                            }
                            case "drop_item": {
                                String itemId = args.has("item_id") ? args.get("item_id").getAsString() : "minecraft:dirt";
                                int count = args.has("count") ? args.get("count").getAsInt() : 1;
                                ResourceLocation itemLoc = ResourceLocation.parse((String)itemId);
                                Item item = (Item)BuiltInRegistries.ITEM.get(itemLoc);
                                if (item != null && item != Items.AIR && verityEntity != null) {
                                    ItemStack dropStack = new ItemStack((ItemLike)item, count);
                                    ItemEntity droppedItem = new ItemEntity(player.level(), verityEntity.getX(), verityEntity.getY(), verityEntity.getZ(), dropStack);
                                    player.level().addFreshEntity((Entity)droppedItem);
                                    data = "Successfully dropped " + count + " " + itemId;
                                    break;
                                }
                                data = "Error: Item does not exist or I am not in the world.";
                                break;
                            }
                            case "play_favourite_song": {
                                if (verityEntity != null) {
                                    verityEntity.level().playSound(null, verityEntity.blockPosition(), (SoundEvent)ModSounds.VERITY_DISC_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                                    data = "Successfully played the favourite song.";
                                    break;
                                }
                                data = "Failed, not in world.";
                                break;
                            }
                            case "stop_favourite_song": {
                                MinecraftServer server = player.getServer();
                                ((GameRules.BooleanValue)server.getGameRules().getRule(GameRules.RULE_SENDCOMMANDFEEDBACK)).set(false, server);
                                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "/stopsound @a * verity:verity_disc");
                                data = "Successfully stopped the favourite song.";
                                break;
                            }
                            case "return_to_player": {
                                if (verityEntity == null || verityEntity.isRemoved()) {
                                    data = "No Verity found";
                                    break;
                                }
                                verityEntity.discard();
                                ItemStack retStack = new ItemStack((ItemLike)ModItems.VERITY_ITEM.get());
                                CustomData.update((DataComponentType)DataComponents.CUSTOM_DATA, (ItemStack)retStack, tag -> tag.putString("VerityVariant", verityEntity.getVariant()));
                                player.getInventory().add(retStack);
                                data = "Successfully to the player";
                                break;
                            }
                            case "get_block_player_is_looking_at": {
                                double reach = player.blockInteractionRange();
                                HitResult hitResult = player.pick(reach * 2.0, 0.0f, false);
                                if (hitResult.getType() == HitResult.Type.BLOCK) {
                                    BlockHitResult blockHitResult = (BlockHitResult)hitResult;
                                    BlockPos blockPos = blockHitResult.getBlockPos();
                                    BlockState blockState = player.level().getBlockState(blockPos);
                                    Block block = blockState.getBlock();
                                    data = block.getName().toString();
                                    break;
                                }
                                data = "Block out of reach";
                                break;
                            }
                            case "give_gift": {
                                Item dropItem;
                                WorldSpawnData dataInstance = WorldSpawnData.get(serverLevel);
                                float karma = dataInstance.verityKarma;
                                int dropCount = 1;
                                if (karma >= 15.0f) {
                                    dropItem = Items.EMERALD;
                                    dropCount = 3;
                                    data = "Dropped high-tier loot because they are very kind.";
                                } else if (karma >= 5.0f) {
                                    dropItem = Items.BREAD;
                                    dropCount = 5;
                                    data = "Dropped mid-tier food because they are nice.";
                                } else if (karma <= -5.0f) {
                                    dropItem = Items.ROTTEN_FLESH;
                                    data = "Dropped rotten flesh because they are rude.";
                                } else {
                                    dropItem = Items.DIRT;
                                    data = "Dropped dirt because the relationship is neutral.";
                                }
                                if (verityEntity != null) {
                                    ItemStack giftStack = new ItemStack((ItemLike)dropItem, dropCount);
                                    ItemEntity droppedEntity = new ItemEntity(player.level(), verityEntity.getX(), verityEntity.getY() + 0.5, verityEntity.getZ(), giftStack);
                                    player.level().addFreshEntity((Entity)droppedEntity);
                                    player.level().playSound(null, verityEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f);
                                    break;
                                }
                                data = "Error: I am not in the world to drop items.";
                                break;
                            }
                            default: {
                                data = "Tool not recognized.";
                            }
                        }
                        toolsUsed.append(action).append(", ");
                        combinedData.append(action).append(" returned: ").append((String)data).append("\n");
                    }
                    CompletableFuture.supplyAsync(() -> AiAPI.askGroq(verityEntity, "Player asked: %s\nTools used: %s\nData retrieved:\n%s\nTell the player this information naturally. YOU MUST STILL output your response as a VALID JSON OBJECT with the array 'actions' left empty.\n".formatted(message, toolsUsed.toString(), combinedData.toString()), currentDay, WorldSpawnData.get((ServerLevel)serverLevel).verityKarma)).thenAccept(response -> player.getServer().execute(() -> {
                        try {
                            String cleanResponse = ModEvents.extractJson(response);
                            JsonObject finalObj = JsonParser.parseString((String)cleanResponse).getAsJsonObject();
                            String reply = finalObj.get("message").getAsString();
                            if (verityEntity != null && !verityEntity.isRemoved()) {
                                if (finalObj.has("variant")) {
                                    verityEntity.setVariant(finalObj.get("variant").getAsString());
                                }
                                PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)verityEntity, (CustomPacketPayload)new PlayTtsPayload(verityEntity.getId(), reply), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            } else {
                                PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)player, (CustomPacketPayload)new PlayTtsPayload(player.getId(), reply), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            }
                            ModEvents.send(player, reply);
                        }
                        catch (Exception e) {
                            if (verityEntity != null) {
                                verityEntity.stopTalking();
                            }
                            ModEvents.send(player, "Error parsing final AI response.");
                            e.printStackTrace();
                        }
                    }));
                });
            }
            catch (Exception e) {
                player.getServer().execute(() -> ModEvents.send(player, "Failed to parse AI instruction."));
                e.printStackTrace();
            }
        });
    }

    private static String findNearestOre(ServerPlayer player, String type) {
        int r = 32;
        BlockPos center = player.blockPosition();
        BlockPos min = center.offset(-r, -r, -r);
        BlockPos max = center.offset(r, r, r);
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed((BlockPos)min, (BlockPos)max)) {
            double dist;
            boolean match;
            if (center.distSqr((Vec3i)pos) > (double)(r * r)) continue;
            BlockState state = player.level().getBlockState(pos);
            if (!(match = (switch (type.toLowerCase()) {
                case "diamond" -> state.is(BlockTags.DIAMOND_ORES);
                case "iron" -> state.is(BlockTags.IRON_ORES);
                case "gold" -> state.is(BlockTags.GOLD_ORES);
                case "coal" -> state.is(BlockTags.COAL_ORES);
                case "emerald" -> state.is(BlockTags.EMERALD_ORES);
                case "lapis" -> state.is(BlockTags.LAPIS_ORES);
                case "redstone" -> state.is(BlockTags.REDSTONE_ORES);
                case "copper" -> state.is(BlockTags.COPPER_ORES);
                default -> false;
            })) || !((dist = center.distSqr((Vec3i)pos)) < bestDist)) continue;
            bestDist = dist;
            best = pos.immutable();
        }
        if (best == null) {
            return "No " + type + " ore found nearby.";
        }
        return type + " ore at X=" + best.getX() + " Y=" + best.getY() + " Z=" + best.getZ();
    }

    private static void send(ServerPlayer player, String msg) {
        if (((String)msg).length() > 1500) {
            msg = ((String)msg).substring(0, 1500) + "...";
        }
        player.getServer().getPlayerList().broadcastSystemMessage((Component)Component.literal((String)("<Verity> " + (String)msg)), false);
    }

    private static String extractJson(String raw) {
        int start = raw.indexOf(123);
        int end = raw.lastIndexOf(125);
        if (start != -1 && end != -1 && end >= start) {
            return raw.substring(start, end + 1);
        }
        return raw;
    }

    private static class ScheduledTask {
        int ticksRemaining;
        Runnable task;

        ScheduledTask(Runnable task, int ticksRemaining) {
            this.task = task;
            this.ticksRemaining = ticksRemaining;
        }
    }
}

