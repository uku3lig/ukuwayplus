package net.uku3lig.ukuway.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.util.HideawayItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TooltipBackgroundRenderer.class)
public abstract class MixinTooltipBackgroundRenderer {
    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/tooltip/TooltipBackgroundRenderer;renderBorder(Lnet/minecraft/client/gui/DrawContext;IIIIIII)V"))
    private static void modifyBorderColor(Args args) {
        if (MinecraftClient.getInstance().currentScreen instanceof HandledScreen<?> screen && screen.focusedSlot != null
                && UkuwayConfig.get().isInventoryRarities()) {
            HideawayItem item = HideawayItem.fromStack(screen.focusedSlot.getStack());

            if (item != null && item.rarity() != null) {
                int argb = item.rarity().withAlpha();

                args.set(6, argb);
                args.set(7, argb);
            }
        }
    }

    private MixinTooltipBackgroundRenderer() {
    }
}
