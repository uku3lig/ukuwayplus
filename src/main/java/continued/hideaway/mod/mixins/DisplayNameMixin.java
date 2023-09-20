package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: This entire system lmao
@Mixin(PlayerTabOverlay.class)
public class DisplayNameMixin {
    @Inject(at = @At("RETURN"), method = "getNameForDisplay", cancellable = true)
    public void getDisplayName(PlayerInfo entry, CallbackInfoReturnable<Component> cir) {
        Component name = cir.getReturnValue();
        if (HideawayContinued.connected()){
            String result = DisplayNameUtil.ignFromDisplayName(name.getString());
            if (DisplayNameUtil.clientUsername().equals(result)) {
                MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
                newName.append(name).append(" ").append(Chars.badge());
                cir.setReturnValue(newName);
            }
        }
    }
}
