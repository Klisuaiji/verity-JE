/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.advancements.CriterionTrigger
 */
package varmite.verity.triggers;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import varmite.verity.triggers.BadDayTrigger;
import varmite.verity.triggers.BadKarmaTrigger;
import varmite.verity.triggers.FavoriteSongTrigger;
import varmite.verity.triggers.GoodKarmaTrigger;
import varmite.verity.triggers.KarmaChangeTrigger;
import varmite.verity.triggers.OpenBoxTrigger;
import varmite.verity.triggers.PlaySoundTrigger;
import varmite.verity.triggers.TalkTrigger;
import varmite.verity.triggers.ThreeDaysTrigger;
import varmite.verity.triggers.VillageTrigger;

public class ModTriggers {
    public static final OpenBoxTrigger UNBOX_VERITY_TRIGGER = (OpenBoxTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new OpenBoxTrigger());
    public static final TalkTrigger TALK_TRIGGER = (TalkTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new TalkTrigger());
    public static final VillageTrigger VILLAGE_TRIGGER = (VillageTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new VillageTrigger());
    public static final KarmaChangeTrigger KARMA_CHANGE_TRIGGER = (KarmaChangeTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new KarmaChangeTrigger());
    public static final GoodKarmaTrigger GOOD_KARMA_TRIGGER = (GoodKarmaTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new GoodKarmaTrigger());
    public static final BadKarmaTrigger BAD_KARMA_TRIGGER = (BadKarmaTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new BadKarmaTrigger());
    public static final FavoriteSongTrigger FAVORITE_SONG_TRIGGER = (FavoriteSongTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new FavoriteSongTrigger());
    public static final PlaySoundTrigger PLAY_SOUND_TRIGGER = (PlaySoundTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new PlaySoundTrigger());
    public static final BadDayTrigger BAD_DAY_TRIGGER = (BadDayTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new BadDayTrigger());
    public static final ThreeDaysTrigger THREE_DAYS_TRIGGER = (ThreeDaysTrigger)CriteriaTriggers.m_10595_((CriterionTrigger)new ThreeDaysTrigger());
}

