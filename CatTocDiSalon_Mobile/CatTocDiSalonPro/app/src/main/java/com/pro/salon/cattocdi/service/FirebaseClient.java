package com.pro.salon.cattocdi.service;


import com.pro.salon.cattocdi.models.FirebaseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FirebaseClient {

    @GET("{filename}")
    Call<FirebaseModel> getToken(@Path("filename") String filename);
}
