package net.uku3lig.ukuway.ui;

import lombok.Setter;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.uku3lig.ukuway.UkuwayPlus;
import net.uku3lig.ukuway.config.UkuwayConfig;
import net.uku3lig.ukuway.util.KeyboardManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private static final Identifier NOT_SAVE_KEY = new Identifier("pixelhideawaycore", "inventory-do-not-save");

    @Setter @NotNull
    private ShopType oldShopType = ShopType.UNKNOWN;

    public void tick() {
        if (MinecraftClient.getInstance().currentScreen == null || !(MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen containerScreen)) {
            oldShopType = ShopType.UNKNOWN;
            return;
        }

        if (getShopType(containerScreen) == ShopType.UNKNOWN) {
            oldShopType = ShopType.UNKNOWN;
            return;
        }

        ShopType shopType = getShopType(containerScreen);
        boolean fill = GLFW.glfwGetKey(GLFW.glfwGetCurrentContext(), KeyBindingHelper.getBoundKeyOf(KeyboardManager.autoSell).getCode()) == GLFW.GLFW_PRESS;

        if ((shopType == ShopType.FRUIT || shopType == ShopType.FISH) && ((UkuwayConfig.get().isAutoSell() && shopType != oldShopType) || fill)) {
            List<Slot> emptyChestSlots = new ArrayList<>();
            List<Slot> playerItemSlots = new ArrayList<>();
            GenericContainerScreenHandler handler = containerScreen.getScreenHandler();

            for (Slot slot : handler.slots) {
                if (slot.inventory instanceof PlayerInventory) {
                    if (!slot.getStack().isEmpty() && !slot.getStack().isOf(Items.FISHING_ROD)) {
                        NbtCompound nbt = slot.getStack().getSubNbt(UkuwayPlus.PUBLIC_BUKKIT_VALUES);
                        if (nbt == null || !nbt.contains(NOT_SAVE_KEY.toString())) {
                            playerItemSlots.add(slot);
                        }
                    }
                } else if (slot.getStack().isEmpty()) {
                    emptyChestSlots.add(slot);
                }
            }

            for (Slot playerSlot : playerItemSlots) {
                if (emptyChestSlots.isEmpty()) break;

                Slot emptySlot = emptyChestSlots.get(0);
                containerScreen.onMouseClick(playerSlot, emptySlot.id, 0, SlotActionType.QUICK_MOVE);

                if (!emptySlot.getStack().isEmpty()) {
                    emptyChestSlots.remove(0);
                }
            }
        }

        oldShopType = shopType;
    }

    @NotNull
    private ShopType getShopType(GenericContainerScreen containerScreen) {
        GenericContainerScreenHandler handler = containerScreen.getScreenHandler();
        String screenName = containerScreen.getTitle().getString();
        if (screenName.contains("\uE00C") || screenName.contains("\uE010")) {
            FriendListManager.tick(); // qhar????????
            return ShopType.UNKNOWN;
        }


        for (ItemStack itemStack : handler.getStacks()) {
            if (itemStack.getNbt() == null) continue;
            String nbtData = itemStack.getNbt().asString();

            if (nbtData.contains("buy")) {
                if (nbtData.contains("The Marmoset Monkey Brothers")) return ShopType.FRUIT;
                if (nbtData.contains("Bill Beaks")) return ShopType.FISH;
            } else if (nbtData.contains("same rarity")) {
                return ShopType.TRADER;
            }
        }

        return ShopType.UNKNOWN;
    }

    public enum ShopType {
        FRUIT,
        FISH,
        TRADER,
        UNKNOWN
    }
}
