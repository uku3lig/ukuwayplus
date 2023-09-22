package continued.hideaway.mod.feat.ui;

import continued.hideaway.mod.HideawayContinued;
import continued.hideaway.mod.feat.ext.AbstractContainerScreenAccessor;
import continued.hideaway.mod.feat.lifecycle.Task;
import continued.hideaway.mod.util.StaticValues;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlayerHeadItem;

import java.time.Instant;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class FriendsListUI {
    private static boolean finishedChecking = false;

    public static void tick() {
        String playerName = HideawayContinued.client().player.getName().getString();
        if (!StaticValues.friendsList.contains(playerName)) StaticValues.friendsList.add(playerName);

        if (HideawayContinued.client().screen instanceof AbstractContainerScreen screen) {
            if (!(screen.getMenu() instanceof ChestMenu)) return;
            ChestMenu menu = (ChestMenu) screen.getMenu();
            for (ItemStack itemStack : menu.getItems()) {
                if (itemStack.getItem() == Items.PLAYER_HEAD) {
                    if (itemStack.getTag().toString().contains("Left click to Accept")) continue;
                    String name = itemStack.getTag().getCompound("SkullOwner").getString("Name");
                    if (!StaticValues.friendsList.contains(name)) StaticValues.friendsList.add(name);
                    if (!finishedChecking) {
                        HideawayContinued.client().setScreen((Screen) null);
                        finishedChecking = true;
                    }
                }
            }

        } else {
            StaticValues.friendsCheck = true;
            finishedChecking = false;

            LastSeenMessages.Update messages = new LastSeenMessages.Update(0, new BitSet());

            Instant now = Instant.now();
            HideawayContinued.client().player.connection.send(
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
