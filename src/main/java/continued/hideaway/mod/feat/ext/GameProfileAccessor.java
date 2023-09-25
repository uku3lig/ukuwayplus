package continued.hideaway.mod.feat.ext;

import com.mojang.authlib.properties.Property;

import java.util.Collection;

public interface GameProfileAccessor {
    void setSkin(Collection<Property> base64EncodedSkinTexture);
    Collection<Property> getSkinTexture();
}
