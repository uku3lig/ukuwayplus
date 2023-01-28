package dev.abodactyl.cce.feat.ws;

import com.google.gson.*;
import dev.abodactyl.cce.CCEClient;
import dev.abodactyl.cce.feat.player.ModPlayer;
import net.minecraft.client.MinecraftClient;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class WebSocketManager {

    public static final String URI = "ws://gateway.cce.fyi:3400";
    private static WebSocketClient CLIENT;

    private void start() throws URISyntaxException {
        CLIENT = new WebSocketClient(new URI(URI), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PlayerUuid", MinecraftClient.getInstance().getSession().getUuid());
                jsonObject.addProperty("CCEVersion", CCEClient.version());
                send(jsonObject.toString());
            }

            @Override
            public void onMessage(String message) {
                if (message.equals("error: invalid uuid")) {
                    CCEClient.logger().error("WebSocket connection reported invalid UUID. Closing CCE websocket connection...");
                    CLIENT.close();
                    return;
                }

                JsonArray obj = JsonParser.parseString(message).getAsJsonArray();

                for (JsonElement user : obj) {
                    JsonObject userObject = user.getAsJsonObject();
                    String username = userObject.get("username").getAsString();
                    UUID uuid = UUID.fromString(userObject.get("rank").getAsString());
                    ModPlayer.CCERank rank = ModPlayer.CCERank.valueOf(userObject.get("rank").getAsString());

                    if (!CCEClient.playerCache().inCache(username)) {
                        CCEClient.playerCache().put(new ModPlayer(username, uuid, rank));
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {}

            @Override
            public void onError(Exception ex) {
                CCEClient.logger().error(ex);
                CCEClient.logger().error("WebSocket error occured. Closing CCE websocket connection...");
                CLIENT.close();
            }
        };
    }

    public void open() {
        if (CLIENT == null) {
            try {
                start();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(IPacket p) {
        if (CLIENT != null) {
            CLIENT.send(new Gson().toJson(p));
        }
    }

}
