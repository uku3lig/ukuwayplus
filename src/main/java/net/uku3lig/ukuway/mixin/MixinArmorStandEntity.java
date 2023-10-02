package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.uku3lig.ukuway.wardrobe.Wardrobe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity {
    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Unique
    private ItemStack oldHeadStack = null;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci){
        ArmorStandEntity armorStand = (ArmorStandEntity) (Object) this;
        boolean hasCosmetic = armorStand.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.LEATHER_HORSE_ARMOR;
        if (hasCosmetic) oldHeadStack = armorStand.getEquippedStack(EquipmentSlot.HEAD);

        if (!Wardrobe.getWardrobeArmorStands().contains(armorStand.getUuid())) {
          if (hasCosmetic && UkuwayPlus.connected() && UkuwayConfig.get().isHideCosmetics()) this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
          if (!hasCosmetic && UkuwayPlus.connected() && !UkuwayConfig.get().isHideCosmetics() && oldHeadStack != null) this.equipStack(EquipmentSlot.HEAD, oldHeadStack);
        }
    }

}
