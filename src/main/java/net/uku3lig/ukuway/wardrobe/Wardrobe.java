package net.uku3lig.ukuway.wardrobe;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Wardrobe {
    @Getter
    private static final Set<UUID> wardrobeEntities = new HashSet<>();
    @Getter
    private static final Set<UUID> wardrobeArmorStands = new HashSet<>();


    public static void tick() {
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        if (clientPlayer == null) return;

        if (!clientPlayer.isSpectator()) {
            wardrobeEntities.clear();
            return;
        }

        int boxWidth = 3 * 2;
        Box boundingBox = Box.of(clientPlayer.getPos(), boxWidth, boxWidth, boxWidth);
        List<PlayerEntity> playerList = clientPlayer.clientWorld.getPlayers(TargetPredicate.createNonAttackable(), clientPlayer, boundingBox);
        List<ArmorStandEntity> armourStandList = clientPlayer.clientWorld.getNonSpectatingEntities(ArmorStandEntity.class, boundingBox);

        wardrobeEntities.clear();
        for (PlayerEntity player : playerList) {
            if (player == clientPlayer) continue;
            wardrobeEntities.add(player.getUuid());
        }

        wardrobeArmorStands.clear();
        wardrobeArmorStands.addAll(armourStandList.stream().map(ArmorStandEntity::getUuid).toList());
    }

    private Wardrobe() {
    }
}
