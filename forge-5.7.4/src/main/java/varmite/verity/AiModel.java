/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity;

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
}

