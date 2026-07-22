/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.advancements.critereon.DeserializationContext
 *  net.minecraft.advancements.critereon.SimpleCriterionTrigger
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  varmite.verity.triggers.OpenBoxTrigger
 *  varmite.verity.triggers.OpenBoxTrigger$TriggerInstance
 */
package varmite.verity.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import varmite.verity.triggers.OpenBoxTrigger;

public class OpenBoxTrigger
extends SimpleCriterionTrigger<TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation("verity", "open_box");

    public ResourceLocation getId() {
        return ID;
    }

    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.addPlayerListener(player, instance -> true);
    }
}

