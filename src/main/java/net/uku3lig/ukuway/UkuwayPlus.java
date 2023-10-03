package net.uku3lig.ukuway;

import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.uku3lig.ukulib.config.ConfigManager;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.discord.DiscordManager;
import net.uku3lig.ukuway.jukebox.Jukebox;
import net.uku3lig.ukuway.keyboard.KeyboardManager;
import net.uku3lig.ukuway.shop.Shop;
import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.wardrobe.Wardrobe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class UkuwayPlus implements ClientModInitializer {
    @Getter
    private static final ConfigManager<UkuwayConfig> manager = ConfigManager.create(UkuwayConfig.class, "ukuway-plus");

    @Getter
    private static final Logger logger = LoggerFactory.getLogger(UkuwayPlus.class);

    @Getter
    public static final Jukebox jukebox = new Jukebox();

    @Getter
    public static final Shop shop = new Shop();

    public static final String PUBLIC_BUKKIT_VALUES = "PublicBukkitValues";

    private long ticksElapsed = 0;

    @Override
    public void onInitializeClient() {
        KeyboardManager.register();

        if (UkuwayConfig.get().isDiscordRPC()) DiscordManager.start();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (UkuwayPlus.connected()) {
                jukebox.tick();
                shop.tick();
                Wardrobe.tick();

                if (!FriendListManager.isInit() || client.currentScreen instanceof GenericContainerScreen || FriendListManager.getFriends().isEmpty()) {
                    FriendListManager.tick();
                }
            }

            if (ticksElapsed % 200 == 0) {
                try {
                    DiscordManager.update();
                } catch (Exception e) {
                    logger.warn("An error occurred while updating Discord RPC", e);
                }
            }

            ticksElapsed++;
        });
    }

    public static boolean connected() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            return MinecraftClient.getInstance().getCurrentServerEntry().address.endsWith("playhideaway.com");
        } else {
            return false;
        }
    }

    public static Optional<String> findVersion() {
        return FabricLoader.getInstance().getModContainer("ukuway-plus").map(container -> container.getMetadata().getVersion().getFriendlyString());
    }
}