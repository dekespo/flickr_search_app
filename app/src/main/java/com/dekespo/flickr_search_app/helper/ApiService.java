package com.dekespo.flickr_search_app.helper;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService
{
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of EmployeeList
    */
    @GET("retrofit/json_object.json")
    Call<EmployeeList> getMyJSON();
}

