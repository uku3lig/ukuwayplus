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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final HashMap<Text, Integer> activeToasts = new HashMap<>();

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
//
//        DISABLED FOR NOW - PERFORMANCE ISSUES
//
//        for (Map.Entry<Text, Integer> entry : activeToasts.entrySet()) {
//            if (entry.getValue() >= 300) {
//                activeToasts.remove(entry.getKey());
//            } else activeToasts.replace(entry.getKey(), entry.getValue() + 1);
//        }
//
//        for (Text t : HideawayPlus.toastStack()) {
//            HideawayPlus.toastStack().remove(t);
//            activeToasts.put(t, 0);
//        }
//
//        int yCounter = 10;
//        for (Text activeToast : activeToasts.keySet()) {
//            DrawableHelper.drawTextWithShadow(
//                matrices,
//                MinecraftClient.getInstance().textRenderer,
//                activeToast,
//                10, yCounter, 0xff5555
//            );
//
//            yCounter += 13;
//        }
    }
}
