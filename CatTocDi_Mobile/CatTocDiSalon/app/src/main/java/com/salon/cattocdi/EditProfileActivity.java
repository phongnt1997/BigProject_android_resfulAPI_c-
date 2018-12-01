package com.salon.cattocdi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salon.cattocdi.fragements.ProfileFragment;
import com.salon.cattocdi.requests.AccountApi;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.utils.MyContants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends Activity {
    private BottomNavigationView bottomNav;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        TextView tvName = findViewById(R.id.acitivty_edit_profile_name_tv);
        tvName.setText(MyContants.Customer.getName());

        final EditText etFirstName = findViewById(R.id.activity_edit_profile_firstname_et);
        final EditText etLastName = findViewById(R.id.activity_edit_profile_lastname_et);
        final EditText etPhone = findViewById(R.id.activity_edit_profile_phone_et);
        final EditText etEmail = findViewById(R.id.activity_edit_profile_email_et);

        etFirstName.setText(MyContants.Customer.getName());
        etLastName.setText(MyContants.Customer.getLastname());
        etPhone.setText(MyContants.Customer.getPhone());
        etEmail.setText(MyContants.Customer.getEmai());

        btnUpdate = findViewById(R.id.btn_update_profile);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyContants.Customer.setName(etFirstName.getText().toString());
                MyContants.Customer.setLastname(etLastName.getText().toString());
                MyContants.Customer.setPhone(etPhone.getText().toString());
                MyContants.Customer.setEmai(etEmail.getText().toString());


                ApiClient.getInstance().create(AccountApi.class)
                        .updateProfile("Bearer " + MyContants.TOKEN, MyContants.Customer)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200){
                                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                                    intent.putExtra("update", "update");
                                    Toast.makeText(EditProfileActivity.this,"Bạn đã thay đổi thông tin cá nhân thành công",Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });



            }
        });
    }


}
