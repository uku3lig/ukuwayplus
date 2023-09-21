package continued.hideaway.mod.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ParseItemName {

    public static String getItemName(ItemStack itemstack) {
        CompoundTag itemCompounds = itemstack.getTag();

        if (itemCompounds != null && itemCompounds.getAsString().contains("PublicBukkitValues")) {
            JsonObject bukkitJO = JsonParser.parseString(itemCompounds.getCompound("PublicBukkitValues").getAsString()).getAsJsonObject();
            for (String key : bukkitJO.keySet()) {
                if (key.contains("pixelhideawaycore:gameplay_item_id")) {
                    return bukkitJO.get(key).getAsString();
                }
            }
        }

        System.out.println(itemCompounds.toString());

        return null;
    }
}
