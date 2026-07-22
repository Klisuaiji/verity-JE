/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.entity.client;

public class VerityAnimation {
    public static float getYOffset(float tick) {
        if (tick < 0.0f || tick > 50.0f) {
            return 0.0f;
        }
        if (tick <= 12.0f) {
            float p = tick / 12.0f;
            return 1.0f - p * p * p;
        }
        if (tick <= 30.0f) {
            float p = (tick - 12.0f) / 18.0f;
            return 4.0f * p * (1.0f - p) * 0.5f;
        }
        if (tick <= 42.0f) {
            float p = (tick - 30.0f) / 12.0f;
            return 4.0f * p * (1.0f - p) * 0.15f;
        }
        return 0.0f;
    }

    public static float getScaleY(float tick) {
        if (tick < 0.0f || tick > 50.0f) {
            return 1.0f;
        }
        if (tick <= 12.0f) {
            float p = tick / 12.0f;
            return 1.0f + p * p * 0.3f;
        }
        if (tick <= 18.0f) {
            float p = (tick - 12.0f) / 6.0f;
            return 1.0f - (float)Math.sin((double)p * Math.PI) * 0.6f;
        }
        if (tick >= 30.0f && tick <= 36.0f) {
            float p = (tick - 30.0f) / 6.0f;
            return 1.0f - (float)Math.sin((double)p * Math.PI) * 0.3f;
        }
        if (tick >= 42.0f && tick <= 46.0f) {
            float p = (tick - 42.0f) / 4.0f;
            return 1.0f - (float)Math.sin((double)p * Math.PI) * 0.1f;
        }
        return 1.0f;
    }

    public static float getScaleXZ(float tick) {
        float scaleY = VerityAnimation.getScaleY(tick);
        return 1.0f + (1.0f - scaleY) * 0.75f;
    }
}

