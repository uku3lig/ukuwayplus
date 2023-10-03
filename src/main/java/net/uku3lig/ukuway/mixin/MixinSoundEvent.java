package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.config.UkuwayConfig;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SoundEvent.class)
public class MixinSoundEvent {
    @Unique
    private static final SoundEvent EMPTY_SOUND = SoundEvent.of(new Identifier(""));

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject(at = @At("HEAD"), method = "of(Lnet/minecraft/util/Identifier;Ljava/util/Optional;)Lnet/minecraft/sound/SoundEvent;", cancellable = true)
    private static void newSoundEvent(Identifier location, Optional<Float> range, CallbackInfoReturnable<SoundEvent> cir) {
        boolean isAmbient = location.getPath().split("\\.")[0].contains("ambient");
        boolean isActivity = location.getPath().split("\\.")[0].contains("activities");

        if ((isAmbient && !UkuwayConfig.get().isAmbientSounds()) || (isActivity && !UkuwayConfig.get().isActivitySongs())) {
            cir.setReturnValue(EMPTY_SOUND);
        }
    }

    private MixinSoundEvent() {
    }
}
