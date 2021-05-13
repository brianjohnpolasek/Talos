package talos.bot.helpers;

import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import talos.bot.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StravaHelper {
    //REMOVE LATER
    private final String client_id = Config.get("STRAVA_CLIENT_ID");
    private final String client_secret = Config.get("STRAVA_CLIENT_SECRET");
    private final String refresh_token = Config.get("STRAVA_REFRESH_TOKEN");

    private final String stravaURL = "https://www.strava.com/api/v3/athlete/activities?access_token=";
    private final String authURL = "https://www.strava.com/oauth/token?client_id="
            + client_id
            + "&client_secret="
            + client_secret
            + "&refresh_token="
            + refresh_token
            + "&grant_type=refresh_token";

    public String getActivities() throws IOException {
        URL url = new URL(stravaURL + getAccessToken());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }

        //Close reader and connection
        bufferedReader.close();
        connection.disconnect();

        //Return just activity names
        JSONArray response = new JSONArray(content.toString());
        return IntStream.range(0, response.length())
                .mapToObj(index -> ((JSONObject) response.get(index)).optString("name"))
                .collect(Collectors.toList()).toString();
    }

    public EmbedBuilder getLatestActivityData() {
        EmbedBuilder embedBuilder = new EmbedBuilder();


    }

    private String getAccessToken() throws IOException {
        URL url = new URL(authURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty("Accept", "application/json");

        connection.setDoOutput(true);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"));

        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine.trim());
        }

        bufferedReader.close();
        connection.disconnect();

        JSONObject accessToken = new JSONObject(response.toString());

        return accessToken.getString("access_token");
    }
}
