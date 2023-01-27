package dev.abodactyl.cce.mixins;

import dev.abodactyl.cce.CCEClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class NametagMixin {
  
    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        Text prev = cir.getReturnValue();
        if (CCEClient.connected()) {
            // Ask WebSocket if player is using mod
            // If so, append rank icon to their username            
        }
    }
  
}
