package net.uku3lig.ukuway.ui;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FriendListManager {
    @Getter
    private static final Set<String> friends = new HashSet<>();

    private static GenericContainerScreenHandler oldHandler = null;
    private static long ticksElapsed = 0;

    @Getter
    private static boolean init = false;

    public static void tick() {
        if (MinecraftClient.getInstance().currentScreen instanceof GenericContainerScreen containerScreen) {
            checkFriends(containerScreen);
        } else if (ticksElapsed >= 50 && !init && MinecraftClient.getInstance().player != null) {
            init = true;
            MinecraftClient.getInstance().player.networkHandler.sendChatCommand("friend");
        }

        ticksElapsed++;
    }

    private static void checkFriends(GenericContainerScreen containerScreen) {
        long count = containerScreen.getScreenHandler().getStacks().stream().map(ItemStack::getItem).filter(Items.PLAYER_HEAD::equals).count();
        if (count == friends.size() || count <= 1) {
            return; // friend list didn't change, no need to check
        }

        init = true;

        GenericContainerScreenHandler handler = containerScreen.getScreenHandler();
        if (oldHandler != null && oldHandler == handler) return;
        oldHandler = handler;

        Optional<Slot> nextPageSlot = handler.slots.stream().filter(slot -> slot.getStack().isOf(Items.PAPER)
                        && slot.getStack().getNbt() != null && slot.getStack().getNbt().asString().contains("→"))
                .findFirst();

        CompletableFuture.runAsync(() -> {
            for (ItemStack itemStack : handler.getStacks()) {
                NbtCompound nbt = itemStack.getNbt();
                if (nbt == null || !itemStack.isOf(Items.PLAYER_HEAD) || nbt.toString().contains("Left click to Accept")) {
                    continue;
                }

                NbtCompound skullOwner = nbt.getCompound("SkullOwner");
                String name = skullOwner.getString("Name").toLowerCase(Locale.ROOT);
                if (!name.isBlank()) friends.add(name);
            }
        });

        nextPageSlot.ifPresentOrElse(slot -> containerScreen.onMouseClick(slot, 0, 0, SlotActionType.PICKUP),
                () -> MinecraftClient.getInstance().setScreen(null));
    }

    private FriendListManager() {
    }
}
