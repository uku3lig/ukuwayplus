package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.feat.ext.AbstractContainerScreenAccessor;
import continued.hideaway.mod.feat.lifecycle.Lifecycle;
import continued.hideaway.mod.feat.lifecycle.Task;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FriendsListUI {
    private static boolean finishedChecking = false;

    public static void tick() {
        if (!StaticValues.friendsList.contains(HideawayPlus.client().player.getName().getString())) StaticValues.friendsList.add(HideawayPlus.client().player.getName().getString());

        if (HideawayPlus.client().screen instanceof AbstractContainerScreen screen) {
            if (!(screen.getMenu() instanceof ChestMenu menu)) return;
            if (StaticValues.friendsList.size() > 1 && (!finishedChecking || StaticValues.friendsCheck)) return;

            List<ItemStack> allItems = new ArrayList<>(menu.getItems());
            while (menu.getItems().stream().anyMatch(itemStack -> itemStack.getItem() == Items.PAPER && itemStack.getTag().getAsString().contains("→"))) {
                Slot paperSlot = menu.slots.stream().filter(slot -> slot.getItem().getItem() == Items.PAPER && slot.getItem().getTag().getAsString().contains("→")).findFirst().orElse(null);
                ((AbstractContainerScreenAccessor)menu).hp$slotChange(paperSlot, paperSlot.index - 1, 0, ClickType.QUICK_MOVE);
                allItems.addAll(menu.getItems());
            }

            HideawayPlus.lifecycle().addAsync(
                    "friendsCheck",
                    CompletableFuture.runAsync(() -> {
                        finishedChecking = false;
                        List<ItemStack> newAllItems = new ArrayList<>(allItems);
                        for (ItemStack itemStack : newAllItems) {
                            if (itemStack.getItem() == Items.PLAYER_HEAD) {
                                if (itemStack.getTag().toString().contains("Left click to Accept")) continue;
                                String name = itemStack.getTag().getCompound("SkullOwner").getString("Name");
                                if (!StaticValues.friendsList.contains(name)) StaticValues.friendsList.add(name);
                            }
                        }
                        finishedChecking = true;
                        StaticValues.friendsCheck = true;
                    })
            );

            HideawayPlus.client().setScreen(null);

        } else {
            StaticValues.friendsCheck = true;

            LastSeenMessages.Update messages = new LastSeenMessages.Update(0, new BitSet());

            Instant now = Instant.now();
            HideawayPlus.client().player.connection.send(
                    new ServerboundChatCommandPacket(
                            "friend",
                            now,
                            0L,
                            ArgumentSignatures.EMPTY,
                            messages)
            );
        }
    }
}
