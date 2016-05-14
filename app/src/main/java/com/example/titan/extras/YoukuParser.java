package com.example.titan.extras;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.materialtest.R;
import com.example.titan.pojo.YouKuVideos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TITAN on 2015/5/2.
 */
public class YoukuParser {


    public static ArrayList<YouKuVideos> parseJSONBySearch(JSONObject response) {

        ArrayList<YouKuVideos> listVideos = new ArrayList<>();

        if (response == null || response.length() == 0){
            return null;
        }

        JSONObject currentJsonObject;

        String id;
        String title;
        String link;
        String thumbnail;
        try {
            JSONArray jsonArray = response.getJSONArray("videos");

            for (int i = 0; i < jsonArray.length(); i++){

                id = "no id";
                title = "no title";
                link = "no link";
                thumbnail = null;

                currentJsonObject = jsonArray.getJSONObject(i);
                YouKuVideos youKuVideos = new YouKuVideos();

                if(currentJsonObject.has("id") && !currentJsonObject.isNull("id")){

                    id = currentJsonObject.getString("id");
                }

                if(currentJsonObject.has("title") && !currentJsonObject.isNull("title")){

                    title = currentJsonObject.getString("title");

                }

                if (currentJsonObject.has("link") && !currentJsonObject.isNull("link")){
                    link = currentJsonObject.getString("link");
                }

                if (currentJsonObject.has("thumbnail") && ! currentJsonObject.isNull("thumbnail")){
                    thumbnail = currentJsonObject.getString("thumbnail");
                }

                youKuVideos.setId(id);
                youKuVideos.setTitle(title);
                youKuVideos.setLink(link);
                youKuVideos.setThumbnail(thumbnail);

                listVideos.add(youKuVideos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listVideos;
    }


    public static ArrayList<YouKuVideos> parseJSONByCategory(JSONObject response) {

        ArrayList<YouKuVideos> listVideos = new ArrayList<>();

        if (response == null || response.length() == 0){
            return null;
        }

        JSONObject currentJsonObject;

        String id;
        String name;
        String link;
        String thumbnail;
        try {
            JSONArray jsonArray = response.getJSONArray("shows");

            for (int i = 0; i < jsonArray.length(); i++){

                id = "no id";
                name = "no name";
                link = "no link";
                thumbnail = null;

                currentJsonObject = jsonArray.getJSONObject(i);
                YouKuVideos youKuVideos = new YouKuVideos();

                if(currentJsonObject.has("id") && !currentJsonObject.isNull("id")){

                    id = currentJsonObject.getString("id");
                }

                if(currentJsonObject.has("name") && !currentJsonObject.isNull("name")){

                    name = currentJsonObject.getString("name");

                }

                if (currentJsonObject.has("link") && !currentJsonObject.isNull("link")){
                    link = currentJsonObject.getString("link");
                }

                if (currentJsonObject.has("thumbnail") && ! currentJsonObject.isNull("thumbnail")){
                    thumbnail = currentJsonObject.getString("thumbnail");
                }

                youKuVideos.setId(id);
                youKuVideos.setTitle(name);
                youKuVideos.setLink(link);
                youKuVideos.setThumbnail(thumbnail);

                listVideos.add(youKuVideos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listVideos;
    }

}
