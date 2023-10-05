package net.uku3lig.ukuway.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.util.Chars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {
    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        if (UkuwayPlus.isConnected()) {
            MutableText newName = cir.getReturnValue().copy();

            if (FriendListManager.getFriends().contains(entry.getProfile().getName().toLowerCase(Locale.ROOT))) {
                newName = Chars.FRIEND_BADGE.with(newName);
            }

            cir.setReturnValue(newName);
        }
    }
}
