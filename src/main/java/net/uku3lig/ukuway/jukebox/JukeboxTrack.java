package net.uku3lig.ukuway.jukebox;


import net.minecraft.util.Identifier;

import java.util.List;

public enum JukeboxTrack {
    ACTIVITY_BOUNCE_BATTLE(
            "Bounce Battle",
            List.of(new Part(new Identifier("hideaway", "activities.bounce_battle_music_loop"), 40 * 20)),
            Category.ACTIVITIES
    ),
    ACTIVITY_KING_OF_THE_CASTLE(
            "King of the Castle",
            List.of(
                    new Part(new Identifier("hideaway", "activities.event_jazzyjingle1"), 19 * 20),
                    new Part(new Identifier("hideaway", "activities.event_jazzyjingle2"), 19 * 20),
                    new Part(new Identifier("hideaway", "activities.event_jazzyjingle3"), 19 * 20)
            ),
            Category.ACTIVITIES
    ),
    ACTIVITY_JETSKI_RACE(
            "Jet Ski Race",
            List.of(new Part(new Identifier("hideaway", "activities.jet_ski_race_music_loop"), (long) (34.5 * 20))),
            Category.ACTIVITIES
    ),
    ACTIVITY_TREASURE_DIVING(
            "Treasure Diving",
            List.of(new Part(new Identifier("hideaway", "activities.treasure_diving_music_loop"), (long) (86.5 * 20))),
            Category.ACTIVITIES
    ),
    ACTIVITY_VOLLEYBALL(
            "Volleyball",
            List.of(new Part(new Identifier("hideaway", "activities.volleyball_music_loop"), (long) (46.5 * 20))),
            Category.ACTIVITIES
    ),

    ARCADE_BIRDSPOTTING(
            "Birdspotting",
            List.of(new Part(new Identifier("hideaway", "arcade.birdspotting_music_loop"), (long) (25.5 * 20))),
            Category.ARCADE
    ),
    ARCADE_BUNCHABUGS(
            "Bunch-A-Bugs",
            List.of(new Part(new Identifier("hideaway", "arcade.bunchabugs_music_loop"), (long) (22 * 20))),
            Category.ARCADE
    ),
    ARCADE_FRUIT_JUICED(
            "Fruit Juiced",
            List.of(new Part(new Identifier("hideaway", "arcade.fruitjuiced_music_loop"), (long) (31.5 * 20))),
            Category.ARCADE
    ),
    ARCADE_SAND_STACKER(
            "Sand Stacker",
            List.of(new Part(new Identifier("hideaway", "arcade.sandstacker_music_loop"), (long) (23.5 * 20))),
            Category.ARCADE
    ),
    ARCADE_SEAFLOOR_URCHINS(
            "The Seafloor is Urchins",
            List.of(new Part(new Identifier("hideaway", "arcade.theseafloorisurchins_music_loop"), (long) (50.5 * 20))),
            Category.ARCADE
    ),
    ARCADE_VOLCANIC_PANIC(
            "Volcanic Panic",
            List.of(new Part(new Identifier("hideaway", "arcade.volcanicpanic_music_loop"), (long) (34.5 * 20))),
            Category.ARCADE
    ),

    WARDROBE(
            "Wardrobe Theme",
            List.of(new Part(new Identifier("hideaway", "ui.wardrobe.song_loop"), (long) (23 * 20))),
            Category.MISC
    ),

//    These tracks use positional audio (somehow) so function incorrectly
//
//    ACTIVITY_BREAKFAST("Breakfast", new Identifier("hideaway", "activities.breakfast_music_loop"), Category.ACTIVITIES),
//    ACTIVITY_BEACH_BONFIRE("Bonfire", new Identifier("hideaway", "activities.beach_bonfire_music_loop"), Category.ACTIVITIES),
//    ACTIVITY_POOL_PARTY("Pool Party", new Identifier("hideaway", "activities.pool_party_music_loop"), Category.ACTIVITIES),
//    TITO_TRADEUP_SHACK("Tito's Trade-up Shack Theme", new Identifier("hideaway", "ambient.tradeup.radio_loop"), Category.MISC),
//    TINTS_N_TEXTURES("Tints'N'Textures Theme", new Identifier("hideaway", "ambient.tints_n_textures.radio_loop"), Category.MISC),
//    UNCLE_GUITAR("Uncle's Song", new Identifier("hideaway", "ambient.uncle.guitar_loop"), Category.MISC),
    ;


    public final String name;
    public final List<Part> parts;
    public final Category category;

    JukeboxTrack(String n, List<Part> p, Category c) {
        this.name = n;
        this.parts = p;
        this.category = c;
    }

    public enum Category {
        ARCADE(), ACTIVITIES(), MISC();
    }

    public static class Part {
        public Identifier id;
        public long length;

        public Part(Identifier i, long l) {
            this.id = i;
            this.length = l;
        }
    }
}
