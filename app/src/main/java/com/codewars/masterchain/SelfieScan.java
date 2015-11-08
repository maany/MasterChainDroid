package com.codewars.masterchain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codewars.masterchain.retrofit.TestRequests;
import com.codewars.masterchain.retrofit.UploadImageAsyncTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class SelfieScan extends Activity {

    private Button mFingerPrintScanButton;
    private Button mSignUpActivityButton;
    private EditText editText;
    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE_LONG = 2;
    private static final String BIOMETRIC_SERVER_URL = "http://54.166.98.10:3000";
    private static final int FINGERPRINT_REQUEST_CODE =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie_scan);
        mFingerPrintScanButton = (Button)findViewById(R.id.fingerPrintScanButton);
        mSignUpActivityButton = (Button)findViewById(R.id.signUpActivityButton);
        editText = (EditText)findViewById(R.id.editText);

        mFingerPrintScanButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dispatchTakePictureIntentLong();
                return false;
            }
        });
        mFingerPrintScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });
        mSignUpActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selfie_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void dispatchTakePictureIntentLong() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            //upload fingerprint to server via ASYNC TASK
            if(requestCode == FINGERPRINT_REQUEST_CODE){
                File imageFile = null;
                try {

                    imageFile = createImageFile();
                    String userId = getUserId();
                    uploadFingerprintToServerAsync(imageFile, userId);
                    Intent intent = new Intent(this, BiometricSuccess.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_LONG && resultCode == RESULT_OK) {
            Intent failIntent = new Intent(this, FailedActivity.class);
            startActivity(failIntent);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    private void uploadFingerprintToServer(File imageFile,String userId){
        TypedFile typedFile = new TypedFile("multipart/form-data", imageFile);
        String description = "userId";
        String API = "http://54.166.98.10:3000";
        RestAdapter restAdapter = null;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        TestRequests service = restAdapter.create(TestRequests.class);
        service.sendImage(typedFile ,description,new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                editText.setText("Success");
            }

            @Override
            public void failure(RetrofitError error) {
                editText.setText("Failure");
            }
        });

    }
    private void uploadFingerprintToServerAsync(File imageFile,String userId){
        UploadImageAsyncTask task = new UploadImageAsyncTask();
        task.execute(imageFile);
    }
    private String sentTestRetrofitRequest(){
        String API = "http://54.166.98.10:3000";
        RestAdapter restAdapter = null;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        TestRequests service = restAdapter.create(TestRequests.class);
        service.getUser(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                editText.setText("Success");
            }

            @Override
            public void failure(RetrofitError error) {
                editText.setText("Failure");
            }
        });
        return "";
    }
    /**
     * Get User Id from fb
     * //todo
     * @return
     */
    public String getUserId(){
        return "demo_user_1";
    }
}
