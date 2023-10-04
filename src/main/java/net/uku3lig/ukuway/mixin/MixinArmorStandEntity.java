package net.uku3lig.ukuway.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.ui.Wardrobe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity extends LivingEntity {
    protected MixinArmorStandEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        ItemStack headStack = this.getEquippedStack(EquipmentSlot.HEAD);

        if (UkuwayPlus.isConnected() && headStack.isOf(Items.LEATHER_HORSE_ARMOR)) {
            if (this.getPos().isInRange(Wardrobe.LOCATION, 5)) {
                this.getWorld().getPlayers().stream()
                        .filter(p -> Wardrobe.getWardrobePlayers().contains(p.getUuid()))
                        .forEach(p -> p.equipStack(EquipmentSlot.CHEST, headStack));
            }
            this.kill();
        }
    }
}
