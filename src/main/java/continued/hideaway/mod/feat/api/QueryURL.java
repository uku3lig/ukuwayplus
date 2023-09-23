package continued.hideaway.mod.feat.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import continued.hideaway.mod.HideawayPlus;
import continued.hideaway.mod.util.Constants;
import continued.hideaway.mod.util.StaticValues;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class QueryURL {
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER =
            new PoolingHttpClientConnectionManager();
    private static final CloseableHttpClient HTTP_CLIENT =
            HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();
    private static final URL API_URL;

    static {
        try {
            API_URL = new URL("http://localhost:3000/api/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void asyncLifePing(String playerUUID, String apiCode) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpGet request = new HttpGet(API_URL + "live/" + playerUUID + "/" + apiCode);
                request.addHeader(Constants.MOD_NAME + " v" + Constants.VERSION, HideawayPlus.client().player.getName().getString());
                try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String jsonContent = EntityUtils.toString(response.getEntity());
                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        if (jsonObject.has("success")) {
                        }
                    } else if (response.getStatusLine().getStatusCode() == 400) {
                        String jsonContent = EntityUtils.toString(response.getEntity());
                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        if (jsonObject.has("error")) {
                            switch (jsonObject.get("message").getAsString()) {
                                case "User not alive" -> {
                                    API.living = false;
                                }
                                case "Invalid UUID or code" -> {
                                    HideawayPlus.logger().error("API Error: " + jsonObject.get("message").getAsString());
                                    API.living = false;
                                }
                            }
                        }
                    }
                }
            } catch (IOException | ParseException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public static void asyncCreateUser(String playerUUID) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpGet request = new HttpGet(API_URL + "create/" + playerUUID);
                request.addHeader(Constants.MOD_NAME + " v" + Constants.VERSION, HideawayPlus.client().player.getName().getString());
                try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String jsonContent = EntityUtils.toString(response.getEntity());
                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        if (jsonObject.has("code")) {
                            API.API_KEY = jsonObject.get("code").getAsString();
                            API.living = true;
                        }
                    } else if (response.getStatusLine().getStatusCode() == 400) {
                        String jsonContent = EntityUtils.toString(response.getEntity());
                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        if (jsonObject.has("error")) {
                            switch (jsonObject.get("message").getAsString()) {
                                case "User already exists", "Invalid UUID or code" -> {
                                    HideawayPlus.logger().error("API Error: " + jsonObject.get("message").getAsString());
                                }
                            }
                        }
                    }
                }
            } catch (IOException | ParseException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public static void asyncDestroy(String playerUUID, String apiCode) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpGet request = new HttpGet(API_URL + "end/" + playerUUID + "/" + apiCode);
                request.addHeader(Constants.MOD_NAME + " v" + Constants.VERSION, HideawayPlus.client().player.getName().getString());
                try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String jsonContent = EntityUtils.toString(response.getEntity());
                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        if (jsonObject.has("success") && jsonObject.get("success").getAsBoolean()) {
                            API.living = false;
                        }
                    }
                }
            } catch (IOException | ParseException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public static void asyncPlayerList() {
        CompletableFuture.runAsync(() -> {
            try {
                HttpGet request = new HttpGet(API_URL + "users/");
                request.addHeader(Constants.MOD_NAME + " v" + Constants.VERSION, HideawayPlus.client().player.getName().getString());
                try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String jsonContent = EntityUtils.toString(response.getEntity());

                        JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
                        JsonArray jsonElements = jsonObject.get("users").getAsJsonArray();

                        if (!jsonElements.isEmpty()) {
                            StaticValues.modUsers.clear();
                            for (int i = 0; i < jsonElements.size(); i++) {
                                StaticValues.modUsers.add(String.valueOf(jsonElements.get(i).getAsString()));
                            }
                        }
                    }
                }
            } catch (IOException | ParseException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}