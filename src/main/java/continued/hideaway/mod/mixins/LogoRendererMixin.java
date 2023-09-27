package continued.hideaway.mod.mixins;

import net.minecraft.client.gui.components.LogoRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {

    @ModifyConstant(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", constant = @Constant(intValue = 44))
    private int logoBlitHeight(int h) {
        return 64;
    }

    @ModifyVariable(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private int logoPositionHeight(int h) {
        return 33;
    }
}
