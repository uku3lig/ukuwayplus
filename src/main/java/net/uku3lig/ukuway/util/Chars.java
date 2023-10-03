package net.uku3lig.ukuway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

@Getter
@AllArgsConstructor
public enum Chars {
    DISC("\uE010", null),
    FRIEND_BADGE("\uE002", "tooltip.hp.friend"),
    USER_BADGE("\uE001", null),
    SETTINGS_ICON("\uEF01", null);

    private final String character;
    private final String translationKey;

    private static final Identifier FONT = new Identifier("hideaway_plus", "text");

    public MutableText with(Text next) {
        MutableText text = Text.literal(character).styled(s -> s.withFont(FONT).withFormatting(Formatting.WHITE));

        if (translationKey != null) {
            Text hoverText = Text.translatable(translationKey).styled(s2 -> s2.withColor(Formatting.GOLD));
            text = text.styled(s -> s.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText)));
        }

        return Text.empty().append(text).append(next);
    }

    public MutableText formatted() {
        return with(Text.empty());
    }
}
