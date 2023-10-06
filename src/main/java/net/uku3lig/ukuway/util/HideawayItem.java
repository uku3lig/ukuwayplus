package net.uku3lig.ukuway.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.Arrays;

public record HideawayItem(ItemStack parent, Rarity rarity, ItemType type, boolean signed, boolean recolor, boolean special, boolean notSave) {
    public static HideawayItem fromStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return null;
        String nbtString = nbt.asString();

        Rarity rarity = Arrays.stream(Rarity.values()).filter(r -> nbtString.contains(r.getCharacter())).findFirst().orElse(null);
        ItemType type = Arrays.stream(ItemType.values()).filter(t -> nbtString.contains(t.getCharacter())).findFirst().orElse(null);
        boolean signed = nbtString.contains(SpecialChars.SIGNED.getCharacter());
        boolean recolor = nbtString.contains(SpecialChars.RECOLOR.getCharacter());
        boolean special = nbtString.contains(SpecialChars.SPECIAL.getCharacter());
        boolean notSave = nbtString.contains("inventory-do-not-save");

        return new HideawayItem(stack, rarity, type, signed, recolor, special, notSave);
    }
}
