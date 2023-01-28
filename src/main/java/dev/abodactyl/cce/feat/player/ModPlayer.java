package dev.abodactyl.cce.feat.player;

import java.util.UUID;

public class ModPlayer {

    public String name;
    public UUID uuid;
    public CCERank rank = CCERank.NOT;

    public ModPlayer(String n, UUID u, CCERank r) {
        name = n;
        uuid = u;
        rank = r;
    }

    public enum CCERank {
        ADMIN,
        MODERATOR,
        PLAYER,
        NOT;

        public static CCERank fromInt(int x) {
            switch (x) {
                case 3 -> {
                    return ADMIN;
                }
                case 2 -> {
                    return MODERATOR;
                }
                case 1 -> {
                    return PLAYER;
                }
                default -> {
                    return NOT;
                }
            }
        }
    }

}
