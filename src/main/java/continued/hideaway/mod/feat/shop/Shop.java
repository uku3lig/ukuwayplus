package continued.hideaway.mod.feat.shop;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.StaticValues;
import continued.hideaway.mod.feat.ext.AbstractContainerScreenAccessor;
import continued.hideaway.mod.feat.lifecycle.Task;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shop {
    public void tick() {
        if (getShopName() == null) return;
        String shopName = getShopName();

        if ("fruit".equals(shopName) || "fish".equals(shopName)) {
            List<Slot> emptyChestSlots = new ArrayList<>();
            List<Slot> playerEmptySlots = new ArrayList<>();
            AbstractContainerScreen<ChestMenu> menu = (AbstractContainerScreen<ChestMenu>) HideawayContinued.client().screen;
            ChestMenu chestMenu = menu.getMenu();

            for (Slot slot : chestMenu.slots) {
                if (slot.getItem().getItem() != Items.AIR) {
                    if (slot.container instanceof Inventory) {
                        playerEmptySlots.add(slot);
                    }
                } else if (!(slot.container instanceof Inventory)) {
                    emptyChestSlots.add(slot);
                }
            }

            for (int i = StaticValues.shopIterationNum; i < playerEmptySlots.size() && !emptyChestSlots.isEmpty() && !StaticValues.shopScreenWasFilled; i++) {
                Slot playerSlot = playerEmptySlots.get(i);

                ((AbstractContainerScreenAccessor)menu).hp$slotChange(playerSlot, emptyChestSlots.get(0).index, 0, ClickType.QUICK_MOVE);

                Iterator<Slot> chestSlotIterator = emptyChestSlots.iterator();
                while (chestSlotIterator.hasNext()) {
                    Slot chestSlot = chestSlotIterator.next();
                    if (chestSlot.getItem().getItem() != Items.AIR) {
                        chestSlotIterator.remove();
                        break;
                    }
                }

                if (emptyChestSlots.isEmpty()) StaticValues.shopIterationNum++;
                if (i == playerEmptySlots.size() -1) StaticValues.shopScreenWasFilled = true;
                if (StaticValues.shopIterationNum >= playerEmptySlots.size()) StaticValues.shopScreenWasFilled = true;
            }
        }
    }

    private String getShopName() {
        ChestMenu screen = ((AbstractContainerScreen<ChestMenu>) HideawayContinued.client().screen).getMenu();

        for (ItemStack itemStack : screen.getItems()) {
            if (!itemStack.hasTag()) continue;
            String nbtData = itemStack.getTag().getAsString();

            if (nbtData.contains("buy")) {
                if (nbtData.contains("The Marmoset Monkey Brothers")) return "fruit";
                if (nbtData.contains("Bill Beaks")) return "fish";
            } else if (nbtData.contains("same rarity")) { return "trader"; }
        }

        return null;
    }
}
