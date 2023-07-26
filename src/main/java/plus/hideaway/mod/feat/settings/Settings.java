package plus.hideaway.mod.feat.settings;

import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import org.json.JSONObject;
import plus.hideaway.mod.HideawayPlus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class Settings {

    public boolean rpc = true;
    public boolean ws = true;
    public boolean stats = true;

    public static void write() {
        var path = FabricLoader.getInstance().getConfigDir().resolve("hideawayplus.json");
        var file = new File(path.toUri());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                HideawayPlus.logger().error("Unable to create config file: " + e.getMessage());
                e.printStackTrace();
            }

            var settings = HideawayPlus.settings();
            var obj = new JSONObject();
            obj.put("rpc", settings.rpc);
            obj.put("ws", settings.ws);
            obj.put("stats", settings.stats);

            try {
                var writer = new FileWriter(file);
                writer.write(obj.toString());
                writer.close();
            } catch (IOException e) {
                HideawayPlus.logger().error("Unable to create config file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void read() {
        var path = FabricLoader.getInstance().getConfigDir().resolve("hideawayplus.json");
        var file = new File(path.toUri());
        if (file.exists()) {
            try {
                var content = Files.readString(path);
                var obj = JsonParser.parseString(content).getAsJsonObject();
                HideawayPlus.settings().rpc = obj.get("rpc").getAsBoolean();
                HideawayPlus.settings().ws = obj.get("ws").getAsBoolean();
                HideawayPlus.settings().stats = obj.get("stats").getAsBoolean();
            } catch (IOException e) {
                HideawayPlus.logger().error("Unable to create config file: " + e.getMessage());
                e.printStackTrace();
            }
        } else HideawayPlus.logger().error("Unable to read config file, does not exist");
    }

}
