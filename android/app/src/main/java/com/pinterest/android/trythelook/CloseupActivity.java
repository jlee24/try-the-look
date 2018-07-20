package com.pinterest.android.trythelook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.pinterest.android.trythelook.api.TryTheLookService;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloseupActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LOG_TAG";
    private static final int REQUEST_CODE = 101;

    // TODO: remember to have ngrok running
    private static final String BACKEND_BASE_URL = "http://97dd49b3.ngrok.io";
    public static final String USER_IMG_FILE = "image.jpg";

    private File pathToUserPhoto;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closeup);

        imageView = findViewById(R.id.imageView);

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

        final ProgressDialog nDialog = new ProgressDialog(CloseupActivity.this);
        nDialog.setTitle("Try the Look");
        nDialog.setMessage("Spinning some yarns");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        TryTheLookService service = retrofit.create(TryTheLookService.class);
        service.tryTheLook(img)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            byte[] bytes = response.body().bytes();
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            nDialog.dismiss();
                            imageView.setImageBitmap(bmp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        nDialog.dismiss();
                        Toast.makeText(CloseupActivity.this,
                                "Oops! " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
