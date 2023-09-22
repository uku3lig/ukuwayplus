package continued.hideaway.mod.mixins.ext;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SoundEvent.class)
public interface SoundEventAccessor {
    @Invoker("<init>")
    static SoundEvent createSoundEvent(ResourceLocation location, float range, boolean newSystem) {
        throw new UnsupportedOperationException();
    }
}
