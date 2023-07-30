package plus.hideaway.mod.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(at = @At("HEAD"), method = "onClosed")
    public void close(PlayerEntity player, CallbackInfo ci) {
        // whoops there's nothing here anymore
    }
}
