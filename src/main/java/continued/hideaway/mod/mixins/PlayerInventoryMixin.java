package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {

    @Inject(at = @At("RETURN"), method = "add(Lnet/minecraft/world/item/ItemStack;)Z")
    public void addItem(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        HideawayPlus.toastStack().add(
                Component.empty().append(
                        Component.literal("+ " + stack.getCount() + "x ").append(stack.getHoverName())
                                .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
                )
        );
    }

}
