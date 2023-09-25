package continued.hideaway.mod.mixins;

import com.mojang.authlib.GameProfile;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ext.PlayerAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerAccessor {
    @Mutable
    @Shadow @Final private GameProfile gameProfile;

    @Inject(at = @At("RETURN"), method = "drop(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/item/ItemEntity;")
    public void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        HideawayPlus.toastStack().add(
            Component.empty().append(
                Component.literal("- " + stack.getCount() + "x ").append(stack.getHoverName())
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            )
        );
    }

    @Override
    public void hp$setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }
}
