package continued.hideaway.mod.feat.ext;

import net.minecraft.network.chat.Component;

public interface InGameHudAccessor {

    Component hp$getOverlayMessage();
    Component hp$getTitleMessage();
    Component hp$getSubtitleMessage();

    float hp$getExperiencePoints();
    int hp$getExperienceLevel();
}
