package continued.hideaway.mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class Chars {
    public static Component disc() {
        return Component.literal("\uE010").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }

    public static Component friendBadge() {
        return Component.literal("\uE002").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }
    public static Component userBadge() {
        return Component.literal("\uE001").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }
    public static Component devBadge() {
        return Component.literal("\uE003").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }
    public static Component translatorBadge() {
        return Component.literal("\uE004").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }
    public static Component teamBadge() {
        return Component.literal("\uE005").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }

    public static Component settingsIcon() {
        return Component.literal("\uEF01").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_plus:text")
        )).withStyle(ChatFormatting.WHITE);
    }

    public static void addBadgeWithTooltip(MutableComponent newMessage, Component badge, String key, ChatFormatting formatting) {
        Component component = ((MutableComponent) badge).withStyle(Style.EMPTY.withHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("").append(
                        Component.translatable(key).setStyle(Style.EMPTY.withColor(formatting))
                ))
        ));
        newMessage.append(component);
    }

    public static void addBadge(MutableComponent newComponent, Component badge) {
        newComponent.append(" ").append(badge);
    }
}
