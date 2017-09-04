package com.anonlocationboard.anononymouslocationboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PostCreateActivity extends AppCompatActivity {
    public static final String TAG = "PostCreateActivity";
    private EditText postTitleText;
    private EditText postBodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        postTitleText = (EditText) findViewById(R.id.postTitle);
        postBodyText = (EditText) findViewById(R.id.postBody);

        Log.v(TAG, postTitleText.toString());
    }

    private static String validateEditText(EditText et) {
        String s = et.getText().toString().trim();
        if(s.equals("")) {
            et.setError("MUST not be blank or pure whitespace");
        } else {
            et.setError(null);
        }
        return s;
    }

    public void createPost(View view) {
        Log.v(TAG, "Create Post Fired");

        String postTitle = validateEditText(postTitleText);
        String postBody = validateEditText(postBodyText);

        // check for no error
        if(postTitle.length() == 0 || postBody.length() == 0) {
            return;
        }

        sendPost(postTitle, postBody);
    }

    public void sendPost(String title, String body) {
        //TODO: Connect to the location service
    }
}
