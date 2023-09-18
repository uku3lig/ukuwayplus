package plus.hideaway.mod.feat.ui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.jukebox.JukeboxTrack;

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
                                .child(Components.label(Text.literal("Play a song")).horizontalTextAlignment(HorizontalAlignment.CENTER));

        var stopBtn = (ButtonComponent) Components.button(Text.literal("Stop all music"), (btn) -> HideawayPlus.client().getSoundManager().stopAll())
                .margins(Insets.vertical(4));
        stopBtn.setWidth(160);
        content.child(stopBtn);

        content.child(stopBtn);

        for (JukeboxTrack track : JukeboxTrack.values())  {
            var component = (ButtonComponent) Components.button(Text.literal(track.name), (btn) -> HideawayPlus.jukebox().play(track))
                    .margins(Insets.vertical(2));
            component.setWidth(160);
            content.child(component);
        }

        rootComponent.child(content.padding(Insets.of(10)));
    }

}
