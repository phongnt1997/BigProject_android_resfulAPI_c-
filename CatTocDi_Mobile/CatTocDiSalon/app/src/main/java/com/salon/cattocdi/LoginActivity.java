package com.salon.cattocdi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.salon.cattocdi.models.Account;
import com.salon.cattocdi.models.Customer;
import com.salon.cattocdi.models.ResponseMessage;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.AccountApi;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private Button btnLogin;
    private TextView tvRegister, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        btnLogin = findViewById(R.id.login_activity_login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        tvRegister = findViewById(R.id.login_activity_register_tv);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkLogin() {
        boolean result = false;
        EditText etPhone = findViewById(R.id.login_activity_phone_et);
        EditText etPassword = findViewById(R.id.login_activity_password_et);

        ApiClient.getInstance()
                .create(AccountApi.class)
                .login(etPhone.getText().toString(), etPassword.getText().toString(), "password")
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if (response.code() == 200) {
                            MyContants.TOKEN = response.body().getToken();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            AlertError.showDialogLoginFail(LoginActivity.this, "Số điện thoại hoặc mật khẩu không chính xác");
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        AlertError.showDialogLoginFail(LoginActivity.this, "Có lỗi xảy ra vui lòng đăng nhập lại");
                    }
                });

        return result;
    }




//    private void sendRequest(){
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://192.168.0.120/cattocdi.api/token";
//        StringRequest request = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.d("RESPONSE", response);
//                }
//        },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("RESPONSE", error.getMessage());
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("username","tiendat@gmail.com");
//                param.put("password","Test@123");
//                param.put("grant_type","password");
//                return param;
//            }
//        };
//        queue.add(request);
//    }
}
