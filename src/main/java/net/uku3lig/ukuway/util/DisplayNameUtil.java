package net.uku3lig.ukuway.util;


public class DisplayNameUtil {
    public static String nameFromChatMessage(String chatMessage) {
        String username = chatMessage.replaceAll(":(.*)", "");
        username = username.replaceAll("^\\S+ ", "");
        if (username.isEmpty()) {
            return "";
        } else return username;
    }

    private DisplayNameUtil() {
    }
}
