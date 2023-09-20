package plus.hideaway.mod;

import plus.hideaway.mod.feat.config.HideawayPlusConfig;
import plus.hideaway.mod.feat.discord.DiscordManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.hideaway.mod.feat.jukebox.Jukebox;
import plus.hideaway.mod.feat.keyboard.KeyboardManager;
import plus.hideaway.mod.feat.lifecycle.Lifecycle;
import plus.hideaway.mod.feat.lifecycle.Task;
import plus.hideaway.mod.feat.location.Location;

import java.util.LinkedList;
import java.util.Queue;

@Environment(EnvType.CLIENT)
public class HideawayPlus implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("Hideaway+");

    public static DiscordManager DISCORD_MANAGER;
    public static Jukebox JUKEBOX;

    private static final HideawayPlusConfig CONFIG = HideawayPlusConfig.createAndLoad();
    private static Location LOCATION = Location.UNKNOWN;
    private static Lifecycle LIFECYCLE;

    private static final Queue<Component> TOAST_STACK = new LinkedList<>();

    @Override
    public void onInitializeClient() {
        // Managers and services that need to be retained after
        // initialization, and/or be accessed by other services, should
        // be initialized here.
        LIFECYCLE = new Lifecycle();

        // Managers and services that do not need to be retained after
        // initialization should be initialized here.
        new KeyboardManager();

        if (config().discordRPC()) DISCORD_MANAGER = new DiscordManager().start();
        if (config().jukebox()) JUKEBOX = new Jukebox();

        // Lifecycle tasks should be initialized here.
        lifecycle()
                .add(Task.of(Location::check, 20))
                .add(Task.of(() -> {
                    if (DiscordManager.active) DISCORD_MANAGER.update();
                }, 10));
    }

    public static boolean connected() {
        if (Minecraft.getInstance().getCurrentServer() != null) {
            return Minecraft.getInstance().getCurrentServer().ip.endsWith("playhideaway.com");
        } else return false;
    }

    public static String version() {
        return String.valueOf(
            FabricLoader.getInstance().getModContainer("hideawayplus").get().getMetadata().getVersion()
        );
    }

    public static Logger logger() { return LOGGER; }
    public static boolean debug() { return player().getName().getString().equals("sxpphic"); }
    public static Minecraft client() { return Minecraft.getInstance(); }
    public static LocalPlayer player() { return client().player; }
    public static Queue<Component> toastStack() { return TOAST_STACK; }

    public static HideawayPlusConfig config() { return CONFIG; }
    public static DiscordManager discord() { return DISCORD_MANAGER; }
    public static Jukebox jukebox() { return JUKEBOX; }
    public static Lifecycle lifecycle() { return LIFECYCLE; }
    public static Location location() { return LOCATION; }

    public static void setLocation(Location l) { LOCATION = l; }
}