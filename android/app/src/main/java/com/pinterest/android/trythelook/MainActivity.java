package com.pinterest.android.trythelook;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pinterest.android.trythelook.api.TryTheLookService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 101;
    private static final String BACKEND_BASE_URL = "http://10.0.2.2:8080/";
    public static final String USER_IMG_FILE = "image.jpg";

    private File pathToUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathToUserPhoto = new File(Environment.getExternalStorageDirectory(),
                USER_IMG_FILE);

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pathToUserPhoto));
        startActivityForResult(takePhotoIntent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"),
                    pathToUserPhoto);
            MultipartBody.Part img = MultipartBody.Part.createFormData("file",
                    pathToUserPhoto.getName(),
                    requestBody);

            postUserImg(img);
        }
    }

    private void postUserImg(MultipartBody.Part img) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TryTheLookService service = retrofit.create(TryTheLookService.class);
        service.tryTheLook(img)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
