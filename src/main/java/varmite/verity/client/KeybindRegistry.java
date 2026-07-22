/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  net.minecraft.client.KeyMapping
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.common.EventBusSubscriber$Bus
 *  net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
 *  net.neoforged.neoforge.client.settings.IKeyConflictContext
 *  net.neoforged.neoforge.client.settings.KeyConflictContext
 */
package varmite.verity.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@EventBusSubscriber(modid="verity", bus=EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class KeybindRegistry {
    public static final KeyMapping PUSH_TO_TALK = new KeyMapping("key.verity.push_to_talk", (IKeyConflictContext)KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, 86, "category.verity.keys");
    public static final KeyMapping CYCLE_MIC = new KeyMapping("key.verity.cycle_mic", (IKeyConflictContext)KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, 77, "category.verity.keys");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(PUSH_TO_TALK);
        event.register(CYCLE_MIC);
    }
}

