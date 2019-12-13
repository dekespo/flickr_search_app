package com.dekespo.flickr_search_app.retro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FlickrClient
{

    public static final String METHOD = "flickr.photos.search";
    public static final String API_KEY = "1f7c428f03f28f460843946c1174ad46";
    public static final String FORMAT = "json";
    public static final boolean NO_JSON_CALLBACK = true;
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";

    private static Retrofit getRetrofitInstance()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService()
    {
        return getRetrofitInstance().create(ApiService.class);
    }
}

