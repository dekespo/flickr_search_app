package com.dekespo.flickr_search_app.retro;

import com.dekespo.flickr_search_app.models.FlickrResult;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickerApi
{
    @GET(".")
    Call<FlickrResult> getPhotosSearchResult(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") boolean noJsonCallback,
            @Query("text") String text
    );

    // TODO: Some settings do not work properly or images are too new
    @GET(".")
    Single<FlickrResult> getPhotosSearchResultRxJava(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("text") String text,
            @Query("privacy_filter ") int privacyFilter,
            @Query("safe_search") int safeSearch,
            @Query("content_type ") int contentType,
            @Query("media") String media,
            @Query("in_gallery") boolean in_gallery,
            @Query("format") String format,
            @Query("nojsoncallback") boolean noJsonCallback
    );

    // TODO for search use small pictures?
}

