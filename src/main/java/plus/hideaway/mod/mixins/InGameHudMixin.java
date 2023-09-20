package plus.hideaway.mod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.util.Chars;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        if (HideawayPlus.config().jukebox()) {
            if (HideawayPlus.jukebox().currentTrack != null) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font,
                        Component.empty()
                            .append(Chars.disc())
                            .append(Component.literal("Now playing: " + HideawayPlus.jukebox().currentTrack.name)),
                        10, 10, 0xffffff, true
                );
            }
        }
    }
}
