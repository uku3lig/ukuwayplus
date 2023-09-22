package continued.hideaway.mod.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.ItemRenderer;
import continued.hideaway.mod.HideawayContinued;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci) {
        if (minecraft.player != null && minecraft.options.getCameraType().isFirstPerson() && displayContext != ItemDisplayContext.GUI && HideawayContinued.connected()) {
            CompoundTag playerChestNbt = minecraft.player.getItemBySlot(EquipmentSlot.CHEST).getTagElement("PublicBukkitValues");
            CompoundTag stackNbt = itemStack.getTagElement("PublicBukkitValues");

            if (stackNbt != null && playerChestNbt != null) {
                String stackId = stackNbt.getString("pixelhideawaycore:random");
                String chestId = playerChestNbt.getString("pixelhideawaycore:random");
                
                if (stackId.equals(chestId)) {
                    ci.cancel();
                }
            }
        }
    }
}
