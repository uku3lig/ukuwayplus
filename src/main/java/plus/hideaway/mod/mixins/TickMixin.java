package plus.hideaway.mod.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;

@Mixin(MinecraftClient.class)
public class TickMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci){
        HideawayPlus.lifecycle().tick();
    }
}
