package plus.hideaway.mod.mixins;

import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.util.Chars;
import plus.hideaway.mod.util.DisplayNameUtil;

// TODO: This entire system lmao
@Mixin(PlayerTabOverlay.class)
public class DisplayNameMixin {
    @Inject(at = @At("RETURN"), method = "getNameForDisplay", cancellable = true)
    public void getDisplayName(PlayerInfo entry, CallbackInfoReturnable<Component> cir) {
        Component name = cir.getReturnValue();
        if (HideawayPlus.connected()){
            String result = DisplayNameUtil.ignFromDisplayName(name.getString());
            if (DisplayNameUtil.clientUsername().equals(result)) {
                MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
                newName.append(name).append(" ").append(Chars.badge());
                cir.setReturnValue(newName);
            }
        }
    }
}
