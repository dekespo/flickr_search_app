package com.dekespo.flickr_search_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dekespo.flickr_search_app.models.FlickrPhoto;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.dekespo.flickr_search_app.models.FlickrPhoto.FLICKR_PHOTO_KEY;

public class DetailFragment extends Fragment
{
    private static final String TAG = "DetailFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        Log.d(TAG, "At the detial fragment now");
        FlickrPhoto photo = (FlickrPhoto) getArguments().getSerializable(FLICKR_PHOTO_KEY);
        ImageView imageView = root.findViewById(R.id.detail_image);
        Glide.with(getContext())
                .load(photo.getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_menu_camera)
                .into(imageView);

        return root;
    }
}