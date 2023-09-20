package continued.hideaway.mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class Chars {
    public static Component badge() {
        return Component.literal("\uE001").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_continued:text")
        )).withStyle(ChatFormatting.WHITE);
    }
    public static Component disc() {
        return Component.literal("\uE003 ").setStyle(Style.EMPTY.withFont(
                new ResourceLocation("hideaway_continued:text")
        )).withStyle(ChatFormatting.WHITE);
    }
}
