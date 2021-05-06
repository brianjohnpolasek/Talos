package talos.bot.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StravaHelper {
    private String stravaURL = "https://www.strava.com/api/v3/athlete/activities?access_token=4c94c3926f3353585f9617ed982193de6da3dd79";

    private String accessToken = "4c94c3926f3353585f9617ed982193de6da3dd79";

    public String getActivities() throws IOException {
        URL url = new URL(stravaURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }

        bufferedReader.close();
        connection.disconnect();

        return content.toString();
    }
}
