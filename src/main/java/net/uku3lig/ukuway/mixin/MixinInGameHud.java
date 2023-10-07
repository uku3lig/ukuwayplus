package net.uku3lig.ukuway.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.jukebox.JukeboxTrack;
import net.uku3lig.ukuway.util.Chars;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Shadow
    @Nullable
    private Text overlayMessage;

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(DrawContext drawContext, float tickDelta, CallbackInfo ci) {
        JukeboxTrack track = UkuwayPlus.getJukebox().getCurrentTrack();
        if (track != null) {
            drawContext.drawTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    Chars.DISC.with(Text.of(" Now playing: ")).append(Text.literal(track.getTrackName()).styled(s -> s.withItalic(true))),
                    10, 10, 0xFFFFFF
            );
        }
    }

    @Inject(method = "renderExperienceBar",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I", ordinal = 0)
    )
    public void experienceBarPercent(DrawContext context, int x, CallbackInfo ci) {
        if (UkuwayPlus.isConnected() && overlayMessage != null && overlayMessage.getString().contains("\uE2C3") && MinecraftClient.getInstance().player != null) {
            float progress = MinecraftClient.getInstance().player.experienceProgress * 100;
            String percentage = String.format("%.2f%%", progress);

            context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, percentage, this.scaledWidth / 2, this.scaledHeight - 31 - 4 - 14, 0x80FF20);
        }
    }
}
