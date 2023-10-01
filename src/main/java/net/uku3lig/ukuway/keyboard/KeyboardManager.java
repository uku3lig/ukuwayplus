package net.uku3lig.ukuway.keyboard;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.ui.InventorySlotsUI;
import net.uku3lig.ukuway.ui.JukeboxScreen;
import org.lwjgl.glfw.GLFW;

public class KeyboardManager {
    public static final KeyBinding jukebox = new KeyBinding("key.hp.jukebox", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "categories.hp");
    public static final KeyBinding autoSell = new KeyBinding("key.hp.autoSell", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_S, "categories.hp");
    public static final KeyBinding luggage = new KeyBinding("key.hp.luggage", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "categories.hp");
    public static final KeyBinding wardrobe = new KeyBinding("key.hp.wardrobe", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "categories.hp");
    public static final KeyBinding profile = new KeyBinding("key.hp.profile", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "categories.hp");
    public static final KeyBinding friends = new KeyBinding("key.hp.friends", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "categories.hp");
    public static final KeyBinding journal = new KeyBinding("key.hp.journal", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "categories.hp");
    public static final KeyBinding palmPlate = new KeyBinding("key.hp.palm_plate", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "categories.hp");
    public static final KeyBinding mail = new KeyBinding("key.hp.mail", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "categories.hp");

    // public static final KeyBinding explore = new KeyBinding("key.hp.explore", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "categories.hp");
    // public static final KeyBinding debug = new KeyBinding("categories.hplus.debug", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10,"categories.hplus");
    public KeyboardManager() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (jukebox.wasPressed()) client.setScreen(new JukeboxScreen(null));
            while (luggage.wasPressed()) InventorySlotsUI.clickSlot(1, client);
            while (wardrobe.wasPressed()) InventorySlotsUI.clickSlot(2, client);
            while (profile.wasPressed()) InventorySlotsUI.clickSlot(3, client);
            while (friends.wasPressed()) InventorySlotsUI.clickSlot(4, client);
            while (journal.wasPressed()) InventorySlotsUI.clickSlot(43, client);
            while (palmPlate.wasPressed()) InventorySlotsUI.clickSlot(44, client);
            while (mail.wasPressed() && client.player != null) {
                client.player.networkHandler.sendChatCommand("mail");
            }
            while (autoSell.wasPressed()) {
                if (!UkuwayConfig.get().isAutoSell() && MinecraftClient.getInstance().currentScreen != null) {
                    HideawayPlus.shop().tick();
                }
            }
        });

        KeyBindingHelper.registerKeyBinding(jukebox);
        KeyBindingHelper.registerKeyBinding(autoSell);
        KeyBindingHelper.registerKeyBinding(luggage);
        KeyBindingHelper.registerKeyBinding(wardrobe);
        KeyBindingHelper.registerKeyBinding(profile);
        KeyBindingHelper.registerKeyBinding(friends);
        KeyBindingHelper.registerKeyBinding(journal);
        KeyBindingHelper.registerKeyBinding(palmPlate);
        KeyBindingHelper.registerKeyBinding(mail);
    }
}
