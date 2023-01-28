package dev.abodactyl.cce;

import dev.abodactyl.cce.feat.discord.DiscordManager;
import dev.abodactyl.cce.feat.player.ModPlayerCache;
import dev.abodactyl.cce.feat.ws.WebSocketManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class CCEClient implements ClientModInitializer {

    private static final Logger LOGGER = LogManager.getLogger("ClubChunk Expanded");

    private static DiscordManager DISCORD_MANAGER;
    private static WebSocketManager WS_MANAGER;

    private static ModPlayerCache MOD_PLAYER_CACHE;

    @Override
    public void onInitializeClient() {
        DISCORD_MANAGER = new DiscordManager();

        // (WS server not yet implemented)
        // WS_MANAGER = new WebSocketManager();


        MOD_PLAYER_CACHE = new ModPlayerCache();
    }

    public static boolean connected() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            return MinecraftClient.getInstance().getCurrentServerEntry().address.endsWith("clubchunk.com");
        } else return false;
    }

    public static String version() {
        return String.valueOf(
            FabricLoader.getInstance().getModContainer("cce").get().getMetadata().getVersion()
        );
    }

    public static Logger logger() {
        return LOGGER;
    }

    public static ModPlayerCache playerCache() {
        return MOD_PLAYER_CACHE;
    }

    public static DiscordManager discord() {
        return DISCORD_MANAGER;
    }

    public static WebSocketManager ws() {
        return WS_MANAGER;
    }

}
