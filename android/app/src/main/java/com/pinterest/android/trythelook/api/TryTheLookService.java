package com.pinterest.android.trythelook.api;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface TryTheLookService {

    @GET("/")
    Call<String> getIndex();

    @Multipart
    @POST("api/v1/try_look")
    Call<ResponseBody> tryTheLook(@Part MultipartBody.Part img);

}
