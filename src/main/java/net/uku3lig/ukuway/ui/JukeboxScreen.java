package net.uku3lig.ukuway.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.jukebox.JukeboxTrack;

public class JukeboxScreen extends Screen {
    private final Screen parent;

    public JukeboxScreen(Screen parent) {
        super(Text.translatable("jukebox.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        GridWidget grid = new GridWidget();
        GridWidget.Adder rowHelper = grid.setSpacing(4).createAdder(2);

        for (JukeboxTrack track : JukeboxTrack.values()) {
            rowHelper.add(ButtonWidget.builder(Text.of(track.getTrackName()), b -> UkuwayPlus.getJukebox().play(track)).width(160).build());
        }

        grid.refreshPositions();
        SimplePositioningWidget.setPos(grid, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        grid.forEachChild(this::addDrawableChild);

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> MinecraftClient.getInstance().setScreen(parent))
                .dimensions(this.width / 2 - 162, height - 27, 160, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("jukebox.stop_all_songs"), button -> UkuwayPlus.getJukebox().stop())
                .dimensions(this.width / 2 + 2, height - 27, 160, 20)
                .build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTick) {
        this.renderBackgroundTexture(drawContext);
        drawContext.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(drawContext, mouseX, mouseY, partialTick);
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }
}
