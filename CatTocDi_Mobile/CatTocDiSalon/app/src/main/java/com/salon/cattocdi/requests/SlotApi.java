package com.salon.cattocdi.requests;

import com.salon.cattocdi.models.DateSlot;
import com.salon.cattocdi.models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SlotApi {

    @GET("api/slot/available")
    Call<List<DateSlot>> getSlots(@Header("Authorization") String auth, @Query("salonId") int salonId, @Query("duration") int duration);

}
