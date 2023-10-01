package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.util.StaticValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
    @Final
    @Shadow
    protected static TrackedData<Byte> PLAYER_MODEL_PARTS;

    @Shadow
    public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Unique
    private ItemStack oldHeadStack = null;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if (!StaticValues.wardrobeEntity.isEmpty() && StaticValues.wardrobeEntity.contains(player.getUuidAsString())) {
            ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            if (clientPlayer == null) return;
            Byte b = clientPlayer.getDataTracker().get(PLAYER_MODEL_PARTS);
            player.getDataTracker().set(PLAYER_MODEL_PARTS, b);
        }

        if (!StaticValues.wardrobeEntity.contains(player.getUuidAsString())) {
            boolean hasCosmetic = player.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.LEATHER_HORSE_ARMOR;
            if (hasCosmetic) oldHeadStack = player.getEquippedStack(EquipmentSlot.HEAD);
            if (hasCosmetic && HideawayPlus.connected() && UkuwayConfig.get().isHideCosmetics())
                this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
            if (!hasCosmetic && HideawayPlus.connected() && !UkuwayConfig.get().isHideCosmetics() && oldHeadStack != null)
                this.equipStack(EquipmentSlot.HEAD, oldHeadStack);
        }

    }
}
