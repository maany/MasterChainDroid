package com.codewars.masterchain.retrofit;

import com.squareup.okhttp.RequestBody;

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
    @POST("/user/fingerprint")
    public void uploadImage(@Part("photo") RequestBody photo, @Part("description") String userId);


}
