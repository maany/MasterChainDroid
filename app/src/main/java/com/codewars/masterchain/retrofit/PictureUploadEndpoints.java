package com.codewars.masterchain.retrofit;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Mayank on 11/8/2015.
 */
public interface PictureUploadEndpoints {

    @Multipart
    @POST("/signupfinger")
    public Call<Response> uploadFingerPrint(@Part("image") RequestBody photo, @Part("userId") String userId);


}
