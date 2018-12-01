package com.pro.salon.cattocdi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Response;
import com.pro.salon.cattocdi.models.Account;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnSignup ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.login_activity_login_btn);
        btnSignup = findViewById(R.id.login_activity_register_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkLogin(){

        EditText etUsername = findViewById(R.id.login_activity_username_et);
        EditText etPassword= findViewById(R.id.login_activity_password_et);
        //String grant_type = "password";
        ApiClient.getInstance()
                .create(SalonClient.class)
                .login(etUsername.getText().toString(), etPassword.getText().toString(), "password")
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, retrofit2.Response<Account> response) {
                        Log.d("RESPONSE", response.toString());
                        if (response.code() == 400){
                            showDialogLoginFail("Failed Login");
                        }else{
                            MyContants.TOKEN = response.body().getToken();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                        Log.d("FAILURE", t.getMessage());
                        showDialogLoginFail("Failed");
                    }
                });


    }


    private void showDialogLoginFail(String text){
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
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


}
