package continued.hideaway.mod.mixins;

import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.Prompt;
import continued.hideaway.mod.mixins.ext.GridLayoutAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    @Shadow @Final private static Component RETURN_TO_GAME;
    @Shadow @Final private static Component ADVANCEMENTS;
    @Shadow @Final private static Component STATS;
    @Shadow @Final private static Component SEND_FEEDBACK;
    @Shadow @Final private static Component REPORT_BUGS;
    @Shadow @Final private static Component OPTIONS;
    @Shadow @Final private static Component SHARE_TO_LAN;
    @Shadow @Final private static Component PLAYER_REPORTING;
    @Shadow @Final private static Component RETURN_TO_MENU;
    @Shadow @Final private static Component DISCONNECT;
    @Shadow @Final private static Component SAVING_LEVEL;
    @Shadow @Final private static Component GAME;
    @Shadow @Final private static Component PAUSED;

    @Shadow @Final private boolean showPauseMenu;
    @Shadow @Nullable private Button disconnectButton;

    @Shadow protected abstract void onDisconnect();

    @Shadow protected abstract Button openScreenButton(Component message, Supplier<Screen> screenSupplier);

    @Shadow protected abstract Button openLinkButton(Component message, String linkUri);

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
            if (HideawayContinued.connected()) {
                // literally the only thing I added :sob:
                buttons.add(Button.builder(Component.literal("Hideaway: Continued Settings"), button -> {
                    button.active = false;
//                 HideawayPlus.config().show();
                    Prompt.trace("I hate cloth config (maybe?)");
                }).width(204).build());
            }
        }
    }

}
