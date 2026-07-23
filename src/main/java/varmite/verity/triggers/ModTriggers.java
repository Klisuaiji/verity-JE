/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.advancements.CriterionTrigger
 *  varmite.verity.triggers.BadKarmaTrigger
 *  varmite.verity.triggers.FavoriteSongTrigger
 *  varmite.verity.triggers.GoodKarmaTrigger
 *  varmite.verity.triggers.KarmaChangeTrigger
 *  varmite.verity.triggers.ModTriggers
 *  varmite.verity.triggers.OpenBoxTrigger
 *  varmite.verity.triggers.PlaySoundTrigger
 *  varmite.verity.triggers.TalkTrigger
 *  varmite.verity.triggers.VillageTrigger
 */
package varmite.verity.triggers;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import varmite.verity.triggers.BadKarmaTrigger;
import varmite.verity.triggers.FavoriteSongTrigger;
import varmite.verity.triggers.GoodKarmaTrigger;
import varmite.verity.triggers.KarmaChangeTrigger;
import varmite.verity.triggers.OpenBoxTrigger;
import varmite.verity.triggers.PlaySoundTrigger;
import varmite.verity.triggers.TalkTrigger;
import varmite.verity.triggers.VillageTrigger;

public class ModTriggers {
    public static final OpenBoxTrigger UNBOX_VERITY_TRIGGER = CriteriaTriggers.register(OpenBoxTrigger.ID.toString(), new OpenBoxTrigger());
    public static final TalkTrigger TALK_TRIGGER = CriteriaTriggers.register(TalkTrigger.ID.toString(), new TalkTrigger());
    public static final VillageTrigger VILLAGE_TRIGGER = CriteriaTriggers.register(VillageTrigger.ID.toString(), new VillageTrigger());
    public static final KarmaChangeTrigger KARMA_CHANGE_TRIGGER = CriteriaTriggers.register(KarmaChangeTrigger.ID.toString(), new KarmaChangeTrigger());
    public static final GoodKarmaTrigger GOOD_KARMA_TRIGGER = CriteriaTriggers.register(GoodKarmaTrigger.ID.toString(), new GoodKarmaTrigger());
    public static final BadKarmaTrigger BAD_KARMA_TRIGGER = CriteriaTriggers.register(BadKarmaTrigger.ID.toString(), new BadKarmaTrigger());
    public static final FavoriteSongTrigger FAVORITE_SONG_TRIGGER = CriteriaTriggers.register(FavoriteSongTrigger.ID.toString(), new FavoriteSongTrigger());
    public static final PlaySoundTrigger PLAY_SOUND_TRIGGER = CriteriaTriggers.register(PlaySoundTrigger.ID.toString(), new PlaySoundTrigger());
}

