package com.pro.salon.cattocdi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPromotionActivity extends AppCompatActivity {

    private TextView tvSave;
    private EditText edtDes, edtStartTime, edtEndTime, edtDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);

        edtDes = findViewById(R.id.activity_promotion_description);
        edtStartTime = findViewById(R.id.activity_promotion_start_time);
        edtEndTime = findViewById(R.id.activity_promotion_end_time);
        edtDiscount = findViewById(R.id.activity_promotion_discount);



        final DatePickerDialog datePicker = new DatePickerDialog(AddPromotionActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtStartTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }

        }, Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth() - 1, Calendar.getInstance().getTime().getDate());
        final DatePickerDialog datePickerEnd = new DatePickerDialog(AddPromotionActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtEndTime.setText(dayOfMonth  + "-" + (monthOfYear + 1) + "-" + year);
            }

        }, Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth() - 1, Calendar.getInstance().getTime().getDate());
        datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        edtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        edtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long start = 0;
                try {
                    start = new SimpleDateFormat("dd-MM-yyyy").parse(edtStartTime.getText().toString()).getTime();
                    datePickerEnd.getDatePicker().setMinDate(start + 24*60*60*1000);
                    datePickerEnd.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
        tvSave = findViewById(R.id.add_promotion_save_tv);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataStart = sdf.parse(edtStartTime.getText().toString());
                    Date dataEnd = sdf.parse(edtEndTime.getText().toString());
                    String formattedStart = output.format(dataStart);
                    String formattedEnd = output.format(dataEnd);

                    ApiClient.getInstance()
                            .create(SalonClient.class)
                            .createPromotion("Bearer " + MyContants.TOKEN,
                                    formattedStart, formattedEnd,
                                    Integer.parseInt(edtDiscount.getText().toString()),
                                    edtDes.getText().toString())
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.code() == 200){
                                        goToProfileFragment();
                                    }
                                    else {
                                        AlertDialog alertDialog = new AlertDialog.Builder(AddPromotionActivity.this).create();
                                        alertDialog.setMessage("Đã có chương trình khuyến mãi diễn ra trong thời gian bạn chọn");
                                        alertDialog.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    AlertError.showDialogLoginFail(AddPromotionActivity.this, "Có lỗi xảy ra vui lòng xem lại kết nối");
                                }
                            });

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
}
