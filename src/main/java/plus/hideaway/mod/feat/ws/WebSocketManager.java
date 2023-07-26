package plus.hideaway.mod.feat.ws;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import plus.hideaway.mod.Prompt;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.lifecycle.Task;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketManager {

    private static final String WEBSOCKET_URL = "ws://localhost:4738";
    private static int crashCount = 0;
    private static boolean connected = false;
    private static boolean willReconnect = true;

    private static WebSocketClient client;

    public WebSocketManager() {
        try {
            connect();
        } catch (Exception e) {
            HideawayPlus.logger().error("Error occurred when intializing WebSocket client:");
            HideawayPlus.logger().error(e);
            e.printStackTrace();
        }
    }

    public boolean connected() {
        return connected;
    }
    public boolean willReconnect() {
        return willReconnect;
    }

    public void end() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "leave");
        jsonObject.addProperty("uuid", MinecraftClient.getInstance().getSession().getUuid());
        client.send(jsonObject.toString());
    }

    private void connect() throws URISyntaxException {
        client = new WebSocketClient(new URI(WEBSOCKET_URL), new Draft_6455()) {

            @Override
            public void onOpen(ServerHandshake handshake) {
                Prompt.trace("Connected to WebSocket.");
                HideawayPlus.logger().info("Connected to WebSocket server.");
                connected = true;

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "join");
                jsonObject.addProperty("uuid", MinecraftClient.getInstance().getSession().getUuid());
                jsonObject.addProperty("version", HideawayPlus.version());
                client.send(jsonObject.toString());
            }

            @Override
            public void onMessage(String message) {
                try {
                    JsonObject obj = JsonParser.parseString(message).getAsJsonObject();
                    String action = obj.get("action").getAsString();
                    switch (action) {
                        case "error" -> {
                            String msg = obj.get("message").getAsString();
                            switch (msg) {
                                case "Invalid UUID" -> {
                                    HideawayPlus.logger().error("Invalid UUID found, please restart client.");
                                    HideawayPlus.lifecycle().addOneOff(Task.of(() -> {
                                        Prompt.error("It seems like your UUID is invalid! This means that several Hideaway+ services may not function, as well as server-side and client-side features. We recommend restarting your client to fix the issue.");
                                    }, 0));
                                }
                            }
                        }
                        case "outage" -> {
                            String head = obj.get("heading").getAsString();
                            String desc = obj.get("description").getAsString();
                            OutageSeverity sev = OutageSeverity.valueOf(obj.get("severity").getAsString());
                            Prompt.outage(head, desc, sev);
                        }
//                        case "users" -> {
//                            JsonArray jsonArray = obj.get("list").getAsJsonArray();
//
//                            ModUserCache.clear();
//                            for (JsonElement user : jsonArray) {
//                                String username = user.getAsString();
//                                ModUserCache.addUser(username);
//                            }
//                        }
                    }
                } catch (Exception ignored) {}
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                connected = false;
                crashCount++;

                ModUserCache.clear();
                if (crashCount < 5) {
                    Prompt.trace("Disconnected from WebSocket, reconnecting...");
                    new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                            this.reconnect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    willReconnect = false;
                    Prompt.trace("Failed to reconnect to WebSocket.");
                    HideawayPlus.logger().error("Disconnected from WebSocket server 5 times, halting reconnection attempts...");
                    HideawayPlus.lifecycle().addOneOff(Task.of(() -> {
                        Prompt.error("Whoops! It seems like the core socket server for Hideaway+ is unreachable. Please contact us on our Discord for support.");
                    }, 0));

                }
            }

            @Override
            public void onError(Exception ex) {
                HideawayPlus.logger().error("Error occurred in WebSocket client:");
                HideawayPlus.logger().error(ex);
                ex.printStackTrace();
            }
        };
        client.connect();
    }

    public enum OutageSeverity { MILD, MODERATE, SEVERE }

}
