package com.salon.cattocdi.requests;

import com.salon.cattocdi.models.Salon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SalonApi {

    @GET("api/Salon")
    Call<List<Salon>> getAllSalon(@Header("Authorization") String auth);

    @GET("api/Salon")
    Call<List<Salon>> searchSalon(@Header("Authorization") String auth, @Query("nameAndAddress") String nameOrAddress,@Query("service") String services);

    @GET("api/Salon/{id}")
    Call<Salon> getSalonById(@Header("Authorization") String auth, @Path("id") int id);

}
