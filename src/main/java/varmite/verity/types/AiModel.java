/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  varmite.verity.types.AiModel
 */
package varmite.verity.types;

public enum AiModel {
    FAST_LITE("Fast-lite"),
    FAST("Fast"),
    INTELLIGENT("Intelligent");

    private final String displayName;

    private AiModel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}

