package com.dekespo.flickr_search_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;


public class FlickrPhoto implements Serializable
{
    public static final String FLICKR_PHOTO_KEY = "FlickrPhoto";

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private long farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private long ispublic;
    @SerializedName("isfriend")
    @Expose
    private long isfriend;
    @SerializedName("isfamily")
    @Expose
    private long isfamily;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public long getFarm()
    {
        return farm;
    }

    public void setFarm(long farm)
    {
        this.farm = farm;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public long getIspublic()
    {
        return ispublic;
    }

    public void setIspublic(long ispublic)
    {
        this.ispublic = ispublic;
    }

    public long getIsfriend()
    {
        return isfriend;
    }

    public void setIsfriend(long isfriend)
    {
        this.isfriend = isfriend;
    }

    public long getIsfamily()
    {
        return isfamily;
    }

    public void setIsfamily(long isfamily)
    {
        this.isfamily = isfamily;
    }

    public String getUrl()
    {
        return String.format("http://farm%d.staticflickr.com/%s/%s_%s.jpg", this.getFarm(), this.getServer(), this.getId(), this.getSecret());
    }

    @NonNull
    @Override
    public String toString()
    {
//        String[] list = {this.id, this.owner, this.secret, this.server, "" + this.farm, this.title, "" + this.ispublic, "" + this.isfriend, "" + this.isfamily};
//        return TextUtils.join(", ", list);
        return this.title;
    }

}
