package net.uku3lig.ukuway.util;


import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Chars {
    public static Text disc() {
        return Text.literal("\uE010").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text friendBadge() {
        return Text.literal("\uE002").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text userBadge() {
        return Text.literal("\uE001").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text devBadge() {
        return Text.literal("\uE003").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text translatorBadge() {
        return Text.literal("\uE004").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text teamBadge() {
        return Text.literal("\uE005").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static Text settingsIcon() {
        return Text.literal("\uEF01").setStyle(Style.EMPTY.withFont(
                new Identifier("hideaway_plus:text")
        )).formatted(Formatting.WHITE);
    }

    public static void addBadgeWithTooltip(MutableText newMessage, Text badge, String key, Formatting formatting) {
        Text text = badge.copy().setStyle(Style.EMPTY.withHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("").append(
                        Text.translatable(key).setStyle(Style.EMPTY.withColor(formatting))
                ))
        ));
        newMessage.append(text);
    }

    public static void addBadge(MutableText newText, Text badge) {
        newText.append(" ").append(badge);
    }
}
