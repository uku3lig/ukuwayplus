package net.uku3lig.ukuway.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.TextColor;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {
    @Inject(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/item/ItemStack;III)V"))
    public void drawSlotRarity(DrawContext context, Slot slot, CallbackInfo ci) {
        if (UkuwayPlus.isConnected() && UkuwayConfig.get().isInventoryRarities()) {
            TextColor itemColor = slot.getStack().getName().getStyle().getColor();
            if (itemColor != null) {
                int argb = itemColor.getRgb() | (150 << 24);

                context.fill(slot.x, slot.y + 2, slot.x + 1, slot.y + 14, argb);
                context.fill(slot.x + 1, slot.y + 1, slot.x + 2, slot.y + 15, argb);
                context.fill(slot.x + 2, slot.y, slot.x + 14, slot.y + 16, argb);
                context.fill(slot.x + 14, slot.y + 1, slot.x + 15, slot.y + 15, argb);
                context.fill(slot.x + 15, slot.y + 2, slot.x + 16, slot.y + 14, argb);
            }
        }
    }
}
