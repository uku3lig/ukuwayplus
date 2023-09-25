package continued.hideaway.mod.mixins;

import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import continued.hideaway.mod.feat.ext.GameProfileAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

@Mixin(GameProfile.class)
public class GameProfileMixin implements GameProfileAccessor {

    @Shadow @Final private PropertyMap properties;

    @Override
    public void setSkin(Collection<Property> base64EncodedSkinTexture) {
        System.out.println("Setting skin");
        System.out.println(base64EncodedSkinTexture.size());
        PropertyMap propertyMap = new PropertyMap();
        for (Property property : base64EncodedSkinTexture) {
            propertyMap.put(property.getName(), property);
        }

        this.properties.clear();
        this.properties.putAll(propertyMap);
    }
    @Override
    public Collection<Property> getSkinTexture() {
        Collection<Property> base64EncodedSkinTexture = properties.get("textures");

        for (Property property : base64EncodedSkinTexture) {
            System.out.println(property.getValue());
        }
        return base64EncodedSkinTexture;
    }
}

