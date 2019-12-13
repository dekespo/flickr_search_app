package com.dekespo.flickr_search_app.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dekespo.flickr_search_app.R;
import com.dekespo.flickr_search_app.models.FlickrPhoto;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FlickrPhotoAdapter extends ArrayAdapter<FlickrPhoto>
{
    public FlickrPhotoAdapter(Context context, ArrayList<FlickrPhoto> photoList)
    {
        super(context, android.R.layout.simple_list_item_1, photoList);
    }

    @NonNull
    @Override
    public View getView(int position, View recycled, @NonNull ViewGroup container)
    {
        LinearLayout itemView;
        final ImageView myImageView;
        if (recycled == null)
        {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            itemView = (LinearLayout) inflator.inflate(R.layout.photo_item, container, false);
            myImageView = itemView.findViewById(R.id.photo_place);
        }
        else
        {
            myImageView = (ImageView) recycled;
        }

        FlickrPhoto photo = getItem(position);
        String url = String.format("http://farm%d.staticflickr.com/%s/%s_%s.jpg", photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());

        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_menu_camera)
                .into(myImageView);

        return myImageView;
    }
}
