package net.uku3lig.ukuway.keyboard;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.ui.JukeboxScreen;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class KeyboardManager {
    public static final KeyBinding autoSell = new KeyBinding("key.hp.autoSell", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_S, "categories.hp");

    private static final List<KeyBindingWrapper> binds = List.of(
            create("key.hp.jukebox", GLFW.GLFW_KEY_J, () -> MinecraftClient.getInstance().setScreen(new JukeboxScreen(null))),
            new KeyBindingWrapper(autoSell, () -> {
                if (!UkuwayConfig.get().isAutoSell() && MinecraftClient.getInstance().currentScreen != null) {
                    UkuwayPlus.getShop().tick();
                }
            }),
            create("key.hp.luggage", GLFW.GLFW_KEY_B, () -> clickSlot(1, MinecraftClient.getInstance())),
            create("key.hp.wardrobe", GLFW.GLFW_KEY_G, () -> clickSlot(2, MinecraftClient.getInstance())),
            create("key.hp.profile", GLFW.GLFW_KEY_Z, () -> clickSlot(3, MinecraftClient.getInstance())),
            create("key.hp.friends", GLFW.GLFW_KEY_U, () -> clickSlot(4, MinecraftClient.getInstance())),
            create("key.hp.journal", GLFW.GLFW_KEY_Y, () -> clickSlot(43, MinecraftClient.getInstance())),
            create("key.hp.palm_plate", GLFW.GLFW_KEY_V, () -> clickSlot(44, MinecraftClient.getInstance())),
            create("key.hp.mail", GLFW.GLFW_KEY_C, () -> {
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.networkHandler.sendChatCommand("mail");
                }
            })
    );

    public static void register() {
        for (KeyBindingWrapper bind : binds) {
            KeyBindingHelper.registerKeyBinding(bind.keyBinding);
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyBindingWrapper bind : binds) {
                while (bind.keyBinding.wasPressed()) {
                    bind.action.run();
                }
            }
        });
    }
    
    private static void clickSlot(Integer slotNumber, MinecraftClient client) {
        if (client.interactionManager != null && client.player != null) {
            ScreenHandler screenHandler = client.player.currentScreenHandler;
            client.interactionManager.clickSlot(screenHandler.syncId, slotNumber, 0, SlotActionType.PICKUP, client.player);
        }
    }

    public record KeyBindingWrapper(KeyBinding keyBinding, Runnable action) {
    }

    private static KeyBindingWrapper create(String translationKey, int code, Runnable action) {
        return new KeyBindingWrapper(new KeyBinding(translationKey, InputUtil.Type.KEYSYM, code, "categories.hp"), action);
    }

    private KeyboardManager() {
    }
}
