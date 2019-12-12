package com.dekespo.flickr_search_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FlickrPhotos
{
    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("pages")
    @Expose
    private long pages;
    @SerializedName("perpage")
    @Expose
    private long perpage;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("photo")
    @Expose
    private ArrayList<FlickrPhoto> photo = null;

    public long getPage()
    {
        return page;
    }

    public void setPage(long page)
    {
        this.page = page;
    }

    public long getPages()
    {
        return pages;
    }

    public void setPages(long pages)
    {
        this.pages = pages;
    }

    public long getPerpage()
    {
        return perpage;
    }

    public void setPerpage(long perpage)
    {
        this.perpage = perpage;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public ArrayList<FlickrPhoto> getPhoto()
    {
        return photo;
    }

    public void setPhoto(ArrayList<FlickrPhoto> photo)
    {
        this.photo = photo;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Fickr Photos";
    }
}
