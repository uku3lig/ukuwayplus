package net.uku3lig.ukuway.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Uuids;
import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.util.StaticValues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FriendsListUI {
    private static GenericContainerScreenHandler oldHandler = null;
    private static int ticker = 0;
    private static boolean calledProper = false;

    public static void tick() {
        if (MinecraftClient.getInstance().currentScreen != null && MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen containerScreen
                && containerScreen.getScreenHandler().getStacks().stream().map(ItemStack::getItem).filter(Items.PLAYER_HEAD::equals).count() > StaticValues.friendsUsernames.size() - 1)
            StaticValues.friendsCheck = false;

        if (!StaticValues.friendsUUID.contains(MinecraftClient.getInstance().player.getUuidAsString()))
            StaticValues.friendsUUID.add(MinecraftClient.getInstance().player.getUuidAsString());
        if (!StaticValues.friendsUsernames.contains(MinecraftClient.getInstance().player.getName().getString()))
            StaticValues.friendsUsernames.add(MinecraftClient.getInstance().player.getName().getString());
        if (StaticValues.friendsCheck) return;

        if (MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen containerScreen) {
            GenericContainerScreenHandler handler = containerScreen.getScreenHandler();
            calledProper = true;
            if (oldHandler != null && oldHandler == handler) return;
            oldHandler = handler;

            List<ItemStack> allItems = new ArrayList<>(handler.getStacks());
            boolean hasMorePages = handler.getStacks().stream().anyMatch(itemStack -> itemStack.getItem() == Items.PAPER && itemStack.getNbt().asString().contains("→"));

            HideawayPlus.lifecycle().addAsync(
                    "friendsCheck",
                    CompletableFuture.runAsync(() -> {
                        System.out.println("Friends list size: " + StaticValues.friendsUsernames.size());
                        List<ItemStack> newAllItems = new ArrayList<>(allItems);
                        for (ItemStack itemStack : newAllItems) {
                            if (itemStack.getItem() == Items.PLAYER_HEAD) {
                                if (itemStack.getNbt().toString().contains("Left click to Accept")) continue;
                                int[] uuidIntArray = itemStack.getNbt().getCompound("SkullOwner").getIntArray("Id");
                                String uuid = Uuids.toUuid(uuidIntArray).toString();
                                if (!StaticValues.friendsUUID.contains(uuid)) StaticValues.friendsUUID.add(uuid);
                                String name = itemStack.getNbt().getCompound("SkullOwner").getString("Name");
                                if (!StaticValues.friendsUsernames.contains(name))
                                    StaticValues.friendsUsernames.add(name);
                            }
                        }
                    })
            );

            if (!hasMorePages) {
                StaticValues.friendsCheck = true;
                MinecraftClient.getInstance().setScreen(null);
            } else {
                Slot paperSlot = handler.slots.stream().filter(slot -> slot.getStack().getItem() == Items.PAPER && slot.getStack().getNbt().asString().contains("→")).findFirst().orElse(null);
                containerScreen.onMouseClick(paperSlot, 0, 0, SlotActionType.PICKUP);
            }
        } else {
            if (ticker >= 25 && !calledProper) {
                StaticValues.friendsCheck = false;
                MinecraftClient.getInstance().player.networkHandler.sendChatCommand("friend");
            } else {
                ticker++;
                calledProper = false;
            }

        }
    }
}
