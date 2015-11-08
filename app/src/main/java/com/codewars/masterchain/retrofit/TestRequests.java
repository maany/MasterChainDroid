package com.codewars.masterchain.retrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by Mayank on 11/8/2015.
 */
public interface TestRequests {
    @POST("/signupfinger/")
    public void getUser(Callback<String> response);

    @Multipart
    @POST("/signupfinger/")
    public void sendImage(@Part("myfile") TypedFile file,
                        @Part("description") String description,Callback<String> response);


}
