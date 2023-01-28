package dev.abodactyl.cce.mixins;

import dev.abodactyl.cce.CCEClient;
import dev.abodactyl.cce.feat.player.ModPlayer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class NametagMixin {
    @Inject(at = @At("RETURN"), method = "getPlayerName", cancellable = true)
    public void getDisplayName(CallbackInfoReturnable<Text> cir) {
        Text prev = cir.getReturnValue();
        if (CCEClient.connected()) {
            String content = prev.getString();
            String ign = content.split(" ")[1];
            MutableText after = MutableText.of(TextContent.EMPTY);
            ModPlayer player = CCEClient.playerCache().get(ign);

            switch (player.rank) {
                case ADMIN -> after.append(
                    Text.of(" \uE003")
                );
                case MODERATOR -> after.append(
                    Text.of(" \uE002")
                );
                case PLAYER -> after.append(
                    Text.of(" \uE001")
                );
                default -> after.append(
                    Text.of(" \uE004")
                );
            }
            cir.setReturnValue(after);
        } else cir.setReturnValue(prev);
    }
}
