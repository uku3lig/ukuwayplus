package continued.hideaway.mod.feat.shop;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.feat.lifecycle.Task;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    public void tick() {
        if (getShopName() == null) return;
        String shopName = getShopName();

        switch (shopName) {
            case "fruit" -> {
                for (ItemStack itemStack : HideawayContinued.player().getInventory().items) {
                    if (!itemStack.hasTag()) continue;
                    String nbtData = itemStack.getTag().getAsString();

                    List<Slot> emptyChestSlots = ((AbstractContainerScreen<ChestMenu>) HideawayContinued.client().screen).getMenu().slots;
                    emptyChestSlots.removeIf(slot -> slot.getItem().getItem() != Items.AIR);
                }
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
