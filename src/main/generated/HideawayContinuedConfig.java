package continued.hideaway.mod.feat.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HideawayContinuedConfig extends ConfigWrapper<continued.hideaway.mod.feat.config.HideawayContinuedConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> pipNav = this.optionForKey(this.keys.pipNav);
    private final Option<java.lang.Boolean> hideCosmetics = this.optionForKey(this.keys.hideCosmetics);
    private final Option<java.lang.Boolean> discordRPC = this.optionForKey(this.keys.discordRPC);
    private final Option<java.lang.Boolean> autoEnableEditor = this.optionForKey(this.keys.autoEnableEditor);
    private final Option<java.lang.Boolean> jukebox = this.optionForKey(this.keys.jukebox);
    private final Option<java.lang.Boolean> noAmbientSounds = this.optionForKey(this.keys.noAmbientSounds);

    private HideawayContinuedConfig() {
        super(continued.hideaway.mod.feat.config.HideawayContinuedConfigModel.class);
    }

    private HideawayContinuedConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(continued.hideaway.mod.feat.config.HideawayContinuedConfigModel.class, janksonBuilder);
    }

    public static HideawayContinuedConfig createAndLoad() {
        var wrapper = new HideawayContinuedConfig();
        wrapper.load();
        return wrapper;
    }

    public static HideawayContinuedConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new HideawayContinuedConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public boolean pipNav() {
        return pipNav.value();
    }

    public void pipNav(boolean value) {
        pipNav.set(value);
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

    public boolean autoEnableEditor() {
        return autoEnableEditor.value();
    }

    public void autoEnableEditor(boolean value) {
        autoEnableEditor.set(value);
    }

    public boolean jukebox() {
        return jukebox.value();
    }

    public void jukebox(boolean value) {
        jukebox.set(value);
    }

    public boolean noAmbientSounds() {
        return noAmbientSounds.value();
    }

    public void noAmbientSounds(boolean value) {
        noAmbientSounds.set(value);
    }


    public static class Keys {
        public final Option.Key pipNav = new Option.Key("pipNav");
        public final Option.Key hideCosmetics = new Option.Key("hideCosmetics");
        public final Option.Key discordRPC = new Option.Key("discordRPC");
        public final Option.Key autoEnableEditor = new Option.Key("autoEnableEditor");
        public final Option.Key jukebox = new Option.Key("jukebox");
        public final Option.Key noAmbientSounds = new Option.Key("noAmbientSounds");
    }
}

