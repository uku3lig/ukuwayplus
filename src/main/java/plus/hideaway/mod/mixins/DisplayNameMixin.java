package plus.hideaway.mod.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.Prompt;
import plus.hideaway.mod.feat.ws.ModUserCache;
import plus.hideaway.mod.util.Chars;
import plus.hideaway.mod.util.DisplayNameUtil;

@Mixin(PlayerListHud.class)
public class DisplayNameMixin {
    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        Text name = cir.getReturnValue();
        if (HideawayPlus.connected()){
            String result = DisplayNameUtil.ignFromDisplayName(name.getString());
            if (ModUserCache.containsUser(entry.getProfile().getId().toString()) || DisplayNameUtil.clientUsername().equals(result)) {
                MutableText newName = MutableText.of(TextContent.EMPTY);
                newName.append(name).append(" ").append(Chars.badge());
                cir.setReturnValue(newName);
            }
        }
    }
}
