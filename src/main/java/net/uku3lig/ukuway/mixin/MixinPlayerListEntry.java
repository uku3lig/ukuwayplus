package net.uku3lig.ukuway.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import net.uku3lig.ukuway.ui.Wardrobe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(PlayerListEntry.class)
public class MixinPlayerListEntry {
    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "getSkinTexture", at = @At("RETURN"), cancellable = true)
    public void changePlayerSkin(CallbackInfoReturnable<Identifier> cir) {
        change(cir, ClientPlayerEntity::getSkinTexture);
    }

    @Inject(method = "getModel", at = @At("RETURN"), cancellable = true)
    public void changePlayerModel(CallbackInfoReturnable<String> cir) {
        change(cir, ClientPlayerEntity::getModel);
    }

    @Inject(method = "getCapeTexture", at = @At("RETURN"), cancellable = true)
    public void changePlayerCape(CallbackInfoReturnable<Identifier> cir) {
        change(cir, ClientPlayerEntity::getCapeTexture);
    }

    @Inject(method = "getElytraTexture", at = @At("RETURN"), cancellable = true)
    public void changePlayerElytra(CallbackInfoReturnable<Identifier> cir) {
        change(cir, ClientPlayerEntity::getElytraTexture);
    }

    @Unique
    private <T> void change(CallbackInfoReturnable<T> cir, Function<ClientPlayerEntity, T> mapper) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && !Wardrobe.getWardrobePlayers().isEmpty() && Wardrobe.getWardrobePlayers().contains(this.profile.getId())) {
                cir.setReturnValue(mapper.apply(player));
        }
    }
}
