package com.dekespo.flickr_search_app.retro;

import com.dekespo.flickr_search_app.models.FlickrResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{
    @GET(".")
    Call<FlickrResult> getFlickrResult(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") boolean noJsonCallback,
            @Query("text") String text
    );

}

