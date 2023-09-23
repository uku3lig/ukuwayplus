package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ui.ConfigUI;
import continued.hideaway.mod.mixins.ext.GridLayoutAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PauseScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    protected GameMenuScreenMixin(Component title) {
        super(title);
    }

    /**
     * @author Skye Redwood
     * @reason Adding extra buttons to pause menu
     *
     * God, this method is an absolute fucking mess
     */
    @Inject(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout;visitWidgets(Ljava/util/function/Consumer;)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void idk(CallbackInfo ci, GridLayout gridLayout, GridLayout.RowHelper rowHelper, Component component) {

        if (gridLayout != null) {
            final List<LayoutElement> buttons = ((GridLayoutAccessor) gridLayout).getChildren();
            if (HideawayPlus.connected()) {
                // literally the only thing I added :sob:
                buttons.add(Button.builder(Component.literal("Hideaway: Continued Settings"), button -> {
                    this.minecraft.setScreen(new ConfigUI());
                }).width(204).build());
            }
        }
    }

}
