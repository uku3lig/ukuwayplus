package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.TextColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {

    @Final @Shadow protected ScreenHandler handler;
    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "render", at = @At("TAIL"))
    public void renderSlotRarity(DrawContext drawContext, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (UkuwayPlus.connected() && UkuwayConfig.get().isInventoryRarities()) {
            for (int k = 0; k < (this.handler).slots.size(); ++k) {
                Slot slot = (this.handler).slots.get(k);
                TextColor itemColor = slot.getStack().getName().getStyle().getColor();
                if (itemColor != null) {
                    int colorAlpha = itemColor.getRgb() | (150 << 24);
                    int leftX = x + slot.x;
                    int leftY = y + slot.y;

                    drawContext.fill(leftX, leftY + 2, leftX + 1, leftY + 14, colorAlpha);
                    drawContext.fill(leftX + 1, leftY + 1, leftX + 2, leftY + 15, colorAlpha);
                    drawContext.fill(leftX + 2, leftY, leftX + 14, leftY + 16, colorAlpha);
                    drawContext.fill(leftX + 14, leftY + 1, leftX + 15, leftY + 15, colorAlpha);
                    drawContext.fill(leftX + 15, leftY + 2, leftX + 16, leftY + 14, colorAlpha);
                }
            }
        }
    }
}
