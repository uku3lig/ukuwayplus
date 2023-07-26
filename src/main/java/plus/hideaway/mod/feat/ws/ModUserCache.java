package plus.hideaway.mod.feat.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModUserCache {
    private static final List<String> users = new ArrayList<>();
    public static void addUser(String username) { users.add(username); }
    public static void clear() {
        users.clear();
    }
    public static boolean containsUser(String username) {
        return users.contains(username);
    }
}
