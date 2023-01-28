package dev.abodactyl.cce.feat.player;

import com.google.gson.Gson;
import dev.abodactyl.cce.CCEClient;
import dev.abodactyl.cce.feat.ws.GetModPlayerPacket;

import java.util.*;

public class ModPlayerCache {

    private final List<ModPlayer> PLAYERS = new ArrayList<>();
    private final List<String> WAITING = new ArrayList<>();

    public boolean inCache(String player) {
        return PLAYERS.stream().anyMatch(pl -> Objects.equals(pl.name, player));
    }

    public ModPlayer get(String name) {
//        if (inCache(name)) {
//            WAITING.remove(name);
//            return getFromCache(name);
//        }
//        else if (WAITING.contains(name)) return get(name);
//        else {
//            CCEClient.ws().send(new GetModPlayerPacket(name));
//            WAITING.add(name);
//            return get(name);
//        }


        // Temporary solution until WS server is set up
        PLAYERS.add(
            new ModPlayer(
                name,
                UUID.randomUUID(),
                ModPlayer.CCERank.fromInt(new Random().nextInt(3))
            )
        );

        return getFromCache(name);
    }

    private ModPlayer getFromCache(String name) {
        List<ModPlayer> l = PLAYERS.stream().filter(pl -> Objects.equals(pl.name, name)).toList();
        if (l.size() > 0) return l.get(0);
        else return null;
    }

    public void put(ModPlayer player) {
        PLAYERS.add(player);
    }

}
