package continued.hideaway.mod.util;

import net.minecraft.client.Minecraft;
import org.intellij.lang.annotations.RegExp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayNameUtil {
    public static String ignFromDisplayName(String content) {
        Pattern pattern = Pattern.compile("[\\w]+");
        Matcher matcher = pattern.matcher(content);
        String username = matcher.find() ? matcher.group(0) : null;
        if (username == null || username.isEmpty()) {
            return content;
        } else return username;
    }

    public static String newRankFromRank(String content) {
        String rank = content.substring(0, content.indexOf(" "));
        switch (rank) {
            // Admin
            case "\uE29A": return "default";
            // Mod
            case "\uE29B": return "mod";
            // TEAM
            case "\uE608": return "team";
            // MEDIA
            case "\uE29C": return "media";
            // GUIDE
            case "\uE29D": return "guide";
            // EVENTS
            case "\uE29E": return "events";
            // VACATION CLUB+
            case "\uE29F": return "vacation_club_plus";
            // VACATION CLUB
            case "\uE5F8": return "vacation_club";
            // GUEST
            case "\uE5F9": return "guest";


            // COMMON
            case "\uE2A0": return "common";
            // UNCOMMON
            case "\uE2A1": return "rare";
            // RARE
            case "\uE2A2": return "rare";
            // EXOTIC
            case "\uE2A3": return "rare";
            // MYTHICAL
            case "\uE2A4": return "rare";
            // LIMITED
            case "\uE2A5": return "rare";
            // SECRET RARE
            case "\uE2A6": return "rare";

            // BAKEND
            case "\uE2A7": return "rare";
            // CHILL
            case "\uE607": return "rare";

            // TRINKET
            case "\uE2A8": return "rare";
            // ITEM
            case "\uE2A9": return "rare";
            // HAT
            case "\uE2AA": return "rare";
            // FURNITURE
            case "\uE2AB": return "rare";
            // BADGE
            case "\uE2AC": return "rare";
            // PICTURE
            case "\uE2AD": return "rare";
            // SPECIAL
            case "\uE2AE": return "rare";
            // AUTHOR
            case "\uE2AF": return "rare";
            // SHELL
            case "\uE2B0": return "rare";
            // FISH
            case "\uE2B1": return "rare";
        }

        return rank;
    }

    public static String clientUsername() {
        return Minecraft.getInstance().player.getName().getString().trim();
    }

    public static String modPlayerID(String username) {
        if (StaticValues.modUsers.containsValue(username)) {
            for (String key : StaticValues.modUsers.keySet()) {
                if (StaticValues.modUsers.get(key).equals(username)) {
                    return key;
                }
            }
        }
        return null;
    }

    public static String nameFromChatMessage(String chatMessage) {
        String username = chatMessage.replaceAll(":(.*)", "");
        username = username.replaceAll("^\\S+ ", "");
        if (username.isEmpty()) {
            return "";
        } else return username;
    }
}
