package com.pro.salon.cattocdi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailActivity extends AppCompatActivity {

    private TextView tvPrice, tvPriceUnit, tvDuration, tvDurationUnit, tvSave, tvDelete, tvServiceName, tvCategory;
    private EditText etPrice, etDuration;
    private int from_page, salonServiceId, serviceId, duration;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        Intent intent = getIntent();

        String sName = intent.getStringExtra("service_name");
        String sCategory = intent.getStringExtra("category_name");
        Double price = intent.getDoubleExtra("price", -1);
        duration = intent.getIntExtra("duration", -1);
       serviceId = intent.getIntExtra("service_id", -1);


        tvServiceName = findViewById(R.id.activity_detail_service_name);
        tvServiceName.setText(sName);
        tvCategory = findViewById(R.id.activity_detail_category_name);
        tvCategory.setText(sCategory);
        tvPrice = findViewById(R.id.service_detail_price_tv);
        tvPriceUnit = findViewById(R.id.service_detail_price_unit_tv);
        etPrice = findViewById(R.id.service_detail_price_et);
        etPrice.setText(price.toString());
        tvDuration = findViewById(R.id.service_detail_duration_tv);
        tvDurationUnit = findViewById(R.id.service_detail_duration_unit_tv);
        etDuration = findViewById(R.id.service_detail_duration_et);
        etDuration.setText(String.valueOf(duration));
        tvSave = findViewById(R.id.service_detail_save_tv);
        tvDelete = findViewById(R.id.service_detail_delete_tv);

        etPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tvPrice.setTextColor(Color.parseColor("#8d6aa1"));
                    tvPriceUnit.setTextColor(Color.parseColor("#8d6aa1"));
                }else{
                    tvPrice.setTextColor(Color.parseColor("#000000"));
                    tvPriceUnit.setTextColor(Color.parseColor("#808080"));
                }
            }
        });

        etDuration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tvDuration.setTextColor(Color.parseColor("#8d6aa1"));
                    tvDurationUnit.setTextColor(Color.parseColor("#8d6aa1"));
                }else{
                    tvDuration.setTextColor(Color.parseColor("#000000"));
                    tvDurationUnit.setTextColor(Color.parseColor("#808080"));
                }
            }
        });


        from_page = intent.getIntExtra("from_page", -1);
        salonServiceId = intent.getIntExtra("salon_service_id", -1);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = Double.parseDouble(etPrice.getText().toString());
                int avrgTime = Integer.parseInt(etDuration.getText().toString());
                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updateServices( "Bearer "+ MyContants.TOKEN,salonServiceId, serviceId,
                                price, avrgTime)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.d("RESPONSE", response.toString());
                                if(response.code() == 400){
                                    showDialogFail("Cannot update");
                                }else{
                                    backToPrevious(from_page);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("FAILED", t.getMessage());
                            }
                        });

            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ServiceDetailActivity.this);
                Button btnOk, btnCancel;
                TextView tvInfo;
                dialog.setContentView(R.layout.dialog_delete);
                btnOk = dialog.findViewById(R.id.dialog_ok);
                btnCancel = dialog.findViewById(R.id.dialog_cancel);
                tvInfo = dialog.findViewById(R.id.dialog_info);
                tvInfo.setText("Bạn có muốn xóa dịch vụ này?");
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiClient.getInstance()
                                .create(SalonClient.class)
                                .deleteService("Bearer " + MyContants.TOKEN, salonServiceId)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d("RESPONSE", response.toString());
                                        if(response.code() == 400){
                                            showDialogFail("Cannot delete");
                                        }else{
                                            backToPrevious(from_page);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("FAILED", t.getMessage());
                                        showDialogFail("Cannot delete");
                                    }
                                });
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

               // backToPrevious(from_page);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToPrevious(from_page);
    }

    private void backToPrevious(int from_page){
        if(from_page == MyContants.PROFILE_PAGE){
            goToProfileFragment();
        }else if(from_page == MyContants.MANAGER_SERVICE_PAGE){
            Intent intent = new Intent(ServiceDetailActivity.this, ServiceActivity.class);
            startActivity(intent);
        }
    }

    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
    private void showDialogFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(ServiceDetailActivity.this).create();
        dialog.setTitle("Lỗi");
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
