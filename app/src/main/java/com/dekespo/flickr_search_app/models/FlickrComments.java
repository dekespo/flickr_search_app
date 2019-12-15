package com.dekespo.flickr_search_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlickrComments
{
    @SerializedName("photo_id")
    @Expose
    private String photoId;
    @SerializedName("comment")
    @Expose
    private ArrayList<FlickrComment> comment = null;

    public String getPhotoId()
    {
        return photoId;
    }

    public void setPhotoId(String photoId)
    {
        this.photoId = photoId;
    }

    public ArrayList<FlickrComment> getComment()
    {
        return comment;
    }

    public void setComment(ArrayList<FlickrComment> comment)
    {
        this.comment = comment;
    }
}
