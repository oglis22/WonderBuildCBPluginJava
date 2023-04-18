package dev.lupluv.cb.voting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VoteAPI {

    private static final String USER_AGENT  = "LUPLUV";

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // minecraft-server.eu
    public static final String vote1_serverKey = "mss6352eaf78fd052x858186336352eaf88fd";
    public static final String vote1_check = "https://minecraft-server.eu/api/v1/?object=votes&element=claim&key=" + vote1_serverKey + "&username={username}";
    public static final String vote1_claim = "https://minecraft-server.eu/api/v1/?action=post&object=votes&element=claim&key=" + vote1_serverKey + "&username={username}";

    public static int checkVote1(Player p){
        try {
            URL url = new URL(vote1_check.replace("{username}", p.getName()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent

            // If you're not sure if the request will be successful,
            // you need to check the response code and use #getErrorStream if it returned an error code
            InputStream inputStream = connection.getInputStream();

            try {
                int response = Integer.parseInt(getStringFromInputStream(inputStream));
                return response;
            }catch (NumberFormatException ex){
                return 0;
            }


        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean claimVote1(Player p){
        try {
            URL url = new URL(vote1_claim.replace("{username}", p.getName()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);// Set User-Agent

            // If you're not sure if the request will be successful,
            // you need to check the response code and use #getErrorStream if it returned an error code
            InputStream inputStream = connection.getInputStream();

            try {
                int response = Integer.parseInt(getStringFromInputStream(inputStream));
                if(response == 1) return true;
            }catch (NumberFormatException ex){
                return false;
            }


        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

}
