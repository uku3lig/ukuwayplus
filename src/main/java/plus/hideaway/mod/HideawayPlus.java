package plus.hideaway.mod;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import plus.hideaway.mod.feat.discord.DiscordManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plus.hideaway.mod.feat.keyboard.KeyboardManager;
import plus.hideaway.mod.feat.lifecycle.Lifecycle;
import plus.hideaway.mod.feat.lifecycle.Task;
import plus.hideaway.mod.feat.location.Location;
import plus.hideaway.mod.feat.settings.Settings;
import plus.hideaway.mod.feat.ws.WebSocketManager;
import plus.hideaway.mod.util.DisplayNameUtil;

@Environment(EnvType.CLIENT)
public class HideawayPlus implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("Hideaway+");
    private static boolean DEBUG = false;

    public static DiscordManager DISCORD_MANAGER;
    public static WebSocketManager WS_MANAGER;

    private static Location LOCATION = Location.UNKNOWN;
    private static Lifecycle LIFECYCLE;
    private static Settings SETTINGS;
    public static boolean ZOOM;

    private static Text CURRENT_GUI_TITLE;

    @Override
    public void onInitializeClient() {
        // Managers and services that do not need to be retained after
        // initialization, and/or be accessed by other services, should
        // be initialized here.
        LIFECYCLE = new Lifecycle();
        SETTINGS = new Settings();
        Settings.write();
        Settings.read();

        if (SETTINGS.rpc) DISCORD_MANAGER = new DiscordManager().start();
        if (SETTINGS.ws) WS_MANAGER = new WebSocketManager();

        // Managers and services that do not need to be retained after
        // initialization should be initialized here.
        new KeyboardManager();

        // Lifecycle tasks should be initialized here.
        lifecycle()
                .add(Task.of(Location::check, 20))
                .add(Task.of(() -> {
                    if (DiscordManager.active) DISCORD_MANAGER.update();
                }, 10))
                .add(Task.of(() -> {
                    if (connected() && !debug()) {
                        if (DisplayNameUtil.clientUsername().equals("sxpphic")) {
                            DEBUG = true;
                            Prompt.info("Debug mode enabled.");
                        }
                    }
                }, 60));
    }

    public static boolean connected() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            return MinecraftClient.getInstance().getCurrentServerEntry().address.endsWith("playhideaway.com");
        } else return false;
    }

    public static String version() {
        return String.valueOf(
            FabricLoader.getInstance().getModContainer("hideawayplus").get().getMetadata().getVersion()
        );
    }

    public static Logger logger() { return LOGGER; }
    public static boolean debug() { return DEBUG; }

    public static MinecraftClient client() { return MinecraftClient.getInstance(); }
    public static ClientPlayerEntity player() { return client().player; }

    public static DiscordManager discord() { return DISCORD_MANAGER; }
    public static Text gui() { return CURRENT_GUI_TITLE; }
    public static Lifecycle lifecycle() { return LIFECYCLE; }
    public static Location location() { return LOCATION; }
    public static WebSocketManager ws() { return WS_MANAGER; }
    public static Settings settings() { return SETTINGS; }

    public static void setLocation(Location l) { LOCATION = l; }
    public static void setGUI(Text t) { CURRENT_GUI_TITLE = t; }
}