package continued.hideaway.mod.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.api.API;
import continued.hideaway.mod.util.Chars;
import continued.hideaway.mod.util.DisplayNameUtil;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin <T extends Entity>{

    @Shadow protected abstract boolean shouldShowName(T entity);

    @Shadow protected abstract void renderNameTag(T entity, Component displayName, PoseStack poseStack, MultiBufferSource buffer, int packedLight);

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (this.shouldShowName(entity) && HideawayPlus.connected() && entity instanceof Player) {
            String result = DisplayNameUtil.ignFromDisplayName(entity.getDisplayName().getString());
            String playerID = "";
            if (StaticValues.modUsers.containsValue(result)) {
                for (String key : StaticValues.modUsers.keySet()) {
                    if (StaticValues.modUsers.get(key).equals(result)) {
                        playerID = key;
                        break;
                    }
                }
            }

            MutableComponent newName = MutableComponent.create(ComponentContents.EMPTY);
            newName.append(result);

            if (StaticValues.modUsers.containsValue(result) && !StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.badge());
            if (StaticValues.modDevelopers.contains(playerID)) newName.append(" ").append(Chars.devBadge());
            if (StaticValues.friendsList.contains(result)) newName.append(" ").append(Chars.friendBadge());
            if (!StaticValues.modUsers.containsValue(result) && !StaticValues.friendsList.contains(result)) newName = entity.getDisplayName().copy();

            this.renderNameTag(entity, newName, poseStack, buffer, packedLight);
        }
        ci.cancel();
    }
}
