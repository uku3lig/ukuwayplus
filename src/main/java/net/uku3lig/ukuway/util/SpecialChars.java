package net.uku3lig.ukuway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpecialChars {
    RECOLOR(""),
    SIGNED("\uE60D"),
    SPECIAL("\uE2AE"),
    ;

    private final String character;
}
