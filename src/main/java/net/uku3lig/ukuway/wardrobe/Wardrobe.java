package net.uku3lig.ukuway.wardrobe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.location.Location;
import net.uku3lig.ukuway.util.StaticValues;

import java.util.List;

public class Wardrobe {

    public static void tick() {
        if (HideawayPlus.location() != Location.WARDROBE_WHEEL) {
            StaticValues.wardrobeEntity.clear();
            return;
        }
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        if (clientPlayer == null) return;

        int boxWidth = 3 * 2;
        Box boundingBox = Box.of(clientPlayer.getPos(), boxWidth, boxWidth, boxWidth);
        List<PlayerEntity> playerList = clientPlayer.clientWorld.getPlayers(TargetPredicate.createNonAttackable(), clientPlayer, boundingBox);
        List<ArmorStandEntity> armourStandList = clientPlayer.clientWorld.getNonSpectatingEntities(ArmorStandEntity.class, boundingBox);

        StaticValues.wardrobeEntity.clear();
        for (PlayerEntity player : playerList) {
            if (player == clientPlayer || StaticValues.wardrobeEntity.contains(player.getUuid().toString())) continue;
            StaticValues.wardrobeEntity.add(player.getUuid().toString());
        }

        StaticValues.wardrobeArmourStand.clear();
        for (ArmorStandEntity armourStand : armourStandList) {
            if (StaticValues.wardrobeArmourStand.contains(armourStand.getUuid().toString())) continue;
            StaticValues.wardrobeArmourStand.add(armourStand.getUuid().toString());
        }
    }
}
