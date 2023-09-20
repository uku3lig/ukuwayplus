package plus.hideaway.mod.mixins;

import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoundEvent.class)
public class SoundEventMixin {
    // TODO: Implement cancelling of ambient sounds if the setting is enabled
}
