package continued.hideaway.mod.feat.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HideawayPlusConfig extends ConfigWrapper<continued.hideaway.mod.feat.config.HideawayPlusConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> hideCosmetics = this.optionForKey(this.keys.hideCosmetics);
    private final Option<java.lang.Boolean> discordRPC = this.optionForKey(this.keys.discordRPC);
    private final Option<java.lang.Boolean> inventoryRarities = this.optionForKey(this.keys.inventoryRarities);
    private final Option<java.lang.Boolean> autoSell = this.optionForKey(this.keys.autoSell);
    private final Option<java.lang.Boolean> noAmbientSounds = this.optionForKey(this.keys.noAmbientSounds);
    private final Option<java.lang.Boolean> noActivitySongs = this.optionForKey(this.keys.noActivitySongs);

    private HideawayPlusConfig() {
        super(continued.hideaway.mod.feat.config.HideawayPlusConfigModel.class);
    }

    private HideawayPlusConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(continued.hideaway.mod.feat.config.HideawayPlusConfigModel.class, janksonBuilder);
    }

    public static HideawayPlusConfig createAndLoad() {
        var wrapper = new HideawayPlusConfig();
        wrapper.load();
        return wrapper;
    }

    public static HideawayPlusConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new HideawayPlusConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public boolean hideCosmetics() {
        return hideCosmetics.value();
    }

    public void hideCosmetics(boolean value) {
        hideCosmetics.set(value);
    }

    public boolean discordRPC() {
        return discordRPC.value();
    }

    public void discordRPC(boolean value) {
        discordRPC.set(value);
    }

    public boolean inventoryRarities() {
        return inventoryRarities.value();
    }

    public void inventoryRarities(boolean value) {
        inventoryRarities.set(value);
    }

    public boolean autoSell() {
        return autoSell.value();
    }

    public void autoSell(boolean value) {
        autoSell.set(value);
    }

    public boolean noAmbientSounds() {
        return noAmbientSounds.value();
    }

    public void noAmbientSounds(boolean value) {
        noAmbientSounds.set(value);
    }

    public boolean noActivitySongs() {
        return noActivitySongs.value();
    }

    public void noActivitySongs(boolean value) {
        noActivitySongs.set(value);
    }


    public static class Keys {
        public final Option.Key hideCosmetics = new Option.Key("hideCosmetics");
        public final Option.Key discordRPC = new Option.Key("discordRPC");
        public final Option.Key inventoryRarities = new Option.Key("inventoryRarities");
        public final Option.Key autoSell = new Option.Key("autoSell");
        public final Option.Key noAmbientSounds = new Option.Key("noAmbientSounds");
        public final Option.Key noActivitySongs = new Option.Key("noActivitySongs");
    }
}

