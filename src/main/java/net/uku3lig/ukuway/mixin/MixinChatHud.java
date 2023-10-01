package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.util.Chars;
import net.uku3lig.ukuway.util.DisplayNameUtil;
import net.uku3lig.ukuway.util.StaticValues;
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
        String playerID = DisplayNameUtil.modPlayerID(DisplayNameUtil.nameFromChatMessage(message.getString()));
        String playerName = DisplayNameUtil.nameFromChatMessage(message.getString());

        if (StaticValues.friendsUsernames.contains(playerName))
            Chars.addBadgeWithTooltip(newMessage, Chars.friendBadge(), "tooltip.hp.friend", Formatting.GOLD);

        if (StaticValues.devs.contains(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.devBadge(), "tooltip.hp.developer", Formatting.YELLOW);
        else if (StaticValues.teamMembers.contains(playerID)) {
            System.out.println(playerID);
            Chars.addBadgeWithTooltip(newMessage, Chars.teamBadge(), "tooltip.hp.teamMember", Formatting.LIGHT_PURPLE);
        }
        else if (StaticValues.translators.contains(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.translatorBadge(), "tooltip.hp.translator", Formatting.GREEN);
        else if (StaticValues.users.containsKey(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.userBadge(), "tooltip.hp.user", Formatting.WHITE);

        newMessage.append(message);
        return newMessage;
    }
}