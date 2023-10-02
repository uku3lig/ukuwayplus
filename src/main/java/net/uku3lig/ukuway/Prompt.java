package net.uku3lig.ukuway;

import net.uku3lig.ukuway.util.Chars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@SuppressWarnings("unused") // todo
public class Prompt {

    public static void info(String message) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendMessage(
                Text.empty()
                        .append(Text.literal("[").formatted(Formatting.GRAY))
                        .append(Text.literal("Hideaway: Continued").formatted(Formatting.GOLD))
                        .append(Text.literal("] ").formatted(Formatting.GRAY))
                        .append(Text.literal(message).formatted(Formatting.AQUA))
        );
    }

    public static void error(String message) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendMessage(
                Text.empty()
                        .append(Text.literal("[").formatted(Formatting.GRAY))
                        .append(Chars.userBadge())
                        .append(Text.literal("] ").formatted(Formatting.GRAY))
                        .append(Text.literal(message).formatted(Formatting.RED))
        );
    }

    public static void trace(String message) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendMessage(
                Text.empty()
                        .append(Text.literal("[").formatted(Formatting.GRAY))
                        .append(Chars.userBadge())
                        .append(Text.literal(" Debug").formatted(Formatting.DARK_PURPLE))
                        .append(Text.literal("] ").formatted(Formatting.GRAY))
                        .append(Text.literal(message).formatted(Formatting.LIGHT_PURPLE))
        );
    }

    public static void trace(Text message) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendMessage(
                Text.empty()
                        .append(Text.literal("[").formatted(Formatting.GRAY))
                        .append(Chars.userBadge())
                        .append(Text.literal(" Debug").formatted(Formatting.DARK_PURPLE))
                        .append(Text.literal("] ").formatted(Formatting.GRAY))
                        .append(message)
        );
    }

    public static void traceWithClick(Text message, String hover) {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.sendMessage(
                Text.empty()
                        .append(Text.literal("[").formatted(Formatting.GRAY))
                        .append(Chars.userBadge())
                        .append(Text.literal("Debug").formatted(Formatting.DARK_PURPLE))
                        .append(Text.literal("] ").formatted(Formatting.GRAY))
                        .append(message)
                        .styled(s -> s.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of(hover)))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, message.getString()))
                        )

        );
    }

    private Prompt() {
    }
}
