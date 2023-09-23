package continued.hideaway.mod.mixins;

import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V", argsOnly = true)
    private Component addMessageIcons(Component message) {
        MutableComponent newMessage = MutableComponent.create(ComponentContents.EMPTY);
        String username = message.getString().substring(2, message.getString().indexOf(":"));
        String playerID = DisplayNameUtil.modPlayerID(username);

        if (StaticValues.modUsers.containsValue(username) && !StaticValues.modDevelopers.contains(playerID)) newMessage.append(Chars.badge());
        if (StaticValues.modDevelopers.contains(playerID)) newMessage.append(Chars.devBadge());
        if (StaticValues.friendsList.contains(username))
            newMessage.append(Chars.friendBadge())
                            .withStyle(Style.EMPTY.withHoverEvent(
                                    new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("")
                                            .append(Component.translatable("hideaway.tooltip.friend")
                                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)))
                                    )
                            ));
        newMessage.append(message);
        return newMessage;
    }
}