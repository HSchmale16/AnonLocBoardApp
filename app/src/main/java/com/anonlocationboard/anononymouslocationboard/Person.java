package com.anonlocationboard.anononymouslocationboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;

/**
 * Person Identifier Class. On App First Run it will create an identifier in sharedPrefs, and
 * on subsequent runs return the original id.
 * Created by hschmale on 8/24/17.
 */

public class Person {
    private UUID id;
    private final SharedPreferences sharedPrefs;
    private static final String TAG = "Person";

    public Person(Context context) {
        sharedPrefs = context.getSharedPreferences("PersonIdentifier", 0);

        String s = sharedPrefs.getString("id", "NULL");
        if(s.equals("NULL")) {
            generateNewId();
        } else {
            id = UUID.fromString(s);
        }
    }

    private void generateNewId() {
        Log.v(TAG, "Generating new Id");
        id = UUID.randomUUID();
        Log.v(TAG, "ID: " + id.toString());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("id", id.toString());
        editor.apply();
    }

    public String getId() {
        return id.toString();
    }
}
