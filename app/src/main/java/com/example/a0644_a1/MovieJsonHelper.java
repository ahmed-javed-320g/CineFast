package com.example.a0644_a1;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MovieJsonHelper {

    public static ArrayList<movie> loadMovies(Context context, String key) {
        ArrayList<movie> movies = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray(key);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String title = obj.getString("title");
                String genre = obj.getString("genre");
                String trailerUrl = obj.getString("trailer_url");
                String posterName = obj.getString("poster");
                boolean isComingSoon = key.equals("coming_soon");

                int resId = context.getResources().getIdentifier(
                        posterName, "drawable", context.getPackageName());

                movies.add(new movie(title, genre, resId, trailerUrl, isComingSoon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}