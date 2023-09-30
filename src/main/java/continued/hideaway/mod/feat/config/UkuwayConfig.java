package continued.hideaway.mod.feat.config;

import continued.hideaway.mod.HideawayPlus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.uku3lig.ukulib.config.IConfig;

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
        return HideawayPlus.getManager().getConfig();
    }
}