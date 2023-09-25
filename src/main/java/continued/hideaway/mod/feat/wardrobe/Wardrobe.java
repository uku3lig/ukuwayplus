package continued.hideaway.mod.feat.wardrobe;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.location.Location;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Wardrobe {

    public static void tick() {
        if (HideawayPlus.location() != Location.WARDROBE_WHEEL) {
            StaticValues.wardrobeEntity.clear();
            return;
        }
        if (HideawayPlus.player() == null) return;

        int distance = 3;
        AABB boundingBox = new AABB(HideawayPlus.player().position().x() + distance, HideawayPlus.player().position().y() + distance, HideawayPlus.player().position().z() + distance, HideawayPlus.player().position().x() - distance, HideawayPlus.player().position().y() - distance, HideawayPlus.player().position().z() - distance);
        List<Player> playerList = HideawayPlus.player().level().getNearbyPlayers(TargetingConditions.forNonCombat(), HideawayPlus.player(), boundingBox);

        StaticValues.wardrobeEntity.clear();
        for (Player player : playerList) {
            if (player == HideawayPlus.player()) continue;
            if (StaticValues.wardrobeEntity.contains(player.getUUID().toString())) continue;
            StaticValues.wardrobeEntity.add(player.getUUID().toString());
        }
    }
}
