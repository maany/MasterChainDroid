package com.codewars.masterchain.retrofit;

//import com.squareup.okhttp.RequestBody;
///import com.squareup.okhttp.Response;

//import retrofit.Call;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Mayank on 11/8/2015.
 */
public interface PictureUploadEndpoints {

    @Multipart
    @POST("/signupfinger")
    public void uploadFingerPrint(@Part("image") TypedFile photo, @Part("userId") String userId);

    @Multipart
    @POST("/signupfinger")
    public void uploadSelfie(@Part("image") TypedFile photo, @Part("userId") String userId);

    @Multipart
    @POST("/signupfinger")
    public void verifyFingerPrint(@Part("image") TypedFile photo, @Part("userId") String userId);

    @Multipart
    @POST("/signupfinger")
    public void verifySelfie(@Part("image") TypedFile photo, @Part("userId") String userId);


}
