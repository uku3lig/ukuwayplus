package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.feat.ext.BossHealthOverlayAccessor;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.ParseItemName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Shadow private int screenWidth;

    @Shadow public abstract Font getFont();

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private BossHealthOverlay bossOverlay;

    @Shadow @Nullable private Component overlayMessageString;

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        if (HideawayContinued.config().jukebox()) {
            if (HideawayContinued.jukebox().currentTrack != null) {
                guiGraphics.drawString(
                        Minecraft.getInstance().font,
                        Component.empty()
                            .append(Chars.disc())
                            .append(Component.literal("Now playing: " + HideawayContinued.jukebox().currentTrack.name)),
                        10, 10, 0xffffff, true
                );
            }
        }
    }

    @Inject(method = "renderExperienceBar",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I", ordinal = 1, shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            slice = @Slice(
                    from = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I", ordinal = 1))
    )
    public void expereicneBarPercent(GuiGraphics guiGraphics, int x, CallbackInfo ci, int i, String string, int textSize, int textPos) {
        if (HideawayContinued.connected()) {
            string = "(" + (Math.round(this.minecraft.player.experienceProgress * 10000) / 100.0) + "%)";
            textSize = (this.screenWidth - this.getFont().width(string)) / 2;

            textPos = textPos - 14;

            guiGraphics.drawString(this.getFont(), string, (int)(textSize + 1), (int)textPos, 0, true);
            guiGraphics.drawString(this.getFont(), string, (int)(textSize + 1), (int)textPos, 0, true);
            guiGraphics.drawString(this.getFont(), string, (int)(textSize + 1), (int)textPos + 1, 0, true);
            guiGraphics.drawString(this.getFont(), string, (int)(textSize + 1), (int)textPos, 0, true);
            guiGraphics.drawString(this.getFont(), string, (int)(textSize + 1), (int)textPos, 8453920, true);
        }
    }
}
