/*
 * Decompiled with CFR 0.152.
 */
package varmite.verity.network;

public class ClientKarmaData {
    private static int playerKarma;

    public static void set(int karma) {
        playerKarma = karma;
    }

    public static int getPlayerKarma() {
        return playerKarma;
    }
}

