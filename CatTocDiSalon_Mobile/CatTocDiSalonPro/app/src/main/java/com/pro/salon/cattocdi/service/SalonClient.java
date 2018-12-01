package com.pro.salon.cattocdi.service;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.pro.salon.cattocdi.models.Account;
import com.pro.salon.cattocdi.models.Appointment;
import com.pro.salon.cattocdi.models.AppointmentListHome;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.Comment;
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.models.Promotion;
import com.pro.salon.cattocdi.models.ResponseMsg;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.models.WorkingHour;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SalonClient {
    @FormUrlEncoded
    @POST("api/Account/Register")
    Call<ResponseMsg> createAccount(@Field("SalonName") String name, @Field("Address") String address, @Field("Username") String username, @Field("password") String password,
                                    @Field("Email") String email, @Field("PhoneNumber") String phone, @Field("Role") String role, @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("token")
    Call<Account> login(@Field("Username") String username, @Field("password") String password, @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("token")
    Call<Account> login(@Field("Username") String username, @Field("password") String password, @Field("grant_type") String grantType, @Header("Authorization") String authHeader);

    @GET("api/Categories")
    Call<List<Category>> getCategoried(@Header("Authorization") String auth);



    @FormUrlEncoded
    @POST("api/Services/Update")
    Call<String> updateServices(@Header("Authorization") String auth,@Field("Id") int salonServiceId, @Field("ServiceId") int serviceId
            , @Field("Price") double price, @Field("Duration") int duration);


    @DELETE("api/Services")
    Call<String> deleteService(@Header("Authorization") String auth, @Query("salonServiceId") int id);

    //Sua cho nay nha
    @GET("api/Services")
    Call<List<Service>> getService(@Header("Authorization") String auth);

    @GET("api/Promotion")
    Call<List<Promotion>> getPromotion(@Header("Authorization") String auth);


    @POST("api/Promotions/Delete")
    Call<String> updatePromotion(@Header("Authorization") String auth,@Query("id") int id);

    @FormUrlEncoded
    @POST("api/Promotions")
    Call<String> createPromotion(@Header("Authorization")String auth, @Field("StartTime")String startTime,
                                 @Field("EndTime") String endTime, @Field("DiscountPercent") int discount,
                                 @Field("Description") String description);


    @POST("api/Salons/WorkingHour")
    Call<String> updateWorkingHour(@Header("Authorization") String auth, @Body List<WorkingHour> workingHourList);

    @GET("api/Salons/WorkingHour")
    Call<List<WorkingHour>> getWorkingHour(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("api/Salons/Profile")
    Call<String> updateProfile(@Header("Authorization") String auth, @Field("SalonName") String salonName,
                                    @Field("Address") String address, @Field("Capacity") int capital, @Field("Phone") String phone,
                                    @Field("Email") String email, @Field("Longitude") double longtitude, @Field("Latitude") double latitude);
    @GET("api/Salons")
    Call<Salon> getSalonProfile(@Header("Authorization") String auth);


    @GET("api/Customer")
    Call<List<Customer>> getAllCustomer(@Header("Authorization") String auth);

    @GET("api/Customer")
    Call<Customer> getCustomerDetail(@Header("Authorization") String auth, @Query("id") int customerId);

    @GET("api/Appointment")
    Call<AppointmentListHome> getAppointmentHome(@Header("Authorization") String auth);

    @GET("api/Appointment")
    Call<List<Appointment>> getAppointmentByDate(@Header("Authorization") String auth, @Query("date") String date);
    @GET("api/Review")
    Call<List<Comment>> getReview(@Header("Authorization") String auth);

    @POST("api/Appointment/{id}/Approve")
    Call<String> approveAppointment(@Header("Authorization") String auth, @Path("id") int appointmentId);

    @POST("api/Appointment/{id}/Cancel")
    Call<String> cancelAppointment(@Header("Authorization") String auth, @Path("id") int appointmentId, @Body String reason);

    @POST("api/Images/")
    Call<String> updateImage(@Header("Authorization") String auth, @Body String imageUrl);
}
