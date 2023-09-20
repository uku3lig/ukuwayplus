package continued.hideaway.mod.mixins;

import continued.hideaway.mod.feat.ext.ItemStackAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemStackAccessor {
    @Shadow @Nullable private CompoundTag tag;

    @Override
    public CompoundTag getCompoundTag() {
        return this.tag;
    }
}
