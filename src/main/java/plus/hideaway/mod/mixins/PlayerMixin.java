package plus.hideaway.mod.mixins;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.hideaway.mod.HideawayPlus;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Inject(at = @At("RETURN"), method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;")
    public void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        HideawayPlus.toastStack().add(
            Text.empty().append(
                Text.literal("- " + stack.getCount() + "x ").append(stack.getName())
                    .setStyle(Style.EMPTY.withColor(Formatting.RED))
            )
        );
    }
}
