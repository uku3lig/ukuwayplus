package net.uku3lig.ukuway.util;

import net.uku3lig.ukuway.HideawayPlus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.boss.BossBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HUDUtil {
    public static String getCurrentRoomName() {
        if (HideawayPlus.connected()) {
            BossBar bar = MinecraftClient.getInstance().getServer().getBossBarManager().getAll().stream().findFirst().get();
            String text = bar.getName().getString();
            Pattern pattern = Pattern.compile("\\w*'s Room", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group().replace("'s Room", "");
            } else return null;
        } else return null;
    }
}
