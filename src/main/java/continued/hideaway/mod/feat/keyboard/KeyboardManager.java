package continued.hideaway.mod.feat.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.feat.ui.ConfigUI;
import continued.hideaway.mod.feat.ui.JukeboxUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyboardManager {
    public KeyboardManager() {
        var jukebox = new KeyMapping("categories.hplus.jukebox", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "categories.hplus");
        var menu = new KeyMapping("categories.hplus.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "categories.hplus");
//      var debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menu.consumeClick()) {
                HideawayContinued.client().setScreen(new ConfigUI());
            }
            while (jukebox.consumeClick()) {
                if (HideawayContinued.config().jukebox()) HideawayContinued.client().setScreen(new JukeboxUI());
            }
        });

        KeyBindingHelper.registerKeyBinding(menu);
        KeyBindingHelper.registerKeyBinding(jukebox);
    }
}
