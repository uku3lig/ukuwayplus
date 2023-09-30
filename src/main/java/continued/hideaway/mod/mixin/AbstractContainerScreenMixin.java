package continued.hideaway.mod.mixin;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.config.UkuwayConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Final @Shadow protected AbstractContainerMenu menu;
    @Shadow protected int leftPos;
    @Shadow protected int topPos;

    @Inject(method = "render", at = @At("TAIL"))
    public void renderSlotRarity(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (HideawayPlus.connected() && UkuwayConfig.get().isInventoryRarities()) {
            for (int k = 0; k < (this.menu).slots.size(); ++k) {
                Slot slot = (this.menu).slots.get(k);
                TextColor itemColor = slot.getItem().getHoverName().getStyle().getColor();
                if (itemColor != null) {
                    int color = itemColor.getValue();
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;


                    int itemColour = FastColor.ARGB32.color(150, r, g, b);
                    int leftX = leftPos + slot.x;
                    int leftY = topPos + slot.y;

                    guiGraphics.fill(leftX, leftY + 2, leftX + 1, leftY + 14, itemColour);
                    guiGraphics.fill(leftX + 1, leftY + 1, leftX + 2, leftY + 15, itemColour);
                    guiGraphics.fill(leftX + 2, leftY, leftX + 14, leftY + 16, itemColour);
                    guiGraphics.fill(leftX + 14, leftY + 1, leftX + 15, leftY + 15, itemColour);
                    guiGraphics.fill(leftX + 15, leftY + 2, leftX + 16, leftY + 14, itemColour);
                }
            }
        }
    }
}
