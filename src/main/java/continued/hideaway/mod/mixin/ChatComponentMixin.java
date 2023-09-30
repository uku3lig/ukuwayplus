package continued.hideaway.mod.mixin;

import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V", argsOnly = true)
    private Component addMessageIcons(Component message) {
        MutableComponent newMessage = MutableComponent.create(ComponentContents.EMPTY);
        String playerID = DisplayNameUtil.modPlayerID(DisplayNameUtil.nameFromChatMessage(message.getString()));
        String playerName = DisplayNameUtil.nameFromChatMessage(message.getString());

        if (StaticValues.friendsUsernames.contains(playerName))
            Chars.addBadgeWithTooltip(newMessage, Chars.friendBadge(), "tooltip.hp.friend", ChatFormatting.GOLD);

        if (StaticValues.devs.contains(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.devBadge(), "tooltip.hp.developer", ChatFormatting.YELLOW);
        else if (StaticValues.teamMembers.contains(playerID)) {
            System.out.println(playerID);
            Chars.addBadgeWithTooltip(newMessage, Chars.teamBadge(), "tooltip.hp.teamMember", ChatFormatting.LIGHT_PURPLE);
        }
        else if (StaticValues.translators.contains(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.translatorBadge(), "tooltip.hp.translator", ChatFormatting.GREEN);
        else if (StaticValues.users.containsKey(playerID))
            Chars.addBadgeWithTooltip(newMessage, Chars.userBadge(), "tooltip.hp.user", ChatFormatting.WHITE);

        newMessage.append(message);
        return newMessage;
    }
}