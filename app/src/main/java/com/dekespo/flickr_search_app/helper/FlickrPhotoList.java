package com.dekespo.flickr_search_app.helper;

import com.dekespo.flickr_search_app.models.FlickrPhoto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlickrPhotoList
{
    @SerializedName("flickrPhotos")
    @Expose
    private ArrayList<FlickrPhoto> flickrPhotos = null;

    public ArrayList<FlickrPhoto> getFlickerPhotos()
    {
        return flickrPhotos;
    }

    public void setFlickrPhotos(ArrayList<FlickrPhoto> flickrPhotos)
    {
        this.flickrPhotos = flickrPhotos;
    }

}
