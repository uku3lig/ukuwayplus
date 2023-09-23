package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.time.Instant;
import java.util.BitSet;

public class FriendsListUI {
    private static boolean finishedChecking = false;

    public static void tick() {
        if (!StaticValues.friendsList.contains(HideawayPlus.client().player.getName().getString())) StaticValues.friendsList.add(HideawayPlus.client().player.getName().getString());

        if (HideawayPlus.client().screen instanceof AbstractContainerScreen screen) {
            if (!(screen.getMenu() instanceof ChestMenu menu)) return;
            for (ItemStack itemStack : menu.getItems()) {
                if (itemStack.getItem() == Items.PLAYER_HEAD) {
                    if (StaticValues.friendsList.size() <= 1) {finishedChecking = false;StaticValues.friendsCheck = false;}

                    if (itemStack.getTag().toString().contains("Left click to Accept")) continue;
                    String name = itemStack.getTag().getCompound("SkullOwner").getString("Name");
                    if (!StaticValues.friendsList.contains(name)) StaticValues.friendsList.add(name);
                    if (!finishedChecking) {
                        HideawayPlus.client().setScreen(null);
                        finishedChecking = true;
                    }
                } else finishedChecking = true;
            }

            while (menu.getItems().stream().anyMatch(itemStack -> itemStack.getItem() == Items.PAPER && itemStack.getTag().getAsString().contains("→"))) {
                for (ItemStack itemStack : menu.getItems()) {
                    if (itemStack.getItem() == Items.PLAYER_HEAD) {
                        if (itemStack.getTag().toString().contains("Left click to Accept")) continue;
                        String name = itemStack.getTag().getCompound("SkullOwner").getString("Name");
                        if (!StaticValues.friendsList.contains(name)) StaticValues.friendsList.add(name);
                        if (!finishedChecking) {
                            HideawayPlus.client().setScreen((Screen) null);
                            finishedChecking = true;
                        }
                    }
                }
            }

        } else {
            StaticValues.friendsCheck = true;
            finishedChecking = false;

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
