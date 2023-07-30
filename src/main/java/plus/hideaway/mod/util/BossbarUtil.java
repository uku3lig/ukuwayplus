package plus.hideaway.mod.util;

import net.minecraft.entity.boss.BossBar;
import plus.hideaway.mod.HideawayPlus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossbarUtil {

    public static String getCurrentRoomName() {
        if (HideawayPlus.connected()) {
            BossBar bar = HideawayPlus.player().getServer().getBossBarManager().getAll().stream().findFirst().get();
            String text = bar.getName().getString();
            Pattern pattern = Pattern.compile("\\w*'s Room", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group().replace("'s Room", "");
            } else return null;
        } else return null;
    }

}
