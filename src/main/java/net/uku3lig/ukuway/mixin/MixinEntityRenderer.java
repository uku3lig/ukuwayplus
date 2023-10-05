package net.uku3lig.ukuway.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.uku3lig.ukuway.ui.FriendListManager;
import net.uku3lig.ukuway.util.Chars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Locale;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    public void renderLabel(Args args) {
        Entity entity = args.get(0);
        Text text = args.get(1);

        if (FriendListManager.getFriends().contains(entity.getEntityName().toLowerCase(Locale.ROOT))) {
            text = Chars.FRIEND_BADGE.with(text);
        }

        if (text.getString().equalsIgnoreCase("undefined")) {
            text = Text.empty();
        }

        args.set(1, text);
    }
}
