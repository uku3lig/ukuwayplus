package net.uku3lig.ukuway.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Unique
    private static final String RANDOM_KEY = new Identifier("pixelhideawaycore", "random").toString();

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), cancellable = true)
    private void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (UkuwayPlus.isConnected() && modelTransformationMode != ModelTransformationMode.GUI
                && (UkuwayConfig.get().isHideCosmetics() || MinecraftClient.getInstance().options.getPerspective().isFirstPerson())
                && (isWorn(itemStack, EquipmentSlot.HEAD) || isWorn(itemStack, EquipmentSlot.CHEST))) {
            ci.cancel();
        }
    }

    /**
     * Checks if the item is worn in the specified slot by the client player.
     *
     * @param stack the item to be rendered
     * @param slot  the slot to check
     * @return {@code true} if the item is worn
     */
    @Unique
    private boolean isWorn(ItemStack stack, EquipmentSlot slot) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return false;

        NbtCompound stackNbt = stack.getSubNbt(UkuwayPlus.PUBLIC_BUKKIT_VALUES);
        NbtCompound slotNbt = player.getEquippedStack(slot).getSubNbt(UkuwayPlus.PUBLIC_BUKKIT_VALUES);
        if (stackNbt == null || slotNbt == null) return false;

        return stackNbt.getString(RANDOM_KEY).equals(slotNbt.getString(RANDOM_KEY));
    }
}
