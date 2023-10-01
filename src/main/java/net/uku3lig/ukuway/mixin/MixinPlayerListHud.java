package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.util.Chars;
import net.uku3lig.ukuway.util.DisplayNameUtil;
import net.uku3lig.ukuway.util.StaticValues;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {
    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        Text name = cir.getReturnValue();
        if (HideawayPlus.connected()){
            String username = DisplayNameUtil.ignFromDisplayName(name.getString());
            String playerID = DisplayNameUtil.modPlayerID(username);

            MutableText newName = name.copy();

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
