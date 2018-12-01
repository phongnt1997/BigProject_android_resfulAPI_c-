package com.salon.cattocdi.requests;

import com.salon.cattocdi.models.Account;
import com.salon.cattocdi.models.Customer;
import com.salon.cattocdi.models.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AccountApi {
    @FormUrlEncoded
    @POST("login")
    Call<Account> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("api/Account/Register")
    Call<ResponseMessage> register(@Field("username") String username,
                                   @Field("password") String password,
                                   @Field("gender") boolean gender,
                                   @Field("firstname") String firstname,
                                   @Field("lastname") String lastname,
                                   @Field("phoneNumber") String phone);

    @GET("api/customer")
    Call<Customer> getProfile(@Header("Authorization") String auth);

    @POST("api/customer/profile")
    Call<String> updateProfile(@Header("Authorization") String auth, @Body Customer customer);
}
