package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.config.UkuwayConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.wardrobe.Wardrobe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeFeatureRenderer.class)
public class MixinCapeFeatureRenderer {
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", cancellable = true)
    public void render(MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, AbstractClientPlayerEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (UkuwayPlus.connected()) {
            if (!UkuwayConfig.get().isHideCosmetics()) {
                ItemStack playerChestplate = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
                if (playerChestplate != ItemStack.EMPTY) {
                    info.cancel();
                }
            } else if (Wardrobe.getWardrobeEntities().contains(livingEntity.getUuid()) && !Wardrobe.getWardrobeArmorStands().isEmpty()) {
                info.cancel();
            }
        }
    }
}
