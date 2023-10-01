package net.uku3lig.ukuway;

import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.discord.DiscordManager;
import net.uku3lig.ukuway.jukebox.Jukebox;
import net.uku3lig.ukuway.keyboard.KeyboardManager;
import net.uku3lig.ukuway.lifecycle.Lifecycle;
import net.uku3lig.ukuway.lifecycle.Task;
import net.uku3lig.ukuway.location.Location;
import net.uku3lig.ukuway.shop.Shop;
import net.uku3lig.ukuway.ui.FriendsListUI;
import net.uku3lig.ukuway.wardrobe.Wardrobe;
import net.uku3lig.ukuway.util.Constants;
import net.uku3lig.ukuway.util.StaticValues;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.uku3lig.ukulib.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.uku3lig.ukuway.util.StaticValues.friendsCheck;
import static net.uku3lig.ukuway.util.StaticValues.friendsUUID;

@Environment(EnvType.CLIENT)
public class HideawayPlus implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(Constants.MOD_NAME);

    @Getter
    private static final ConfigManager<UkuwayConfig> manager = ConfigManager.create(UkuwayConfig.class, "ukuway-plus");

    private static final Lifecycle LIFECYCLE = new Lifecycle();
    public static final DiscordManager DISCORD_MANAGER = new DiscordManager();
    public static final Jukebox JUKEBOX = new Jukebox();
    public static final Shop SHOP = new Shop();

    private static Location LOCATION = Location.UNKNOWN;

    @Override
    public void onInitializeClient() {
        Constants.MOD_MENU_PRESENT = FabricLoader.getInstance().isModLoaded("modmenu");

        // Managers and services that need to be retained after
        // initialization, and/or be accessed by other services, should
        // be initialized here.

        // Managers and services that do not need to be retained after
        // initialization should be initialized here.
        new KeyboardManager(); // FIXME DUUUUUUUUUUUUUUUDEEEEEEEEEDKZQJ IDJZQIODJZIOQDJIOQZDIOZQJDOIJDJZ

        try {
            if (manager.getConfig().isDiscordRPC()) DISCORD_MANAGER.start();
        } catch (Error err) {
            HideawayPlus.logger().info(err);
            return;
        }

        // Lifecycle tasks should be initialized here.
        lifecycle()
                .add(Task.of(Location::check, 20))
                .add(Task.of(() -> {
                    try {
                        if (DiscordManager.active) DISCORD_MANAGER.update();
                        if (DiscordManager.active && !manager.getConfig().isDiscordRPC()) DISCORD_MANAGER.stop();
                        if (!DiscordManager.active && manager.getConfig().isDiscordRPC()) DISCORD_MANAGER.start();
                    } catch (Error err) {
                        HideawayPlus.logger().error(err);
                    }

                }, 10))
                .add(Task.of(() -> {
                    if (HideawayPlus.connected() && MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen) {
                        HideawayPlus.shop().tick();
                    } else if (StaticValues.shopScreenWasFilled) {
                        HideawayPlus.shop().oldShopName = null;
                        StaticValues.shopIterationNum = 0;
                        StaticValues.shopScreenWasFilled = false;
                    }
                }, 0))
                .add(Task.of(() -> {
                    if (HideawayPlus.connected() && (!friendsCheck || MinecraftClient.getInstance().currentScreen == null || friendsUUID.isEmpty())) {
                        FriendsListUI.tick();
                    }
                }, 0))
                .add(Task.of(Wardrobe::tick, 0));
    }

    public static boolean connected() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            return MinecraftClient.getInstance().getCurrentServerEntry().address.endsWith("playhideaway.com");
        } else return false;
    }

    public static String version() {
        return String.valueOf(
                FabricLoader.getInstance().getModContainer(Constants.MOD_ID).get().getMetadata().getVersion()
        );
    }

    public static Logger logger() {
        return LOGGER;
    }

    public static DiscordManager discord() {
        return DISCORD_MANAGER;
    }

    public static Jukebox jukebox() {
        return JUKEBOX;
    }

    public static Shop shop() {
        return SHOP;
    }

    public static Lifecycle lifecycle() {
        return LIFECYCLE;
    }

    public static Location location() {
        return LOCATION;
    }

    public static void setLocation(Location l) {
        LOCATION = l;
    }
}