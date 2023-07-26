package plus.hideaway.mod.feat.keyboard;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import plus.hideaway.mod.feat.menu.MenuScreen;

public class KeyboardManager {
    public KeyboardManager() {
        var menu = new KeyBinding("categories.hplus.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "categories.hplus");
        var debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (debug.wasPressed()) {
//                if (HideawayPlus.gui() == null) {
//                    Prompt.trace("There is no open GUI.");
//                } else {
//                    StringBuilder s = new StringBuilder();
//                    for (char c : HideawayPlus.gui().toString().toCharArray()) {
//                        s.append("\\u" + Integer.toHexString(c | 0x10000).substring(1));
//                    }
//                    Prompt.trace("Escaped GUI Title: " + s.toString());
//                    Prompt.trace("Unescaped GUI Title: ");
//                    Prompt.traceWithClick(HideawayPlus.gui(), "Click to copy");
//                }
            }

            while (menu.wasPressed()) {
                MinecraftClient.getInstance().setScreen(
                    new MenuScreen(new GameMenuScreen(true))
                );
            }
        });

        KeyBindingHelper.registerKeyBinding(menu);
        KeyBindingHelper.registerKeyBinding(debug);
    }
}
