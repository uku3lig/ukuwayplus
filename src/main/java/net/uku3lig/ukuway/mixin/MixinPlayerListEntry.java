package net.uku3lig.ukuway.mixin;

import com.mojang.authlib.GameProfile;
import net.uku3lig.ukuway.util.StaticValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
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
        if (player != null && !StaticValues.wardrobeEntity.isEmpty()) {
            String hidePlayer = this.profile.getId().toString();

            if (StaticValues.wardrobeEntity.contains(hidePlayer)) {
                cir.setReturnValue(mapper.apply(player));
            }
        }
    }
}
