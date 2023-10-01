package net.uku3lig.ukuway.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;
import net.uku3lig.ukuway.HideawayPlus;

public class UkuwayConfigScreen extends AbstractConfigScreen<UkuwayConfig> {
    public UkuwayConfigScreen(Screen parent) {
        super(parent, Text.of("Ukuway Config"), HideawayPlus.getManager());
    }

    @Override
    protected SimpleOption<?>[] getOptions(UkuwayConfig config) {
        return new SimpleOption[]{
                SimpleOption.ofBoolean("ukuway.config.hideCosmetics", config.isHideCosmetics(), config::setHideCosmetics),
                SimpleOption.ofBoolean("ukuway.config.discordRPC", config.isDiscordRPC(), config::setDiscordRPC),
                SimpleOption.ofBoolean("ukuway.config.inventoryRarities", config.isInventoryRarities(), config::setInventoryRarities),
                SimpleOption.ofBoolean("ukuway.config.autoSell", config.isAutoSell(), config::setAutoSell),
                SimpleOption.ofBoolean("ukuway.config.noAmbientSounds", config.isNoAmbientSounds(), config::setNoAmbientSounds),
                SimpleOption.ofBoolean("ukuway.config.noActivitySongs", config.isNoActivitySongs(), config::setNoActivitySongs),
        };
    }
}
