package continued.hideaway.mod.mixin;

import continued.hideaway.mod.util.StaticValues;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientAdvancements.class)
public class ClientAdvancementsMixin {

    @Shadow @Final private AdvancementList advancements;

    @Inject(at = @At("TAIL"), method = "update")
    private void update(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci) {
        this.advancements.getAllAdvancements().forEach((advancement) -> {
            if (advancement.getDisplay() == null) return;
            if (advancement.getDisplay().getTitle().getString().contains("\uE256") && advancement.getDisplay().getTitle().getString().contains("Added")) StaticValues.friendsCheck = false;
        });
    }
}
