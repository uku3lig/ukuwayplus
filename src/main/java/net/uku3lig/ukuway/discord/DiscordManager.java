package net.uku3lig.ukuway.discord;

import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;

import java.time.Instant;

@Slf4j
public class DiscordManager {
    @Getter
    private static boolean active = false;
    private static IPCClient client;
    private static Instant start;

    public static void start() {
        if (!active && UkuwayConfig.get().isDiscordRPC()) {
            log.info("Starting Discord RPC client...");
            client = new IPCClient(1158161117915398214L);
            client.setListener(new ModIPCListener());

            try {
                client.connect();
                active = true;
                start = Instant.now();
                update();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.warn("An error happened while trying to connect to Discord", e);
            }
        }
    }

    public static void update() {
        if (active && UkuwayConfig.get().isDiscordRPC()) {
            RichPresence.Builder builder = new RichPresence.Builder();

            builder.setState("in the teawieverse")
                    .setDetails(UkuwayPlus.connected() ? "playing hideaway" : "clicking buttons")
                    .setStartTimestamp(start.getEpochSecond())
                    .setLargeImage(UkuwayPlus.connected() ? "hideaway" : "ukuway",
                            UkuwayPlus.findVersion().map(v -> "ukuway+ " + v).orElse("this person broke minecraft"))
                    .setSmallImage("https://mc-heads.net/avatar/" + MinecraftClient.getInstance().getSession().getUuid(), "made with spite & anger");

            client.sendRichPresence(builder.build());
        } else {
            start();
        }
    }

    public static void stop() {
        client.close();
        active = false;
    }

    private static class ModIPCListener implements IPCListener {

        @Override
        public void onPacketSent(IPCClient client, Packet packet) {
            // unneeded
        }

        @Override
        public void onPacketReceived(IPCClient client, Packet packet) {
            // unneeded
        }

        @Override
        public void onActivityJoin(IPCClient client, String secret) {
            // unneeded
        }

        @Override
        public void onActivitySpectate(IPCClient client, String secret) {
            // unneeded
        }

        @Override
        public void onActivityJoinRequest(IPCClient client, String secret, User user) {
            // unneeded
        }

        @Override
        public void onReady(IPCClient client) {
            UkuwayPlus.getLogger().info("Discord RPC client connected!");
        }

        @Override
        public void onClose(IPCClient client, JsonObject json) {
            UkuwayPlus.getLogger().info("Discord RPC client closed!");
        }

        @Override
        public void onDisconnect(IPCClient client, Throwable t) {
            // unneeded
        }
    }

    private DiscordManager() {
    }
}