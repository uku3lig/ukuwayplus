package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.util.Chars;
import net.uku3lig.ukuway.util.DisplayNameUtil;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatHud.class)
public class MixinChatHud {
    @ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", argsOnly = true)
    private Text addMessageIcons(Text message) {
        MutableText newMessage = Text.empty();
        String playerName = DisplayNameUtil.nameFromChatMessage(message.getString());

        if (FriendListManager.getFriends().contains(playerName)) // fixme
            Chars.addBadgeWithTooltip(newMessage, Chars.friendBadge(), "tooltip.hp.friend", Formatting.GOLD);

        newMessage.append(message);
        return newMessage;
    }
}