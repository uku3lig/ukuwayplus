package plus.hideaway.mod.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.hideaway.mod.HideawayPlus;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        // Connection Indicator
        if (HideawayPlus.ws().connected()) {
            DrawableHelper.drawTextWithShadow(
                    matrices,
                    MinecraftClient.getInstance().textRenderer,
                    Text.literal("CONNECTED").setStyle(Style.EMPTY.withFont(new Identifier("hideawayplus:text"))),
                    10, 10, 0x55ff55
            );
        } else if (HideawayPlus.ws().willReconnect()) {
            DrawableHelper.drawTextWithShadow(
                    matrices,
                    MinecraftClient.getInstance().textRenderer,
                    Text.literal("RECONNECTING...").setStyle(Style.EMPTY.withFont(new Identifier("hideawayplus:text"))),
                    10, 10, 0xFFFF55
            );
        } else DrawableHelper.drawTextWithShadow(
                matrices,
                MinecraftClient.getInstance().textRenderer,
                Text.literal("DISCONNECTED").setStyle(Style.EMPTY.withFont(new Identifier("hideawayplus:text"))),
                10, 10, 0xff5555
        );
    }
}
