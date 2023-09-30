package continued.hideaway.mod.feat.config;

import continued.hideaway.mod.HideawayPlus;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class UkuwayConfigScreen extends AbstractConfigScreen<UkuwayConfig> {
    public UkuwayConfigScreen(Screen parent) {
        super(parent, Component.literal("Ukuway Config"), HideawayPlus.getManager());
    }

    @Override
    protected OptionInstance<?>[] getOptions(UkuwayConfig config) {
        return new OptionInstance[]{
                OptionInstance.createBoolean("ukuway.config.hideCosmetics", config.isHideCosmetics(), config::setHideCosmetics),
                OptionInstance.createBoolean("ukuway.config.discordRPC", config.isDiscordRPC(), config::setDiscordRPC),
                OptionInstance.createBoolean("ukuway.config.inventoryRarities", config.isInventoryRarities(), config::setInventoryRarities),
                OptionInstance.createBoolean("ukuway.config.autoSell", config.isAutoSell(), config::setAutoSell),
                OptionInstance.createBoolean("ukuway.config.noAmbientSounds", config.isNoAmbientSounds(), config::setNoAmbientSounds),
                OptionInstance.createBoolean("ukuway.config.noActivitySongs", config.isNoActivitySongs(), config::setNoActivitySongs),
        };
    }
}
