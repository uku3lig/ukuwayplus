package net.uku3lig.ukuway.ui;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Wardrobe {
    public static final Vec3d LOCATION = new Vec3d(66.5f, 5f, -130.5f);

    @Getter
    private static final Set<UUID> wardrobePlayers = new HashSet<>();


    public static void tick() {
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        if (clientPlayer == null) return;

        if (!clientPlayer.isSpectator() && !clientPlayer.getPos().isInRange(LOCATION, 5)) {
            wardrobePlayers.clear();
            return;
        }

        int boxWidth = 3 * 2;
        Box boundingBox = Box.of(clientPlayer.getPos(), boxWidth, boxWidth, boxWidth);
        List<PlayerEntity> playerList = clientPlayer.clientWorld.getPlayers(TargetPredicate.createNonAttackable(), clientPlayer, boundingBox);

        wardrobePlayers.clear();
        for (PlayerEntity player : playerList) {
            if (player == clientPlayer) continue;
            wardrobePlayers.add(player.getUuid());
        }
    }

    private Wardrobe() {
    }
}
