package com.dekespo.flickr_search_app.retro;

import com.dekespo.flickr_search_app.models.FlickrResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService
{
    // TODO Use different things for key and secret and other struff
    @GET("?method=flickr.photos.search&api_key=1f7c428f03f28f460843946c1174ad46&format=json&nojsoncallback=1&text=cats")
    Call<FlickrResult> getFlickrResult();

}

