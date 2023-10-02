package net.uku3lig.ukuway.jukebox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.Identifier;

import java.util.List;

@AllArgsConstructor
@Getter
public enum JukeboxTrack {
    ACTIVITY_BOUNCE_BATTLE(
            "Bounce Battle",
            List.of(createPart("activities.bounce_battle_music_loop", 40)),
            Category.ACTIVITIES
    ),
    ACTIVITY_KING_OF_THE_CASTLE(
            "King of the Castle",
            List.of(
                    createPart("activities.event_jazzyjingle1", 19),
                    createPart("activities.event_jazzyjingle2", 19),
                    createPart("activities.event_jazzyjingle3", 19)
            ),
            Category.ACTIVITIES
    ),
    ACTIVITY_JETSKI_RACE(
            "Jet Ski Race",
            List.of(createPart("activities.jet_ski_race_music_loop", 34.5)),
            Category.ACTIVITIES
    ),
    ACTIVITY_TREASURE_DIVING(
            "Treasure Diving",
            List.of(createPart("activities.treasure_diving_music_loop", 86.5)),
            Category.ACTIVITIES
    ),
    ACTIVITY_VOLLEYBALL(
            "Volleyball",
            List.of(createPart("activities.volleyball_music_loop", 46.5)),
            Category.ACTIVITIES
    ),

    ARCADE_BIRDSPOTTING(
            "Birdspotting",
            List.of(createPart("arcade.birdspotting_music_loop", 25.5)),
            Category.ARCADE
    ),
    ARCADE_BUNCHABUGS(
            "Bunch-A-Bugs",
            List.of(createPart("arcade.bunchabugs_music_loop", 22)),
            Category.ARCADE
    ),
    ARCADE_FRUIT_JUICED(
            "Fruit Juiced",
            List.of(createPart("arcade.fruitjuiced_music_loop", 31.5)),
            Category.ARCADE
    ),
    ARCADE_SAND_STACKER(
            "Sand Stacker",
            List.of(createPart("arcade.sandstacker_music_loop", 23.5)),
            Category.ARCADE
    ),
    ARCADE_SEAFLOOR_URCHINS(
            "The Seafloor is Urchins",
            List.of(createPart("arcade.theseafloorisurchins_music_loop", 50.5)),
            Category.ARCADE
    ),
    ARCADE_VOLCANIC_PANIC(
            "Volcanic Panic",
            List.of(createPart("arcade.volcanicpanic_music_loop", 34.5)),
            Category.ARCADE
    ),

    WARDROBE(
            "Wardrobe Theme",
            List.of(createPart("ui.wardrobe.song_loop", 23)),
            Category.MISC
    ),

//    fixme These tracks use positional audio (somehow) so function incorrectly
//
//    ACTIVITY_BREAKFAST("Breakfast", new Identifier("hideaway", "activities.breakfast_music_loop"), Category.ACTIVITIES),
//    ACTIVITY_BEACH_BONFIRE("Bonfire", new Identifier("hideaway", "activities.beach_bonfire_music_loop"), Category.ACTIVITIES),
//    ACTIVITY_POOL_PARTY("Pool Party", new Identifier("hideaway", "activities.pool_party_music_loop"), Category.ACTIVITIES),
//    TITO_TRADEUP_SHACK("Tito's Trade-up Shack Theme", new Identifier("hideaway", "ambient.tradeup.radio_loop"), Category.MISC),
//    TINTS_N_TEXTURES("Tints'N'Textures Theme", new Identifier("hideaway", "ambient.tints_n_textures.radio_loop"), Category.MISC),
//    UNCLE_GUITAR("Uncle's Song", new Identifier("hideaway", "ambient.uncle.guitar_loop"), Category.MISC),
    ;


    private final String trackName;
    private final List<Part> parts;
    private final Category category;

    private static Part createPart(String path, double seconds) {
        return new Part(new Identifier("hideaway", path), (long) (seconds * 20));
    }

    public enum Category {
        ARCADE, ACTIVITIES, MISC
    }

    public record Part(Identifier id, long length) {
    }
}
