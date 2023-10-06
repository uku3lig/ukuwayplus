package net.uku3lig.ukuway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemType {
    ITEM("\uE2A9"),
    TRINKET("\uE2A8"),
    HAT("\uE2AA"),
    BADGE("\uE2AC"),
    SHELL("\uE2B0"),
    FURNITURE("\uE2AB"),
    ;

    private final String character;
}
