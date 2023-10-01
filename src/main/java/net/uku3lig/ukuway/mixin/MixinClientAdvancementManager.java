package net.uku3lig.ukuway.mixin;

import net.uku3lig.ukuway.util.StaticValues;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientAdvancementManager.class)
public class MixinClientAdvancementManager {

    @Shadow @Final private AdvancementManager manager;

    @Inject(method = "onAdvancements", at = @At("TAIL"))
    private void update(AdvancementUpdateS2CPacket packet, CallbackInfo ci) {
        this.manager.getAdvancements().forEach(adv -> {
            if (adv.getDisplay() == null) return;
            if (adv.getDisplay().getTitle().getString().contains("\uE256") && adv.getDisplay().getTitle().getString().contains("Added")) StaticValues.friendsCheck = false;
        });
    }
}
