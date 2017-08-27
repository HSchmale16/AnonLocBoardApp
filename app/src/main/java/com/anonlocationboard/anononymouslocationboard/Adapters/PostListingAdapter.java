package com.anonlocationboard.anononymouslocationboard.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anonlocationboard.anononymouslocationboard.API.PostListing;
import com.anonlocationboard.anononymouslocationboard.R;

import java.util.ArrayList;

/**
 * Defines the adapter that loads up the post listings and handles the custom item view stuff.
 * Created by hschmale on 8/26/17.
 */

public class PostListingAdapter extends ArrayAdapter<PostListing> {
    public PostListingAdapter(Context context, ArrayList<PostListing> listings) {
        super(context, 0, listings);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostListing listing = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post_listing, parent, false);

        // TODO Work on the things displayed in the text view
        TextView title = convertView.findViewById(R.id.postTitle);
        title.setText(listing.getTitle());
        return convertView;
    }
}
