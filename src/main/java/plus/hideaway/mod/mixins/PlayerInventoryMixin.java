package plus.hideaway.mod.mixins;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.hideaway.mod.HideawayPlus;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(at = @At("RETURN"), method = "addStack(Lnet/minecraft/item/ItemStack;)I")
    public void addItem(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        HideawayPlus.toastStack().add(
                Text.empty().append(
                        Text.literal("+ " + stack.getCount() + "x ").append(stack.getName())
                                .setStyle(Style.EMPTY.withColor(Formatting.GREEN))
                )
        );
    }

}
