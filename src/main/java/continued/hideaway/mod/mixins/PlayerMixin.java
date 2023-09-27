package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Final
    @Shadow
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MODE_CUSTOMISATION;

    @Shadow public abstract void setItemSlot(EquipmentSlot slot, ItemStack stack);
    @Unique private ItemStack oldHeadStack = null;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        Player player = ((Player) (Object) this);
        if (!StaticValues.wardrobeEntity.isEmpty() && StaticValues.wardrobeEntity.contains(player.getStringUUID())) {
            if (HideawayPlus.client().player == null) return;
            Byte b = (HideawayPlus.client().player).getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION);
            player.getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, b);
        }

        if (!StaticValues.wardrobeEntity.contains(player.getStringUUID())) {
            boolean hasCosmetic = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == Items.LEATHER_HORSE_ARMOR;
            if (hasCosmetic) oldHeadStack = player.getItemBySlot(EquipmentSlot.HEAD);
            if (hasCosmetic && HideawayPlus.connected() && HideawayPlus.config().hideCosmetics())
                this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            if (!hasCosmetic && HideawayPlus.connected() && !HideawayPlus.config().hideCosmetics() && oldHeadStack != null)
                this.setItemSlot(EquipmentSlot.HEAD, oldHeadStack);
        }

    }

    @Inject(at = @At("RETURN"), method = "drop(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/item/ItemEntity;")
    public void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        HideawayPlus.toastStack().add(
            Component.empty().append(
                Component.literal("- " + stack.getCount() + "x ").append(stack.getHoverName())
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            )
        );
    }
}
