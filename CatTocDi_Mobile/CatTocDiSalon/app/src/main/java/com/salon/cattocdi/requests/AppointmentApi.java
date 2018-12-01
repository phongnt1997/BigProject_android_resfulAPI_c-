package com.salon.cattocdi.requests;

import com.salon.cattocdi.models.Appointment;
import com.salon.cattocdi.models.AppointmentCreateModel;
import com.salon.cattocdi.models.Comment;
import com.salon.cattocdi.models.DateSlot;
import com.salon.cattocdi.models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppointmentApi {

    @GET("api/appointment")
    Call<List<Appointment>> getAllAppointment(@Header("Authorization") String auth);

    @POST("api/appointment/delete")
    Call<String> cancelAppointment(@Header("Authorization") String auth, @Query("id") int id);

    @POST("api/reviews")
    Call<String> reviewAppointment(@Header("Authorization") String auth, @Body Comment comment);

    @POST("api/appointment")
    Call<String> addAppointment(@Header("Authorization") String auth, @Body AppointmentCreateModel appointmentCreateModel);

}
