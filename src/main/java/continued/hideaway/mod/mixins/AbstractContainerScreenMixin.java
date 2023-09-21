package continued.hideaway.mod.mixins;

import continued.hideaway.mod.feat.ext.AbstractContainerScreenAccessor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements AbstractContainerScreenAccessor {
    @Shadow protected abstract void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type);

    @Override
    public void hp$slotChange(Slot slot, int slotId, int mouseButton, ClickType type) {
        slotClicked(slot, slotId, mouseButton, type);
    }
}
