package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
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
        if (HideawayPlus.connected()){
            String result = DisplayNameUtil.ignFromDisplayName(name.getString());
            String playerID = "";
            if (HideawayPlus.client().level.players().stream().anyMatch(player -> DisplayNameUtil.ignFromDisplayName(player.getDisplayName().getString()).equals(result))) {
                for (AbstractClientPlayer player : HideawayPlus.client().level.players()) {
                    if (DisplayNameUtil.ignFromDisplayName(player.getDisplayName().getString()).equals(result)){
                        playerID = String.valueOf(player.getUUID());
                    }
                }
            }

            MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
            newName.append(name);

            if (StaticValues.modUsers.contains(playerID) && !StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.badge());
            if (StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.devBadge());
            if (StaticValues.friendsList.contains(result)) newName.append(" ").append(Chars.friendBadge());
            if (!newName.toString().equals(result)) cir.setReturnValue(newName);
        }
    }
}
