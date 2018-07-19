package com.pinterest.android.trythelook.api;

import okhttp3.MultipartBody;
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
    Call<String> tryTheLook(@Part MultipartBody.Part img);

}
