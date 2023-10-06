package net.uku3lig.ukuway.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.util.DiscordManager;

public class UkuwayConfigScreen extends AbstractConfigScreen<UkuwayConfig> {
    public UkuwayConfigScreen(Screen parent) {
        super(parent, Text.of("Ukuway Config"), UkuwayPlus.getManager());
    }

    @Override
    protected SimpleOption<?>[] getOptions(UkuwayConfig config) {
        return new SimpleOption[]{
                SimpleOption.ofBoolean("ukuway.config.hideCosmetics", config.isHideCosmetics(), config::setHideCosmetics),
                SimpleOption.ofBoolean("ukuway.config.discordRPC", config.isDiscordRPC(), b -> {
                    config.setDiscordRPC(b);
                    DiscordManager.setStatus(b);
                }),
                SimpleOption.ofBoolean("ukuway.config.inventoryRarities", config.isInventoryRarities(), config::setInventoryRarities),
                SimpleOption.ofBoolean("ukuway.config.autoSell", config.isAutoSell(), config::setAutoSell),
                SimpleOption.ofBoolean("ukuway.config.ambientSounds", config.isAmbientSounds(), config::setAmbientSounds),
                SimpleOption.ofBoolean("ukuway.config.activitySongs", config.isActivitySongs(), config::setActivitySongs),
        };
    }
}
