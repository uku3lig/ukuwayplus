package net.uku3lig.ukuway.util;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class ChestCosmeticFeature<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final HeldItemRenderer heldItemRenderer;

    public ChestCosmeticFeature(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack chestArmor = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (chestArmor.isEmpty()) return;

        matrices.push();

        matrices.translate(0.0f, -0.25f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f));
        matrices.scale(0.625f, -0.625f, -0.625f);
        matrices.translate(0.0D, 2.195, 0.0D);

        this.heldItemRenderer.renderItem(entity, chestArmor, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, light);

        matrices.pop();
    }
}
