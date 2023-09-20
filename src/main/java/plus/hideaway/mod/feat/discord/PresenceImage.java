package plus.hideaway.mod.feat.discord;

public class PresenceImage {
    public enum Large {
        SCENE("scene"),
        SCENE_DARK("scene_dark"),
        PINEAPPLE("pineapple_512"),
        ROUNDEL("roundel_512");

        Large(String key) { this.key = key; }

        private final String key;

        public String key() { return key; }
    }

    public enum Small {
        PINEAPPLE("pineapple_512"),
        ROUNDEL("roundel_512");

        Small(String key) { this.key = key; }

        private String key;
        public String key() { return key; }
    }
}
