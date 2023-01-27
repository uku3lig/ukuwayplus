package dev.abodactyl.cce.feat.discord;

public class DiscordManager {

    public enum State {
        // L_ = Lobby
        // G_ = Game / in-Game
        // PG_ = Party Games
        // otherwise Misc.

        L_TOWN(),
        L_BUSINESS_BOULEVARD(),
        L_DOJO(),

        G_HOT_POTATO(),
        G_SPLEEF(),
        G_MUSICAL_CHAIRS(),
        G_TNT_RUN(),
        G_SKYWARS(),

        PG_LOBBY(),
        PG_HOT_POTATO(),
        PG_SPLEEF(),
        PG_MUSICAL_CHAIRS(),
        PG_TNT_RUN(),
        PG_GAMES_SKYWARS(),

        UNKNOWN()
    }

    public DiscordManager() {
    	
    }

}
