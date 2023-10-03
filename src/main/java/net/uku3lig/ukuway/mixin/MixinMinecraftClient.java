package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.UkuwayPlus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void hideAdvancements(Screen screen, CallbackInfo ci) {
        if (UkuwayPlus.isConnected() && screen instanceof AdvancementsScreen) {
            ci.cancel();
        }
    }
}
