package net.uku3lig.ukuway.mixin;

import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LogoDrawer.class)
public class MixinLogoDrawer {
    @ModifyConstant(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", constant = @Constant(intValue = 44))
    private int logoBlitHeight(int h) {
        return 64;
    }

    @ModifyVariable(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private int logoPositionHeight(int h) {
        return 33;
    }
}
