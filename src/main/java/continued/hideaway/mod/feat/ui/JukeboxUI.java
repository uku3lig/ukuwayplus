package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.jukebox.JukeboxTrack;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class JukeboxUI extends BaseOwoScreen<FlowLayout> {

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
            .surface(Surface.flat(0x77000000))
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout content = Containers.verticalFlow(Sizing.content(), Sizing.content())
                                .child(Components.label(Component.translatable("jukebox.title")).horizontalTextAlignment(HorizontalAlignment.CENTER));

        var stopBtn = (ButtonComponent) Components.button(Component.translatable("jukebox.stop_all_songs"), (btn) -> HideawayPlus.client().getSoundManager().stop())
                .margins(Insets.vertical(4));
        stopBtn.setWidth(160);

        content.child(stopBtn);

        for (JukeboxTrack track : JukeboxTrack.values())  {
            var component = (ButtonComponent) Components.button(Component.literal(track.name), (btn) -> HideawayPlus.jukebox().play(track))
                    .margins(Insets.vertical(2));
            component.setWidth(160);
//            content.child(component);
        }

        rootComponent.child(content);
    }

}
