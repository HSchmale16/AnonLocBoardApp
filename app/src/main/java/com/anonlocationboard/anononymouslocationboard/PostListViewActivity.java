package com.anonlocationboard.anononymouslocationboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anonlocationboard.anononymouslocationboard.API.ApiRequestBuilder;
import com.anonlocationboard.anononymouslocationboard.API.PostListing;
import com.anonlocationboard.anononymouslocationboard.Adapters.PostListingAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

public class PostListViewActivity extends AppCompatActivity {
    public static final String TAG = "PostListViewActivity";
    private RequestQueue requestQueue;
    private PostListingAdapter listingAdapter;
    ArrayList<PostListing> listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostListViewActivity.this, PostCreateActivity.class);
                startActivity(intent);
            }
        });

        // Setup the List View
        listings = new ArrayList<>();
        listingAdapter = new PostListingAdapter(this, listings);

        ListView lvItems = (ListView) findViewById(R.id.post_list_view);
        lvItems.setAdapter(listingAdapter);

        lvItems.setOnScrollListener(new EndlessScrollListener(5, -1) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadNextDataFromApi(page);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostListing postListing = listingAdapter.getItem(i);
                Intent intent = new Intent(PostListViewActivity.this, PostViewActivity.class);
                intent.putExtra("id", postListing.getId());
                startActivity(intent);
            }
        });
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()
        Log.v(TAG, "offset: " + offset);
        Log.v(TAG, "Attempt load of more items");
        String url = ApiRequestBuilder.getPostListUrl(45, 50, offset);
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listingAdapter.addAll(PostListing.fromJson(response));
                        listingAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });
        Log.v(TAG, req.toString());
        requestQueue.add(req);
    }
}
