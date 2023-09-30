package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.jukebox.JukeboxTrack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class JukeboxScreen extends Screen {
    private final Screen parent;

    public JukeboxScreen(Screen parent) {
        super(Component.literal("Jukebox"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        GridLayout grid = new GridLayout();
        GridLayout.RowHelper rowHelper = grid.spacing(4).createRowHelper(2);

        for (JukeboxTrack track : JukeboxTrack.values()) {
            rowHelper.addChild(Button.builder(Component.literal(track.name), b -> HideawayPlus.jukebox().play(track)).width(160).build());
        }

        grid.arrangeElements();
        FrameLayout.alignInRectangle(grid, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        grid.visitWidgets(this::addRenderableWidget);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> Minecraft.getInstance().setScreen(parent))
                .bounds(this.width / 2 - 162, height - 27, 160, 20)
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("Stop music"), button -> HideawayPlus.jukebox().stop())
                .bounds(this.width / 2 + 2, height - 27, 160, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderDirtBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(parent);
    }
}
