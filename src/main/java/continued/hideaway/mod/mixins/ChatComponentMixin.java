package continued.hideaway.mod.mixins;

import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.StaticValues;
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
        String username = message.getString().substring(2, message.getString().indexOf(":"));

        if (StaticValues.friendsList.contains(username)) {
            newMessage.append(Chars.friendBadge());
        }
        newMessage.append(message);
        return newMessage;
    }
}