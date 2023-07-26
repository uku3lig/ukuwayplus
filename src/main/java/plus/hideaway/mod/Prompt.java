package plus.hideaway.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import plus.hideaway.mod.feat.ws.WebSocketManager;
import plus.hideaway.mod.util.Chars;

public class Prompt {

    public static void info(String message) {
        MinecraftClient.getInstance().player.sendMessage(
            MutableText.of(TextContent.EMPTY)
                .append(Text.literal("[").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                .append(Text.literal("Hideaway+").setStyle(Style.EMPTY.withColor(Formatting.GOLD)))
                .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                .append(Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.AQUA)))
        );
    }

    public static void error(String message) {
        MinecraftClient.getInstance().player.sendMessage(
            MutableText.of(TextContent.EMPTY)
                .append(Text.literal("[").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                .append(Chars.badge())
                .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                .append(Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.RED)))
        );
    }

    public static void trace(String message) {
        if (HideawayPlus.debug()) {
            MinecraftClient.getInstance().player.sendMessage(
                MutableText.of(TextContent.EMPTY)
                    .append(Text.literal("[").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                    .append(Chars.badge())
                    .append(Text.literal(" Debug").setStyle(Style.EMPTY.withColor(Formatting.DARK_PURPLE)))
                    .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                    .append(Text.literal(message).setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE)))
            );
        }
    }

    public static void outage(String head, String desc, WebSocketManager.OutageSeverity sev) {
        Formatting f = Formatting.YELLOW;
        switch (sev) {
            case MILD -> { f = Formatting.YELLOW; }
            case MODERATE -> { f = Formatting.GOLD; }
            case SEVERE -> { f = Formatting.RED; }
        }

        MinecraftClient.getInstance().player.sendMessage(
                MutableText.of(TextContent.EMPTY)
                        .append(Text.literal("\n"))
                        .append(Chars.badge())
                        .append(Text.literal(" Outage: ").setStyle(Style.EMPTY.withColor(f).withBold(true)))
                        .append(Text.literal(head).setStyle(Style.EMPTY.withColor(f))
                        .append(Text.literal("\n"))
                        .append(Text.literal(desc).setStyle(Style.EMPTY.withColor(f))
                        .append(Text.literal("\n\n"))
        )));
    }

    public static void trace(Text message) {
        if (HideawayPlus.debug()) {
            MinecraftClient.getInstance().player.sendMessage(
                    MutableText.of(TextContent.EMPTY)
                            .append(Text.literal("[").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                            .append(Chars.badge())
                            .append(Text.literal(" Debug").setStyle(Style.EMPTY.withColor(Formatting.DARK_PURPLE)))
                            .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                            .append(MutableText.of(message.getContent()))
            );
        }
    }

    public static void traceWithClick(Text message, String hover) {
        if (HideawayPlus.debug()) {
            MinecraftClient.getInstance().player.sendMessage(
                    MutableText.of(TextContent.EMPTY)
                            .append(Text.literal("[").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                            .append(Chars.badge())
                            .append(Text.literal("Debug").setStyle(Style.EMPTY.withColor(Formatting.DARK_PURPLE)))
                            .append(Text.literal("] ").setStyle(Style.EMPTY.withColor(Formatting.GRAY)))
                            .append(MutableText.of(message.getContent()))
                            .setStyle(Style.EMPTY.withHoverEvent(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            Text.literal(hover)
                                    )
                            ).withClickEvent(
                                new ClickEvent(
                                    ClickEvent.Action.COPY_TO_CLIPBOARD,
                                    message.getString()
                                )
                            ))
            );
        }
    }

}
