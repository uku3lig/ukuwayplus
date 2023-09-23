package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.api.API;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerTabOverlay.class)
public class DisplayNameMixin {
    @Inject(at = @At("RETURN"), method = "getNameForDisplay", cancellable = true)
    public void getDisplayName(PlayerInfo entry, CallbackInfoReturnable<Component> cir) {
        Component name = cir.getReturnValue();
        if (HideawayPlus.connected()){
            String result = DisplayNameUtil.ignFromDisplayName(name.getString());
            String playerID = "";
            if (StaticValues.modUsers.containsValue(result)) {
                for (String key : StaticValues.modUsers.keySet()) {
                    if (StaticValues.modUsers.get(key).equals(result)) {
                        playerID = key;
                        System.out.println("found user " + result);
                        break;
                    }
                }
            }

            MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
            newName.append(name);

            if (StaticValues.modUsers.containsValue(result) && !StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.badge());
            if (StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.devBadge());
            if (StaticValues.friendsList.contains(result)) newName.append(" ").append(Chars.friendBadge());
            if (!newName.toString().equals(result)) cir.setReturnValue(newName);
        }
    }
}
