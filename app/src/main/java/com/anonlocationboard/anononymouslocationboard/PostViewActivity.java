package com.anonlocationboard.anononymouslocationboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anonlocationboard.anononymouslocationboard.API.ApiRequestBuilder;
import com.anonlocationboard.anononymouslocationboard.API.Post;

import org.json.JSONObject;

public class PostViewActivity extends AppCompatActivity {
    public static String TAG = "PostViewActivity";
    private RequestQueue requestQueue;
    private long postViewingId = Long.MIN_VALUE;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        requestQueue = Volley.newRequestQueue(this);

        loadBundle(savedInstanceState);
        performLoad();
    }

    private void makeView() {
        if (post == null)
            throw new IllegalStateException("Post must be set before this method can be called");
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(post.getTitle());

        TextView postText = (TextView) findViewById(R.id.postText);
        postText.setText(Html.fromHtml(post.getMessage()));
    }

    private void performLoad() {
        String url = ApiRequestBuilder.getPostViewUrl(postViewingId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                post = Post.fromJson(response);
                if (post == null) {
                    // Throw an error or pop up a warning
                    Log.e(TAG, "Improperly rendered post object");
                } else
                    makeView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pop up an error to the user
                Log.e(TAG, error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void loadBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Log.e(TAG, "Failed to get extras this is super bad");
                throw new IllegalStateException("Must have extras in intent");
            } else {
                postViewingId = extras.getLong("id");
            }
        } else {
            postViewingId = savedInstanceState.getLong("id");
        }
    }
}
