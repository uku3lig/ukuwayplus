package continued.hideaway.mod.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import continued.hideaway.mod.feat.ext.ItemStackAccessor;
import io.wispforest.owo.nbt.NbtKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ParseItemName {

    public static String getItemName(ItemStack itemstack) {
        CompoundTag itemCompounds = ((ItemStackAccessor) itemstack).getCompoundTag();

        if (itemCompounds != null && itemCompounds.contains("PublicBukkitValues")) {
            JsonObject bukkitJO = JsonParser.parseString(itemCompounds.getCompound("PublicBukkitValues").getAsString()).getAsJsonObject();
            for (String key : bukkitJO.keySet()) {
                if (key.contains("pixelhideawaycore:gameplay_item_id")) {
                    return bukkitJO.get(key).getAsString();
                }
            }
        }

        return null;
    }
}
