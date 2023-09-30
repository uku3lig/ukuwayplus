package continued.hideaway.mod.mixin;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
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
            String username = DisplayNameUtil.ignFromDisplayName(name.getString());
            String playerID = DisplayNameUtil.modPlayerID(username);

            MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
            newName.append(name);

            if (StaticValues.friendsUsernames.contains(username))
                Chars.addBadge(newName, Chars.friendBadge());

            if (StaticValues.devs.contains(playerID))
                Chars.addBadge(newName, Chars.devBadge());
            else if (StaticValues.teamMembers.contains(playerID))
                Chars.addBadge(newName, Chars.teamBadge());
            else if (StaticValues.translators.contains(playerID))
                Chars.addBadge(newName, Chars.translatorBadge());
            else if (StaticValues.users.containsKey(playerID))
                Chars.addBadge(newName, Chars.userBadge());

            if (!newName.toString().equals(username)) cir.setReturnValue(newName);
        }
    }
}
