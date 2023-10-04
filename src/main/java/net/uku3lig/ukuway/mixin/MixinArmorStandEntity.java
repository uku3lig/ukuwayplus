package net.uku3lig.ukuway.mixin;

import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.ui.Wardrobe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity extends LivingEntity {
    protected MixinArmorStandEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique @Setter
    private ItemStack oldHeadStack = null;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (UkuwayPlus.isConnected() && !Wardrobe.getWardrobeArmorStands().contains(this.getUuid())) {
            UkuwayPlus.setCosmeticVisibility(this, oldHeadStack, this::setOldHeadStack);
        }
    }

}
