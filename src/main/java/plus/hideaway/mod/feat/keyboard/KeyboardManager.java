package plus.hideaway.mod.feat.keyboard;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.Prompt;
import plus.hideaway.mod.feat.ui.ConfigUI;
import plus.hideaway.mod.feat.ui.JukeboxUI;

public class KeyboardManager {
    public KeyboardManager() {
        var jukebox = new KeyBinding("categories.hplus.jukebox", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "categories.hplus");
        var menu = new KeyBinding("categories.hplus.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "categories.hplus");
//      var debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menu.wasPressed()) {
                HideawayPlus.client().setScreen(new ConfigUI());
            }
            while (jukebox.wasPressed()) {
                if (HideawayPlus.config().jukebox()) HideawayPlus.client().setScreen(new JukeboxUI());
            }
        });

        KeyBindingHelper.registerKeyBinding(menu);
        KeyBindingHelper.registerKeyBinding(jukebox);
    }
}
