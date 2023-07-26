package plus.hideaway.mod.mixins.accessors;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(GameMenuScreen.class)
public interface GameMenuScreenAccessor {
    @Invoker("createButton")
    ButtonWidget createButton(Text text, Supplier<Screen> screenSupplier);
    @Invoker("createUrlButton")
    ButtonWidget createUrlButton(Text text, String url);
    @Invoker("disconnect")
    void disconnect();
}
