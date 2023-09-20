package plus.hideaway.mod.feat.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.Prompt;
import plus.hideaway.mod.feat.ui.ConfigUI;
import plus.hideaway.mod.feat.ui.JukeboxUI;

public class KeyboardManager {
    public KeyboardManager() {
        var jukebox = new KeyMapping("categories.hplus.jukebox", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "categories.hplus");
        var menu = new KeyMapping("categories.hplus.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "categories.hplus");
//      var debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menu.consumeClick()) {
                HideawayPlus.client().setScreen(new ConfigUI());
            }
            while (jukebox.consumeClick()) {
                if (HideawayPlus.config().jukebox()) HideawayPlus.client().setScreen(new JukeboxUI());
            }
        });

        KeyBindingHelper.registerKeyBinding(menu);
        KeyBindingHelper.registerKeyBinding(jukebox);
    }
}
