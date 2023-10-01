package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.util.Chars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {

    @Shadow
    private int scaledWidth;

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Shadow
    @Nullable
    private Text overlayMessage;

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(DrawContext drawContext, float tickDelta, CallbackInfo ci) {
        if (HideawayPlus.jukebox() != null && HideawayPlus.jukebox().currentTrack != null) {
            drawContext.drawTextWithShadow(
                    this.getTextRenderer(),
                    Chars.disc().copy().append(Text.of("Now playing: " + HideawayPlus.jukebox().currentTrack.name)),
                    10, 10, 0xffffff
            );
        }
    }

    @Inject(method = "renderExperienceBar",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void experienceBarPercent(DrawContext drawContext, int x, CallbackInfo ci, int i, String string, int textSize, int textPos) {
        if (HideawayPlus.connected()) {
            if (overlayMessage == null || !overlayMessage.getString().contains("\uE2C3") || MinecraftClient.getInstance().player == null)
                return;

            String percentage = (Math.round(MinecraftClient.getInstance().player.experienceProgress * 10000) / 100.0) + "%";
            drawContext.drawCenteredTextWithShadow(this.getTextRenderer(), percentage, this.scaledWidth / 2, textPos - 14, 8453920);
        }
    }
}
