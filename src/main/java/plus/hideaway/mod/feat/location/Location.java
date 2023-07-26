package plus.hideaway.mod.feat.location;

import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.discord.PresenceImage;

public enum Location {
    // Locations
    ISLAND_EMPORIUM(
        "At the Island Emporium",
        "TBC",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    FURNITURE_FUNCTIONATOR(
        "At the Furniture Functionator",
        "TBC",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    TINTS_N_TEXTURES(
        "At Tints'N'Textures",
        "Feeling FABULOUS! 🎨",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
            new Vec3d(0, 0, 0),
            new Vec3d(0, 0, 0)
    ),
    WARDROBE_WHEEL(
        "At the Wardrobe Wonder Wheel",
        "Chilling with the Wonder Squid 🍹",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),

    // Activities
    HOTEL_ROOM(
        "In their Hotel Room",
        "Look at that view! 🏝️",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    FISHING(
        "Fishing on Hideaway Island",
        "... *plop* 🐟",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    WARDROBE(
        "In the Wardrobe",
        "Don't look! 👚",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    SCUBA(
        "Scuba diving",
        "It's quite wet down here 🐠",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),

    // Minigames
    BOUNCE_BATTLE(
        "Playing Bounce Battle",
        "... *boing*",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    JETSKI(
        "In a jetski race",
        "... *nyoom*",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    KING_OF_CASTLE(
            "Playing King of the Castle",
            "... *royal noises*",
            PresenceImage.Large.SCENE,
            PresenceImage.Small.ROUNDEL,
            new Vec3d(0, 0, 0),
            new Vec3d(0, 0, 0)
    ),
    TREASURE_DIVING(
            "Diving for treasure",
            "Oooo shiney 💎",
            PresenceImage.Large.SCENE,
            PresenceImage.Small.ROUNDEL,
            new Vec3d(0, 0, 0),
            new Vec3d(0, 0, 0)
    ),
    VOLLEYBALL(
            "Playing Beach Volleyball",
            "Your serve!",
            PresenceImage.Large.SCENE,
            PresenceImage.Small.ROUNDEL,
            new Vec3d(0, 0, 0),
            new Vec3d(0, 0, 0)
    ),

    // Miscellaneous
    GENERIC(
        "On Hideaway Island",
        "Relaxing in the sun ☀️",
        PresenceImage.Large.SCENE,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    SECRET(
        "You saw nothing...",
        "This is all just a dream... 😵‍💫",
        PresenceImage.Large.SCENE_DARK,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    UNKNOWN(
        "Using Hideaway+",
        "Somewhere in the metaverse... 🚀",
        PresenceImage.Large.SCENE_DARK,
        PresenceImage.Small.ROUNDEL,
        new Vec3d(0, 0, 0),
        new Vec3d(0, 0, 0)
    ),
    ;

    public final String name;
    public final String description;
    public final PresenceImage.Large largeIcon;
    public final PresenceImage.Small smallIcon;
    public final Vec3d cornerA;
    public final Vec3d cornerB;

    Location(
        String name,
        String description,
        PresenceImage.Large largeIcon,
        PresenceImage.Small smallIcon,
        Vec3d cornerA,
        Vec3d cornerB
    ) {
        this.name = name;
        this.description = description;
        this.largeIcon = largeIcon;
        this.smallIcon = smallIcon;
        this.cornerA = cornerA;
        this.cornerB = cornerB;
    }

    public static void check() {
        if (!HideawayPlus.connected()) HideawayPlus.setLocation(UNKNOWN);

        // Location-based
        else if (playerIsInRadius(new Vec3d(13.5f, 7f, -115f), 5)) {
            HideawayPlus.setLocation(WARDROBE_WHEEL);
        }

        // Sidebar/Bossbar-based
        else if (HideawayPlus.player().getServer().getScoreboard() != null) {
            if (scoreboardContains("\ue4d5")) {
                HideawayPlus.setLocation(KING_OF_CASTLE);
            }
            else if (scoreboardContains("\ue4db")) {
                HideawayPlus.setLocation(BOUNCE_BATTLE);
            }
            else if (scoreboardContains("\ue4dc")) {
                HideawayPlus.setLocation(BOUNCE_BATTLE);
            }
            else if (scoreboardContains("\ue523")) {
                HideawayPlus.setLocation(JETSKI);
            }
            else if (scoreboardContains("\ue524")) {
                HideawayPlus.setLocation(BOUNCE_BATTLE);
            }
            else HideawayPlus.setLocation(GENERIC);
        }

        else if (HideawayPlus.player().isSpectator()) HideawayPlus.setLocation(WARDROBE);
        else HideawayPlus.setLocation(GENERIC);
    }

    private static boolean playerIsInRadius(Vec3d loc, int radius) {
        Vec3d actualLoc = HideawayPlus.player().getPos();
        return actualLoc.isInRange(loc, radius);
    }

    private static boolean scoreboardContains(String content) {
        ServerScoreboard board = HideawayPlus.player().getServer().getScoreboard();
        Text name = board.getObjectives().stream().toList().get(0).getDisplayName();
        return name.getString().contains(content);
    }
}
