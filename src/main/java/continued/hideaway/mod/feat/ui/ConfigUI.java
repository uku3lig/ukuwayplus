package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayContinued;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConfigUI extends BaseOwoScreen<FlowLayout> {

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.surface(Surface.flat(0x77000000))
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.CENTER);

        FlowLayout content = Containers.verticalFlow(Sizing.content(), Sizing.content())
                .child(Components.label(Component.literal("Play a song")));

        var options = new ArrayList<ConfigOption<?>>();

        // General
        options.add(new ConfigOption<>("PipNav", HideawayContinued.config().optionForKey(new Option.Key("pipNav")).key(), HideawayContinued.config().optionForKey(new Option.Key("pipNav")).value()));
        options.add(new ConfigOption<>("Hide Cosmetics", HideawayContinued.config().optionForKey(new Option.Key("hideCosmetics")).key(), HideawayContinued.config().optionForKey(new Option.Key("hideCosmetics")).value()));
        options.add(new ConfigOption<>("Discord RPC", HideawayContinued.config().optionForKey(new Option.Key("discordRPC")).key(), HideawayContinued.config().optionForKey(new Option.Key("discordRPC")).value()));

        // Rooms
        options.add(new ConfigOption<>("Auto-Enable Editor", HideawayContinued.config().optionForKey(new Option.Key("autoEnableEditor")).key(), HideawayContinued.config().optionForKey(new Option.Key("autoEnableEditor")).value()));

        // Sound
        options.add(new ConfigOption<>("Jukebox", HideawayContinued.config().optionForKey(new Option.Key("jukebox")).key(), HideawayContinued.config().optionForKey(new Option.Key("jukebox")).value()));
        options.add(new ConfigOption<>("Disable Ambient Sounds", HideawayContinued.config().optionForKey(new Option.Key("noAmbientSounds")).key(), HideawayContinued.config().optionForKey(new Option.Key("noAmbientSounds")).value()));

        final var optionsScrollContainer = Containers.verticalScroll(
                Sizing.fill(90),
                Sizing.fill(85),
                Components.list(
                        options,
                        flowLayout -> {},
                        this::createOptionComponent,
                        true
                )
        );

        content.child(
            optionsScrollContainer
                .scrollbarThiccness(4)
                .padding(Insets.of(1))
        );
        rootComponent.child(content);
    }

    private <T> FlowLayout createOptionComponent(ConfigOption<T> option) {
        var container = Containers.verticalFlow(Sizing.fill(100), Sizing.fixed(32));
        container.padding(Insets.of(5));

        var valueLayout = Containers.verticalFlow(Sizing.content(), Sizing.content());
        valueLayout.verticalAlignment(VerticalAlignment.CENTER);
        container.child(valueLayout);

        if (option.value instanceof Boolean) {
            final var valueBox = Components.checkbox(Component.nullToEmpty(option.name));
            valueBox.onChanged(now -> HideawayContinued.config().optionForKey(option.key));
            valueLayout.child(valueBox.margins(Insets.horizontal(5)));
        } // all options are boolean for now

        return container;
    }

    private record ConfigOption<T>(String name, Option.Key key, T value) {}
}
