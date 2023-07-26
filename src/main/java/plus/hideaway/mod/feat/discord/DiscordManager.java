package plus.hideaway.mod.feat.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import org.json.JSONObject;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.location.Location;
import plus.hideaway.mod.feat.settings.Settings;

import java.time.Instant;
import java.time.ZoneOffset;

public class DiscordManager {
    public static boolean active = false;
    private static IPCClient client;
    private static Instant start;

    public DiscordManager start() {
        if (!active) {
            HideawayPlus.logger().info("Starting Discord RPC client...");
            client = new IPCClient(917049315288047616L);
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    HideawayPlus.logger().info("Discord RPC client connected!");
                    active = true;
                    start = Instant.now();
                    update();
                }

                @Override
                public void onDisconnect(IPCClient client, Throwable t) {
                    active = false;
                }

                @Override
                public void onClose(IPCClient client, JSONObject j) {
                    active = false;
                }
            });

            try {
                client.connect();
            } catch (Exception ignored) {}
        }
        return this;
    }

    public void update() {
        if (active) {
            Location loc = HideawayPlus.location();
            RichPresence.Builder builder = new RichPresence.Builder();
            builder.setState(loc.description)
                    .setDetails(loc.name)
                    .setStartTimestamp(Instant.ofEpochSecond(start.toEpochMilli()).atOffset(ZoneOffset.UTC))
                    .setLargeImage(loc.largeIcon.key(), loc.name)
                    .setSmallImage(loc.smallIcon.key(), "Try Hideaway+ today!");
            client.sendRichPresence(builder.build());
        } else start();
    }
}