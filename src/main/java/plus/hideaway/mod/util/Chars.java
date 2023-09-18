package plus.hideaway.mod.util;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Chars {
    public static Text badge() {
        return Text.literal("\uE001").setStyle(Style.EMPTY.withFont(
                new Identifier("hideawayplus:text")
        )).formatted(Formatting.WHITE);
    }
    public static Text disc() {
        return Text.literal("\uE003 ").setStyle(Style.EMPTY.withFont(
                new Identifier("hideawayplus:text")
        )).formatted(Formatting.WHITE);
    }
}
