package com.pro.salon.cattocdi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pro.salon.cattocdi.adapter.CategoryRecycleViewAdapter;
import com.pro.salon.cattocdi.adapter.WorkingHourAdapter;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.WorkingHour;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkingHourSignupActivity extends AppCompatActivity {

    private WorkingHourAdapter workingHourAdapter;
    private RecyclerView rv;
    private List<WorkingHour> workingHourList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hour_signup);
        rv = findViewById(R.id.activity_working_hours_rv);
        workingHourList = new ArrayList<WorkingHour>();
        for (int i = 0; i < 7; i++) {
            workingHourList.add(new WorkingHour("8:00", "22:00", i));
        }
        workingHourAdapter = new WorkingHourAdapter(this, workingHourList);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(new WorkingHourAdapter(this, workingHourList));

        TextView saveTv = findViewById(R.id.activity_working_hours_signup_save_tv);
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updateWorkingHour("Bearer " + MyContants.TOKEN, workingHourList)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                workingHourAdapter = new WorkingHourAdapter(WorkingHourSignupActivity.this, workingHourList);
                                Log.d("RESPONSE", response.toString());
                                if(response.code() == 200){
                                    Intent intent = new Intent(WorkingHourSignupActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    showDialogLoginFail("Co lỗi xãy ra");
                                }


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("FAILED", t.getMessage());
                                showDialogLoginFail("Failed");
                            }
                        });

            }
        });
    }
    private void showDialogLoginFail(String text){
        final AlertDialog dialog = new AlertDialog.Builder(WorkingHourSignupActivity.this).create();
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
