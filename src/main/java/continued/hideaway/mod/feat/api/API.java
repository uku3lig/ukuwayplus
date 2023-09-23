package continued.hideaway.mod.feat.api;

import continued.hideaway.mod.HideawayPlus;

public class API {
    public static boolean serverUnreachable = false;
    public static boolean enabled = false;
    public static boolean living = false;
    public static String API_KEY = "";

    public static void tick() {
        if (!enabled || serverUnreachable) return;
        if (!living || API_KEY.isEmpty()) { QueryURL.asyncCreateUser(HideawayPlus.player().getStringUUID()); QueryURL.asyncPlayerList(); QueryURL.asyncModDev();}
    }

    public static void live() {
        if (!enabled || serverUnreachable) return;
        QueryURL.asyncLifePing(HideawayPlus.player().getStringUUID(), API_KEY);
        QueryURL.asyncPlayerList();
    }

    public static void end() {
        if (HideawayPlus.player() != null) QueryURL.asyncDestroy(HideawayPlus.player().getStringUUID(), API_KEY);
        enabled = false;
    }

    public static void modDev() {
        QueryURL.asyncModDev();
    }

    public static String uuidFromUsername(String playerName) {
        return QueryURL.asyncGetName(playerName);
    }
}
