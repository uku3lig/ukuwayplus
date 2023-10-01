package net.uku3lig.ukuway.ui;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class InventorySlotsUI {
    public static void clickSlot(Integer slotNumber, MinecraftClient client) {
        if (client.player != null && client.getNetworkHandler() != null) {
            ScreenHandler screenHandler = client.player.currentScreenHandler;

            // The idea here was to close the menu (if open) so that you can quickly switch between menus
            // without pushing esc/E and then pushing a hotkey again -- however, the keyboardmanager doesn't register
            // our hotkeys while in a menu (<-- skill issue bitch)
//            client.getConnection().send(
//                    new ServerboundContainerClosePacket(
//                            screenHandler.containerId
//                    )
//            );
            client.getNetworkHandler().sendPacket(
                    new ClickSlotC2SPacket(
                            screenHandler.syncId,
                            screenHandler.getRevision(),
                            slotNumber,
                            0,
                            SlotActionType.PICKUP,
                            client.player.getInventory().getStack(slotNumber),
                            new Int2ObjectOpenHashMap<>()
                    )
            );
        }
    }
}
