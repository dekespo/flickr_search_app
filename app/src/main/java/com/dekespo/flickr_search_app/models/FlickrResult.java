package com.dekespo.flickr_search_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class FlickrResult
{
    @SerializedName("photos")
    @Expose
    private FlickrPhotos photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public FlickrPhotos getPhotos()
    {
        return photos;
    }

    public void setPhotos(FlickrPhotos photos)
    {
        this.photos = photos;
    }

    public String getStat()
    {
        return stat;
    }

    public void setStat(String stat)
    {
        this.stat = stat;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Flicker Result";
    }
}
