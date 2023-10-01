package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.util.Chars;
import net.uku3lig.ukuway.util.DisplayNameUtil;
import net.uku3lig.ukuway.util.StaticValues;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {
    @Shadow protected abstract boolean hasLabel(T entity);

    @Shadow protected abstract void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (this.hasLabel(entity) && HideawayPlus.connected() && entity instanceof PlayerEntity) {
            String playerName = DisplayNameUtil.ignFromDisplayName(entity.getDisplayName().getString());
            String playerID = DisplayNameUtil.modPlayerID(playerName);

            MutableText newName = entity.getDisplayName().copy();

            if (StaticValues.friendsUsernames.contains(playerName))
                Chars.addBadge(newName, Chars.friendBadge());

            if (StaticValues.devs.contains(playerID))
                Chars.addBadge(newName, Chars.devBadge());
            else if (StaticValues.teamMembers.contains(playerID))
                Chars.addBadge(newName, Chars.teamBadge());
            else if (StaticValues.translators.contains(playerID))
                Chars.addBadge(newName, Chars.translatorBadge());
            else if (StaticValues.users.containsKey(playerID))
                Chars.addBadge(newName, Chars.userBadge());

            this.renderLabelIfPresent(entity, newName, matrices, vertexConsumers, light);
        }
        ci.cancel();
    }
}
