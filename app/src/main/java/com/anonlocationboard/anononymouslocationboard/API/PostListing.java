package com.anonlocationboard.anononymouslocationboard.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Defines a post listing item as returned by list_posts from the HTTP API
 * Created by hschmale on 8/26/17.
 */

public class PostListing {
    protected long    id;
    protected String  title;
    protected UUID    clientId;
    protected double  latitude;
    protected double  longitude;

    public String getTitle() {
        return title;
    }

    public UUID getAuthor() {
        return clientId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getId() {
        return id;
    }

    public static PostListing fromJson(JSONObject jsonObject) {
        PostListing pl = new PostListing();

        try {
            pl.id = jsonObject.getLong("id");
            pl.title = jsonObject.getString("title");
            pl.clientId = UUID.fromString(jsonObject.getString("clientid"));
            pl.latitude = jsonObject.getDouble("latitude");
            pl.longitude = jsonObject.getDouble("longitude");
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }
        return pl;
    }

    public static ArrayList<PostListing> fromJson(JSONArray jsonArray) {
        JSONObject plJson;
        ArrayList<PostListing> listings = new ArrayList<>(jsonArray.length());

        for(int i = 0; i < jsonArray.length(); ++i) {
            try {
                plJson = jsonArray.getJSONObject(i);
            } catch(Exception e) {
                e.printStackTrace();
                continue;
            }

            PostListing listing = PostListing.fromJson(plJson);
            if(listing != null)
                listings.add(listing);
        }

        return listings;
    }
}
