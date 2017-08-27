package com.anonlocationboard.anononymouslocationboard.API;

import org.json.JSONObject;

import java.security.InvalidParameterException;

/**
 * Defines a post as being a listing + it's containing message
 * Created by hschmale on 8/26/17.
 */

public class Post extends PostListing {
    protected String message;

    public String getMessage() {
        return message;
    }

    private Post(PostListing listing, String message) {
        if(listing == null)
            throw new InvalidParameterException();
        this.clientId = listing.clientId;
        this.id = listing.id;
        this.title = listing.title;
        this.latitude = listing.latitude;
        this.longitude = listing.longitude;
        this.message = message;
    }

    public static Post fromJson(JSONObject jsonObject) {
        try {
            PostListing listing = PostListing.fromJson(jsonObject);
            if(listing == null)
                return null;
            return new Post(listing, jsonObject.getString("message"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
