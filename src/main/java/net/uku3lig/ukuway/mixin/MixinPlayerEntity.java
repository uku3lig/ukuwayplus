package net.uku3lig.ukuway.mixin;

import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.wardrobe.Wardrobe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    @Final
    @Shadow
    protected static TrackedData<Byte> PLAYER_MODEL_PARTS;

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique @Setter
    private ItemStack oldHeadStack = null;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        if (Wardrobe.getWardrobeEntities().contains(this.getUuid())) {
            ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            if (clientPlayer != null) {
                this.getDataTracker().set(PLAYER_MODEL_PARTS, clientPlayer.getDataTracker().get(PLAYER_MODEL_PARTS));
            }
        } else {
            UkuwayPlus.setCosmeticVisibility(this, oldHeadStack, this::setOldHeadStack);
        }
    }
}
