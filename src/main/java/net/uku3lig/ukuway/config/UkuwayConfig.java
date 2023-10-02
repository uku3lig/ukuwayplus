package net.uku3lig.ukuway.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.uku3lig.ukulib.config.IConfig;
import net.uku3lig.ukuway.UkuwayPlus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UkuwayConfig implements IConfig<UkuwayConfig> {
    private boolean hideCosmetics = false;
    private boolean discordRPC = true;
    private boolean inventoryRarities = true;
    private boolean autoSell = false;
    private boolean noAmbientSounds = false;
    private boolean noActivitySongs = false;

    @Override
    public UkuwayConfig defaultConfig() {
        return new UkuwayConfig();
    }

    public static UkuwayConfig get() {
        return UkuwayPlus.getManager().getConfig();
    }
}