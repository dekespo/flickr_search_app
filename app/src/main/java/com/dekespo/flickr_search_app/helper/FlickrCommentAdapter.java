package com.dekespo.flickr_search_app.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dekespo.flickr_search_app.R;
import com.dekespo.flickr_search_app.models.FlickrComment;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FlickrCommentAdapter extends ArrayAdapter<FlickrComment>
{
    private static final String TAG = "FlickrCommentAdapter";

    public FlickrCommentAdapter(Context context, ArrayList<FlickrComment> commentList)
    {
        super(context, android.R.layout.simple_list_item_2, commentList);
    }

    @NonNull
    @Override
    public View getView(int position, View recycled, @NonNull ViewGroup container)
    {
        LinearLayout itemView;
        TextView textView;
        Log.d(TAG, "Getting view of flickerComment");
        if (recycled == null)
        {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            itemView = (LinearLayout) inflator.inflate(R.layout.comment_item, container, false);
            textView = itemView.findViewById(R.id.comment_item);
        }
        else
        {
            textView = (TextView) recycled;
        }

        FlickrComment comment = getItem(position);
        textView.setText(comment.getContent());


        return textView;
    }
}
