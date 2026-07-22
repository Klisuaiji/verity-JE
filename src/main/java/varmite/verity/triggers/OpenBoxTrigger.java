package varmite.verity.triggers;

import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class OpenBoxTrigger extends SimpleCriterionTrigger<OpenBoxTrigger.TriggerInstance> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("verity", "open_box");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected OpenBoxTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player) {
        return new OpenBoxTrigger.TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {
        private final ContextAwarePredicate player;

        public TriggerInstance(ContextAwarePredicate player) {
            this.player = player;
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.of(this.player);
        }
    }
}
