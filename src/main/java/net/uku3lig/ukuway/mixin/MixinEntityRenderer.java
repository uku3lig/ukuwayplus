package net.uku3lig.ukuway.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.util.Chars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {
    @Shadow
    protected abstract boolean hasLabel(T entity);

    @Shadow
    protected abstract void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    // TODO use modifyargs like i did in totemcounter
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (this.hasLabel(entity) && UkuwayPlus.connected() && entity instanceof PlayerEntity) {
            MutableText newName = entity.getDisplayName().copy();

            if (FriendListManager.getFriends().contains(entity.getUuid())) {
                Chars.addBadge(newName, Chars.friendBadge());
            }

            this.renderLabelIfPresent(entity, newName, matrices, vertexConsumers, light);
            ci.cancel();
        }
    }
}
