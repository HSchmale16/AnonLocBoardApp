package com.anonlocationboard.anononymouslocationboard.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Prepares urls for the api request builder things
 * Created by hschmale on 8/26/17.
 */

public class ApiRequestBuilder {
    private static final String API_URL = "http://192.168.1.6:4000/";
    private static final String LIST_POSTS = API_URL + "list_posts/%f/%f/%d";
    private static final String VIEW_POST = API_URL + "view_post/%d";

    public static final String TAG = "ApiRequestBuilder";

    public static String getPostListUrl(double latitude, double longitude, int page) {
        return String.format(LIST_POSTS, latitude, longitude, page);
    }

    public static String getPostViewUrl(long id) {
        return String.format(VIEW_POST, id);
    }
}
