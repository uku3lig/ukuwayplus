package net.uku3lig.ukuway.shop;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.keyboard.KeyboardManager;
import net.uku3lig.ukuway.ui.FriendsListUI;
import net.uku3lig.ukuway.util.StaticValues;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shop {

    private static boolean fill;
    public String oldShopName = null;

    public void tick() {
        if (MinecraftClient.getInstance().currentScreen == null || !(MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen containerScreen))
            return;

        if (getShopName(containerScreen) == null) {
            oldShopName = null;
            return;
        }

        String shopName = getShopName(containerScreen);

        if (GLFW.glfwGetKey(GLFW.glfwGetCurrentContext(), KeyBindingHelper.getBoundKeyOf(KeyboardManager.autoSell).getCode()) == GLFW.GLFW_PRESS) {
            fill = true;
        }

        if (("fruit".equals(shopName) || "fish".equals(shopName)) && (UkuwayConfig.get().isAutoSell() || fill)) {
            if (oldShopName != null && !oldShopName.equals(shopName)) StaticValues.shopIterationNum = 0;
            oldShopName = shopName;
            List<Slot> emptyChestSlots = new ArrayList<>();
            List<Slot> playerEmptySlots = new ArrayList<>();
            GenericContainerScreenHandler chestMenu = containerScreen.getScreenHandler();

            for (Slot slot : chestMenu.slots) {
                if (slot.getStack().getItem() != Items.AIR) {
                    if (slot.inventory instanceof PlayerInventory) {
                        playerEmptySlots.add(slot);
                    }
                } else if (!(slot.inventory instanceof PlayerInventory)) {
                    emptyChestSlots.add(slot);
                }
            }

            for (int i = StaticValues.shopIterationNum; i < playerEmptySlots.size() && !emptyChestSlots.isEmpty() && !StaticValues.shopScreenWasFilled; i++) {
                Slot playerSlot = playerEmptySlots.get(i);

                containerScreen.onMouseClick(playerSlot, emptyChestSlots.get(0).id, 0, SlotActionType.QUICK_MOVE);

                Iterator<Slot> chestSlotIterator = emptyChestSlots.iterator();
                while (chestSlotIterator.hasNext()) {
                    Slot chestSlot = chestSlotIterator.next();
                    if (chestSlot.getStack().getItem() != Items.AIR) {
                        chestSlotIterator.remove();
                        break;
                    }
                }

                if (emptyChestSlots.isEmpty()) StaticValues.shopIterationNum++;
                if (i == playerEmptySlots.size() - 1) {
                    StaticValues.shopScreenWasFilled = true;
                    fill = false;
                }
                if (StaticValues.shopIterationNum >= playerEmptySlots.size()) {
                    StaticValues.shopScreenWasFilled = true;
                    fill = false;
                }
            }
        }
    }

    private String getShopName(GenericContainerScreen containerScreen) {
        GenericContainerScreenHandler handler = containerScreen.getScreenHandler();
        String screenName = containerScreen.getTitle().getString();
        if (screenName.contains("\uE00C") || screenName.contains("\uE010")) {
            FriendsListUI.tick();
            return null;
        }


        for (ItemStack itemStack : handler.getStacks()) {
            if (!itemStack.hasNbt()) continue;
            String nbtData = itemStack.getNbt().asString();

            if (nbtData.contains("buy")) {
                if (nbtData.contains("The Marmoset Monkey Brothers")) return "fruit";
                if (nbtData.contains("Bill Beaks")) return "fish";
            } else if (nbtData.contains("same rarity")) {
                return "trader";
            }
        }

        return null;
    }
}
