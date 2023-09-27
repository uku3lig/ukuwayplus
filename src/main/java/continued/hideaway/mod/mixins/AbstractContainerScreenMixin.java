package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ext.AbstractContainerScreenAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements AbstractContainerScreenAccessor {

    @Shadow protected abstract void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type);
    @Final
    @Shadow protected AbstractContainerMenu menu;
    @Shadow protected int leftPos;
    @Shadow protected int topPos;

    @Override
    public void hp$slotChange(Slot slot, int slotId, int mouseButton, ClickType type) {
        slotClicked(slot, slotId, mouseButton, type);
    }

    @Unique
    private void customFill(GuiGraphics gui, int colour, int lowestX, int lowestY,
                             int xPointA, int xPointB, int yPointA, int yPointB) {
        gui.fill(lowestX + xPointA, lowestY + yPointA, lowestX + xPointB, lowestY + yPointB, colour);
    }

    @Unique
    private void writeRightToLeft(GuiGraphics gui, int colour, int lowestX, int lowestY,
                            int right, int left, int y) {
        gui.fill(lowestX + right, lowestY + y, lowestX + left, lowestY + y + 1, colour);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void renderSlotRarity(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (HideawayPlus.connected() && HideawayPlus.config().inventoryRarities()) {
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

                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            6, 10, 0);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            4, 12, 1);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            2, 14, 2);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            2, 14, 3);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            1, 15, 4);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            1, 15, 5);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            0, 16, 6);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            0, 16, 7);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            0, 16, 8);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            0, 16, 9);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            1, 15, 10);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            1, 15, 11);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            2, 14, 12);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            2, 14, 13);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            4, 12, 14);
                    writeRightToLeft(guiGraphics, itemColour, leftX, leftY,
                            6, 10, 15);

                    //guiGraphics.fill(leftPos + slot.x, topPos + slot.y, leftPos + slot.x + 16, topPos + slot.y + 16, FastColor.ARGB32.color(150, r, g, b));
                }
            }
        }
    }
}
