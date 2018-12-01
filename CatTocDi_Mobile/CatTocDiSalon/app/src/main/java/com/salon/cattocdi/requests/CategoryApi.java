package com.salon.cattocdi.requests;

import com.salon.cattocdi.models.Category;
import com.salon.cattocdi.models.Salon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoryApi {

    @GET("api/Category")
    Call<List<Category>> getAllCategory(@Header("Authorization") String auth);

}
