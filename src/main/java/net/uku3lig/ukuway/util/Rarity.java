package net.uku3lig.ukuway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rarity {
    COMMON("\uE2A0", 0x957C85),
    UNCOMMON("\uE2A1", 0x3FC44F),
    RARE("\uE2A2", 0x1D7BD2),
    EXOTIC("\uE2A3", 0x904BD3),
    MYTHICAL("\uE2A4", 0xDD3016),
    LIMITED("\uE2A5", 0xF2D24E),
    ;

    private final String character;
    private final int color;

    public int withAlpha() {
        return color | 0xFF000000;
    }
}
