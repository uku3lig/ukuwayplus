package continued.hideaway.mod.mixin;

import continued.hideaway.mod.HideawayPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SoundEvent.class)
public class SoundEventMixin {
    // TODO: Implement cancelling of ambient sounds if the setting is enabled

    @Inject(at = @At("HEAD"), method = "create", cancellable = true)
    private static void newSoundEvent(ResourceLocation location, Optional<Float> range, CallbackInfoReturnable<SoundEvent> cir) {
        boolean isAmbient = location.getPath().split("\\.")[0].contains("ambient");
        boolean isActivity = location.getPath().split("\\.")[0].contains("activities");

        if (isAmbient && !HideawayPlus.config().noAmbientSounds()) {
            cir.setReturnValue(SoundEvent.createVariableRangeEvent(new ResourceLocation("")));
        }

        if (isActivity && !HideawayPlus.config().noActivitySongs()) {
            cir.setReturnValue(SoundEvent.createVariableRangeEvent(new ResourceLocation("")));
        }
    }
}
