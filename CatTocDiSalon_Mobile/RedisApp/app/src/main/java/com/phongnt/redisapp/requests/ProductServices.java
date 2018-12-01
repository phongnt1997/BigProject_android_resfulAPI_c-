package com.phongnt.redisapp.requests;

import com.phongnt.redisapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductServices {
    @GET("api/products")
    Call<List<Product>> getAllProduct();
    @GET("api/products/{id}")
    Call<Product> getProductById(@Path("id") int id);
}
