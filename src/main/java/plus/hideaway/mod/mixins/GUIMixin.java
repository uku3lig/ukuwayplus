package plus.hideaway.mod.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;

@Mixin(HandledScreens.class)
public class GUIMixin {
    @Inject(at = @At("HEAD"), method = "open")
    private static <T extends ScreenHandler> void open(ScreenHandlerType<T> type, MinecraftClient client, int id, Text title, CallbackInfo ci) {
        HideawayPlus.setGUI(title);
    }
}
