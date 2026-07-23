package varmite.verity.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.CriterionValidator;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class VillageTrigger extends SimpleCriterionTrigger<VillageTrigger.TriggerInstance> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("verity", "village");
    public ResourceLocation getId() { return ID; }

    @Override
    public Codec codec() {
        return TriggerInstance.CODEC;
    }


    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = MapCodec.unit(new TriggerInstance(ContextAwarePredicate.create())).codec();
        private final ContextAwarePredicate player;

        public TriggerInstance(ContextAwarePredicate player) {
            this.player = player;
        }

        @Override
        public void validate(CriterionValidator validator) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.of(this.player);
        }
    }
}
