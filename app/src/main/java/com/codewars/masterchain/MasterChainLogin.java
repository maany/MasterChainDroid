package com.codewars.masterchain;
import com.codewars.masterchain.retrofit.TestRequests;
import com.squareup.okhttp.Response;
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
import android.widget.ImageView;
import retrofit.Call;
import com.codewars.masterchain.retrofit.PictureUploadEndpoints;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Retrofit;

public class MasterChainLogin extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String BIOMETRIC_SERVER_URL = "http://54.166.98.10:3000";
    private static final int FINGERPRINT_REQUEST_CODE =1;
    String mCurrentPhotoPath;
    //private ImageView mImageView;
    private Button mFingerPrintScanButton;
    private Button mSignUpActivityButton;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_chain_login);
        mFingerPrintScanButton = (Button)findViewById(R.id.fingerPrintScanButton);
        mSignUpActivityButton = (Button)findViewById(R.id.signUpActivityButton);
        editText = (EditText)findViewById(R.id.editText);
        editText.setText("sending test request");
        try {
            String name = sentTestRetrofitRequest();
            editText.setText("sent test request");
            editText.setText(name);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        mFingerPrintScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });
        mSignUpActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MasterChainLogin.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_chain_login, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            //todo upload fingerprint to server via ASYNC TASK
            if(requestCode == FINGERPRINT_REQUEST_CODE){
                File imageFile = null;
                try {
                    imageFile = createImageFile();
                String userId = getUserId();
                uploadFingerprintToServer(imageFile, userId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BIOMETRIC_SERVER_URL)
                .build();
        PictureUploadEndpoints uploadService = retrofit.create(PictureUploadEndpoints.class);
        RequestBody file = RequestBody.create(MediaType.parse("image/*"), imageFile.getAbsoluteFile());
        Call<Response> returnCode = uploadService.uploadFingerPrint(file,userId);
    }

    private String sentTestRetrofitRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();
        TestRequests testRequests = retrofit.create(TestRequests.class);
        String name= "test";
        testRequests.getUser(name);
        return name;
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
