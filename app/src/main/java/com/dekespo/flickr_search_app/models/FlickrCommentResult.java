package com.dekespo.flickr_search_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrCommentResult
{
    @SerializedName("comments")
    @Expose
    private FlickrComments comments;
    @SerializedName("stat")
    @Expose
    private String stat;

    public FlickrComments getComments()
    {
        return comments;
    }

    public void setComments(FlickrComments comments)
    {
        this.comments = comments;
    }

    public String getStat()
    {
        return stat;
    }

    public void setStat(String stat)
    {
        this.stat = stat;
    }
}
