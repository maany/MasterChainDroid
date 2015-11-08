package com.codewars.masterchain.retrofit;

import android.os.AsyncTask;

import java.io.File;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Mayank on 11/8/2015.
 */
public class UploadImageAsyncTask extends AsyncTask<File, Integer, String> {
    @Override
    protected String doInBackground(File... params) {
        File imageFile = params[0];
        String returnVal = "";
        TypedFile typedFile = new TypedFile("multipart/form-data", imageFile);
        String description = "userId";
        String API = "http://54.166.98.10:3000";
        RestAdapter restAdapter = null;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        TestRequests service = restAdapter.create(TestRequests.class);
        service.sendImage(typedFile ,description,new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                //returnVal = "Success";
            }

            @Override
            public void failure(RetrofitError error) {
               // returnVal = "Failure";
            }
        });
        return returnVal;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
