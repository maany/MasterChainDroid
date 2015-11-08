package com.codewars.masterchain.retrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Mayank on 11/8/2015.
 */
public interface TestRequests {
    @POST("/signupfinger/")
    public void getUser(Callback<String> response);


}
