/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.types;

import java.util.Map;

public enum TTSLanguage {
    English,
    German;

    private static final Map<TTSLanguage, String> LANG_CODES;

    public String getLangCode() {
        return LANG_CODES.get((Object)this);
    }

    static {
        LANG_CODES = Map.ofEntries(Map.entry(English, "en"), Map.entry(German, "de"));
    }
}

