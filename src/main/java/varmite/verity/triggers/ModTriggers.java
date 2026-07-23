package varmite.verity.triggers;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import varmite.verity.Verity;

public class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, Verity.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, OpenBoxTrigger> UNBOX_VERITY_TRIGGER =
            TRIGGERS.register("openbox", OpenBoxTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, TalkTrigger> TALK_TRIGGER =
            TRIGGERS.register("talk", TalkTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, VillageTrigger> VILLAGE_TRIGGER =
            TRIGGERS.register("village", VillageTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, KarmaChangeTrigger> KARMA_CHANGE_TRIGGER =
            TRIGGERS.register("karmachange", KarmaChangeTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, GoodKarmaTrigger> GOOD_KARMA_TRIGGER =
            TRIGGERS.register("goodkarma", GoodKarmaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, BadKarmaTrigger> BAD_KARMA_TRIGGER =
            TRIGGERS.register("badkarma", BadKarmaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, FavoriteSongTrigger> FAVORITE_SONG_TRIGGER =
            TRIGGERS.register("favoritesong", FavoriteSongTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, PlaySoundTrigger> PLAY_SOUND_TRIGGER =
            TRIGGERS.register("playsound", PlaySoundTrigger::new);

    public static void register(IEventBus modEventBus) {
        TRIGGERS.register(modEventBus);
    }
}
