package continued.hideaway.mod.mixins;

import com.mojang.authlib.GameProfile;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin {
    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "getSkinLocation", at = @At("RETURN"), cancellable = true)
    public void changePlayerSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        if (HideawayPlus.client().player != null && !StaticValues.wardrobeEntity.isEmpty()) {
            AbstractClientPlayer clientPlayer = HideawayPlus.client().player;
            String hidePlayer = this.profile.getId().toString();

            if (StaticValues.wardrobeEntity.contains(hidePlayer)) {
                cir.setReturnValue(clientPlayer.getSkinTextureLocation());
            }
        }
    }

    @Inject(method = "getModelName", at = @At("RETURN"), cancellable = true)
    public void changePlayerModel(CallbackInfoReturnable<String> cir) {
        if (HideawayPlus.client().player != null && !StaticValues.wardrobeEntity.isEmpty()) {
            AbstractClientPlayer clientPlayer = HideawayPlus.client().player;
            String hidePlayer = this.profile.getId().toString();

            if (StaticValues.wardrobeEntity.contains(hidePlayer)) {
                cir.setReturnValue(clientPlayer.getModelName());
            }
        }
    }

    @Inject(method = "getCapeLocation", at = @At("RETURN"), cancellable = true)
    public void changePlayerCape(CallbackInfoReturnable<ResourceLocation> cir) {
        if (HideawayPlus.client().player != null && !StaticValues.wardrobeEntity.isEmpty()) {
            AbstractClientPlayer clientPlayer = HideawayPlus.client().player;
            String hidePlayer = this.profile.getId().toString();

            if (StaticValues.wardrobeEntity.contains(hidePlayer)) {
                cir.setReturnValue(clientPlayer.getCloakTextureLocation());
            }
        }
    }

    @Inject(method = "getElytraLocation", at = @At("RETURN"), cancellable = true)
    public void changePlayerElytra(CallbackInfoReturnable<ResourceLocation> cir) {
        if (HideawayPlus.client().player != null && !StaticValues.wardrobeEntity.isEmpty()) {
            AbstractClientPlayer clientPlayer = HideawayPlus.client().player;
            String hidePlayer = this.profile.getId().toString();

            if (StaticValues.wardrobeEntity.contains(hidePlayer)) {
                cir.setReturnValue(clientPlayer.getElytraTextureLocation());
            }
        }
    }
}
