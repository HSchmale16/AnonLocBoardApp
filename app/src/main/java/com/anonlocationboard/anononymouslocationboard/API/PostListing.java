package com.anonlocationboard.anononymouslocationboard.API;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.animation.ScaleAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Defines a post listing item as returned by list_posts from the HTTP API
 * Created by hschmale on 8/26/17.
 */

public class PostListing {
    long id;
    String title;
    UUID clientId;
    double latitude;
    double longitude;
    long whenAt;

    protected PostListing() {
    }

    public String getTitle() {
        return title != null ? title : "";
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

    /**
     * @return An northing easting based latitude and longitude as a string
     */
    public String getFormatedLatLong() {
        StringBuilder format = new StringBuilder();

        // do latitude first
        if (latitude >= 0)
            format.append("%f N, ");
        else
            format.append("%f S, ");

        if (longitude >= 0)
            format.append("%f E");
        else
            format.append("%f W");

        return String.format(Locale.getDefault(), format.toString(), latitude, longitude);
    }

    protected void setDate(String date) {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date2 = format.parse(date);
            whenAt = date2.getTime();

        } catch (ParseException e) {
            Log.v("ParseException", e.getMessage());
        }
    }

    public String getRelativeDate() {
        Date now = new Date();
        return DateUtils
                .getRelativeTimeSpanString(whenAt, now.getTime(), DateUtils.SECOND_IN_MILLIS)
                .toString();
    }

    public static PostListing fromJson(JSONObject jsonObject) {
        PostListing pl = new PostListing();

        try {
            pl.id = jsonObject.getLong("id");
            pl.title = jsonObject.getString("title");
            pl.clientId = UUID.fromString(jsonObject.getString("clientid"));
            pl.latitude = jsonObject.getDouble("latitude");
            pl.longitude = jsonObject.getDouble("longitude");
            pl.setDate(jsonObject.getString("whenat"));
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
