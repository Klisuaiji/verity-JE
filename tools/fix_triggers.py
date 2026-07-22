#!/usr/bin/env python3
# Reconstruct the 8 trigger classes for NeoForge 1.21.1 SimpleCriterionTrigger API.
# In 1.21.1: SimpleCriterionTrigger<T extends SimpleInstance>; DeserializationContext removed;
# nested TriggerInstance must implement SimpleInstance and provide player().

import os

BASE = "src/main/java/varmite/verity/triggers"
triggers = {
    "BadKarmaTrigger": "badkarma",
    "FavoriteSongTrigger": "favoritesong",
    "GoodKarmaTrigger": "goodkarma",
    "KarmaChangeTrigger": "karmachange",
    "OpenBoxTrigger": "open_box",
    "PlaySoundTrigger": "playsound",
    "TalkTrigger": "talk",
    "VillageTrigger": "village",
}

TEMPLATE = '''package varmite.verity.triggers;

import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class {cls} extends SimpleCriterionTrigger<{cls}.TriggerInstance> {{
    public static final ResourceLocation ID = new ResourceLocation("verity", "{id}");

    @Override
    public ResourceLocation getId() {{
        return ID;
    }}

    @Override
    protected {cls}.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player) {{
        return new {cls}.TriggerInstance(player);
    }}

    public void trigger(ServerPlayer player) {{
        this.trigger(player, instance -> true);
    }}

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {{
        private final ContextAwarePredicate player;

        public TriggerInstance(ContextAwarePredicate player) {{
            this.player = player;
        }}

        @Override
        public Optional<ContextAwarePredicate> player() {{
            return Optional.of(this.player);
        }}
    }}
}}
'''

for cls, tid in triggers.items():
    path = os.path.join(BASE, f"{cls}.java")
    with open(path, "w", encoding="utf-8") as f:
        f.write(TEMPLATE.format(cls=cls, id=tid))
    print(f"rewrote {cls}.java")
