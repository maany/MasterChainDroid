package com.codewars.masterchain.retrofit;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Mayank on 11/8/2015.
 */
public interface TestRequests {
    @GET("/signupfinger/")
    Call<com.squareup.okhttp.Response> getUser(@Path("username") String username);


}
