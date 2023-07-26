package plus.hideaway.mod.util;

import net.minecraft.client.MinecraftClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayNameUtil {
    public static String ignFromDisplayName(String content) {
        Pattern pattern = Pattern.compile("[\\w]+");
        Matcher matcher = pattern.matcher(content);
        String username = matcher.find() ? matcher.group(0) : null;
        if (username == null || username.length() == 0) {
            return content;
        } else return username;
    }

    public static String clientUsername() {
        return MinecraftClient.getInstance().player.getName().getString().trim();
    }
}
