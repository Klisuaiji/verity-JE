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
    public static final OpenBoxTrigger UNBOX_VERITY_TRIGGER = (OpenBoxTrigger)CriteriaTriggers.register((CriterionTrigger)new OpenBoxTrigger());
    public static final TalkTrigger TALK_TRIGGER = (TalkTrigger)CriteriaTriggers.register((CriterionTrigger)new TalkTrigger());
    public static final VillageTrigger VILLAGE_TRIGGER = (VillageTrigger)CriteriaTriggers.register((CriterionTrigger)new VillageTrigger());
    public static final KarmaChangeTrigger KARMA_CHANGE_TRIGGER = (KarmaChangeTrigger)CriteriaTriggers.register((CriterionTrigger)new KarmaChangeTrigger());
    public static final GoodKarmaTrigger GOOD_KARMA_TRIGGER = (GoodKarmaTrigger)CriteriaTriggers.register((CriterionTrigger)new GoodKarmaTrigger());
    public static final BadKarmaTrigger BAD_KARMA_TRIGGER = (BadKarmaTrigger)CriteriaTriggers.register((CriterionTrigger)new BadKarmaTrigger());
    public static final FavoriteSongTrigger FAVORITE_SONG_TRIGGER = (FavoriteSongTrigger)CriteriaTriggers.register((CriterionTrigger)new FavoriteSongTrigger());
    public static final PlaySoundTrigger PLAY_SOUND_TRIGGER = (PlaySoundTrigger)CriteriaTriggers.register((CriterionTrigger)new PlaySoundTrigger());
}

