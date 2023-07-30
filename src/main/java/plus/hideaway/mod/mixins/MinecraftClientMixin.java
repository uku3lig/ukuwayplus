package plus.hideaway.mod.mixins;

import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.settings.Settings;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "close")
    public void close(CallbackInfo ci) {
        // HideawayPlus.ws().end();
        Settings.write();
    }
}
