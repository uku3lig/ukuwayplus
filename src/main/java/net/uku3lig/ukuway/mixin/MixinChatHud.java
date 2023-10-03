package net.uku3lig.ukuway.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.util.Chars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public class MixinChatHud {
    @Unique
    private static final Pattern CHAT_PATTERN = Pattern.compile("^\\S+ (.+):.+");

    @Unique
    private static final Map<String, UUID> uuidCache = new HashMap<>();

    @ModifyArg(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V"), index = 0)
    private Text addMessageIcons(Text message) {
        Optional<UUID> uuid = findPlayer(message);

        if (uuid.isPresent() && FriendListManager.getFriends().contains(uuid.get())) {
            return Chars.FRIEND_BADGE.with(message);
        }

        return message;
    }

    @Unique
    private Optional<UUID> findPlayer(Text message) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return Optional.empty();

        Matcher matcher = CHAT_PATTERN.matcher(message.getString());
        if (matcher.find()) {
            String username = matcher.group(1);
            if (username != null) {
                username = username.toLowerCase(Locale.ROOT);
                UUID uuid = uuidCache.computeIfAbsent(username,
                        s -> world.getPlayers().stream()
                                .filter(p -> p.getEntityName().equalsIgnoreCase(s))
                                .findFirst()
                                .map(Entity::getUuid)
                                .orElse(null)
                );

                return Optional.ofNullable(uuid);
            }
        }

        return Optional.empty();
    }
}