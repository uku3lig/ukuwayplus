package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.config.UkuwayConfigScreen;
import net.uku3lig.ukuway.util.Chars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {

    protected MixinGameMenuScreen(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void addUkuwayButton(CallbackInfo ci, GridWidget gridWidget) {
        AtomicReference<Widget> first = new AtomicReference<>();
        gridWidget.forEachElement(widget -> {
            if (first.get() == null) first.set(widget);
        });

        Widget w = first.get();

        this.addDrawableChild(ButtonWidget.builder(Chars.settingsIcon(), button -> MinecraftClient.getInstance().setScreen(new UkuwayConfigScreen(this)))
                .dimensions(w.getX() + w.getWidth() + 4, w.getY(), 20, 20)
                .build());
    }
}
