package continued.hideaway.mod.feat.ui;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public class InventorySlotsUI {
    public static void clickSlot(Integer slotNumber, Minecraft client) {
        assert client.player != null && client.getConnection() != null;

        AbstractContainerMenu abstractContainerMenu = client.player.containerMenu;
        client.getConnection().send(
                new ServerboundContainerClickPacket(
                        abstractContainerMenu.containerId,
                        abstractContainerMenu.getStateId(),
                        slotNumber,
                        0,
                        ClickType.PICKUP,
                        client.player.getInventory().getItem(slotNumber),
                        new Int2ObjectOpenHashMap<ItemStack>()
                )
        );
    }
}
