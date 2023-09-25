package continued.hideaway.mod.feat.wardrobe;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ext.GameProfileAccessor;
import continued.hideaway.mod.feat.ext.PlayerAccessor;
import continued.hideaway.mod.feat.location.Location;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Wardrobe {

    public static void tick() {
//        if (HideawayPlus.location() != Location.WARDROBE_WHEEL) return;
//        if (HideawayPlus.player() == null) return;
//
//        AABB boundingBox = new AABB(HideawayPlus.player().position().x(), HideawayPlus.player().position().y(), HideawayPlus.player().position().z(), 10, 10, 10);
//        List<Player> playerList = HideawayPlus.player().level().getNearbyPlayers(TargetingConditions.DEFAULT, HideawayPlus.player(), boundingBox);
//
//        for (Player player : playerList) {
//            if (player == HideawayPlus.player()) continue;
//            ((PlayerAccessor)player).hp$setGameProfile(HideawayPlus.player().getGameProfile());
//            System.out.println(((GameProfileAccessor)HideawayPlus.player().getGameProfile()).getSkinTexture());
//            System.out.println(((GameProfileAccessor)player.getGameProfile()).getSkinTexture());
//        }
    }
}
