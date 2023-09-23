package continued.hideaway.mod.feat.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ui.InventorySlotsUI;
import continued.hideaway.mod.feat.ui.JukeboxUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyboardManager {
    public KeyboardManager() {
        var jukebox = new KeyMapping("key.hp.jukebox", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "categories.hp");
        var luggage = new KeyMapping("key.hp.luggage", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, "categories.hp");
        var wardrobe = new KeyMapping("key.hp.wardrobe", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "categories.hp");
        var profile = new KeyMapping("key.hp.profile", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, "categories.hp");
        var friends = new KeyMapping("key.hp.friends", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, "categories.hp");
        // var debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (jukebox.consumeClick()) {
                if (HideawayPlus.config().jukebox()) client.setScreen(new JukeboxUI());
            }
            while (luggage.consumeClick()) {
                InventorySlotsUI.clickSlot(1, client);
            }
            while (wardrobe.consumeClick()) {
                InventorySlotsUI.clickSlot(2, client);
            }
            while (profile.consumeClick()) {
                InventorySlotsUI.clickSlot(3, client);
            }
            while (friends.consumeClick()) {
                InventorySlotsUI.clickSlot(4, client);
            }
        });

        KeyBindingHelper.registerKeyBinding(jukebox);
        KeyBindingHelper.registerKeyBinding(luggage);
        KeyBindingHelper.registerKeyBinding(wardrobe);
        KeyBindingHelper.registerKeyBinding(profile);
        KeyBindingHelper.registerKeyBinding(friends);
    }
}
