package net.uku3lig.ukuway.util;


import net.minecraft.client.MinecraftClient;

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
        return switch (rank) {
            // Admin
            case "\uE29A" -> "default";
            // Mod
            case "\uE29B" -> "mod";
            // TEAM
            case "\uE608" -> "team";
            // MEDIA
            case "\uE29C" -> "media";
            // GUIDE
            case "\uE29D" -> "guide";
            // EVENTS
            case "\uE29E" -> "events";
            // VACATION CLUB+
            case "\uE29F" -> "vacation_club_plus";
            // VACATION CLUB
            case "\uE5F8" -> "vacation_club";
            // GUEST
            case "\uE5F9" -> "guest";


            // COMMON
            case "\uE2A0" -> "common";
            // UNCOMMON
            case "\uE2A1" -> "rare";
            // RARE
            case "\uE2A2" -> "rare";
            // EXOTIC
            case "\uE2A3" -> "rare";
            // MYTHICAL
            case "\uE2A4" -> "rare";
            // LIMITED
            case "\uE2A5" -> "rare";
            // SECRET RARE
            case "\uE2A6" -> "rare";

            // BAKEND
            case "\uE2A7" -> "rare";
            // CHILL
            case "\uE607" -> "rare";

            // TRINKET
            case "\uE2A8" -> "rare";
            // ITEM
            case "\uE2A9" -> "rare";
            // HAT
            case "\uE2AA" -> "rare";
            // FURNITURE
            case "\uE2AB" -> "rare";
            // BADGE
            case "\uE2AC" -> "rare";
            // PICTURE
            case "\uE2AD" -> "rare";
            // SPECIAL
            case "\uE2AE" -> "rare";
            // AUTHOR
            case "\uE2AF" -> "rare";
            // SHELL
            case "\uE2B0" -> "rare";
            // FISH
            case "\uE2B1" -> "rare";
            default -> rank;
        };

    }

    public static String clientUsername() {
        return MinecraftClient.getInstance().player.getName().getString().trim();
    }

    public static String modPlayerID(String username) {
        if (StaticValues.users.containsValue(username)) {
            for (String key : StaticValues.users.keySet()) {
                if (key != null && StaticValues.users.get(key).equals(username)) {
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
