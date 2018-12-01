package com.pro.salon.cattocdi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pro.salon.cattocdi.models.Account;
import com.pro.salon.cattocdi.models.ResponseMsg;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername, etName, etPhone, etEmail, etPassword;
    private Button btnSignup1;
    private String address, role;
    private Boolean isForMen, isForWomen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final String grant_type = "password";
        etUsername = findViewById(R.id.signup_fullname_txt);
        etName = findViewById(R.id.signup_salonname_txt);
        etPhone = findViewById(R.id.signup_phone_txt);
        etEmail = findViewById(R.id.signup_email_txt);
        etPassword = findViewById(R.id.signup_password_txt);
        address = "address test";
        isForMen = false;
        isForWomen = true;
        role = "salon";
        btnSignup1 = findViewById(R.id.signup_activity_create_first_btn);
        btnSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .createAccount(etName.getText().toString(),
                                address.toString(),
                                etUsername.getText().toString(),
                                etPassword.getText().toString(),
                                etEmail.getText().toString(),
                                etPhone.getText().toString(),
                                role.toString(), "password").enqueue(new Callback<ResponseMsg>() {
                    @Override
                    public void onResponse(Call<ResponseMsg> call, Response<ResponseMsg> response) {
                        if (response.body().isSucceeded()) {
                            loginGetToken();
//                            signupStepOne(view);
                        } else {
                            showDialogLoginFail("Tên đăng nhập này đã tồn tại");
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseMsg> call, Throwable t) {
                        Log.d("FAILURE", t.getMessage());
                        showDialogLoginFail("Có lỗi xảy ra. Không thể đăng kí");
                    }
                });

            }
        });
    }

    public void signupStepOne(View view) {
        Intent intent = new Intent(this, ServiceSignupActivity.class);
        intent.putExtra("from_page", MyContants.SIGNUP_PAGE);
        startActivity(intent);
    }

    private void showDialogLoginFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(SignupActivity.this).create();
        dialog.setTitle("Không chính xác");
        dialog.setMessage(text);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void loginGetToken(){
        ApiClient.getInstance()
                .create(SalonClient.class)
                .login(etUsername.getText().toString(), etPassword.getText().toString(), "password")
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, retrofit2.Response<Account> response) {
                        Log.d("RESPONSE", response.toString());
                        MyContants.TOKEN = response.body().getToken();
                        Intent intent = new Intent(SignupActivity.this, ServiceSignupActivity.class);
                        intent.putExtra("from_page", MyContants.SIGNUP_PAGE);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                        Log.d("FAILURE", t.getMessage());

                    }
                });
    }

   /* private void sendNetworkRequest(Salon salon) {
        //create Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.2.192/cattocdi.api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        //get salon & call for the request
        SalonClient user = retrofit.create(SalonClient.class);
        Call<Salon> call = user.createAccount(salon);
        call.enqueue(new Callback<Salon>() {
            @Override
            public void onResponse(Call<Salon> call, Response<Salon> response) {
                Log.d("Success", response.message());
                //Toast.makeText(SignupActivity.this, "yeadd", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Salon> call, Throwable t) {
                Log.d("Failed", t.getMessage());
                //Toast.makeText(SignupActivity.this, "failled", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
