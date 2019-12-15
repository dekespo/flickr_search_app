package com.dekespo.flickr_search_app.helper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dekespo.flickr_search_app.DetailFragment;
import com.dekespo.flickr_search_app.R;
import com.dekespo.flickr_search_app.models.FlickrPhoto;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

public class FlickrPhotoAdapter extends ArrayAdapter<FlickrPhoto>
{
    private FragmentTransaction mTransaction;

    public FlickrPhotoAdapter(Context context, ArrayList<FlickrPhoto> photoList, FragmentTransaction transaction)
    {
        super(context, android.R.layout.simple_list_item_1, photoList);
        if (photoList.size() == 0)
            Toast.makeText(context, "No Image is found!", Toast.LENGTH_LONG).show();
        mTransaction = transaction;
    }

    @NonNull
    @Override
    public View getView(int position, View recycled, @NonNull ViewGroup container)
    {
        LinearLayout itemView;
        final ImageView imageView;
        if (recycled == null)
        {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            itemView = (LinearLayout) inflator.inflate(R.layout.photo_item, container, false);
            imageView = itemView.findViewById(R.id.photo_place);
        }
        else
        {
            imageView = (ImageView) recycled;
        }

        FlickrPhoto photo = getItem(position);

        Glide.with(getContext())
                .load(photo.getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_menu_camera)
                .into(imageView);

        final FlickrPhoto finalPhoto = photo;

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable(FlickrPhoto.FLICKR_PHOTO_KEY, finalPhoto);
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                mTransaction.replace(R.id.nav_host_fragment, detailFragment);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
            }
        });

        return imageView;
    }
}
