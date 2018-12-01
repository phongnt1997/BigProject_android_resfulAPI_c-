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

import com.pro.salon.cattocdi.adapter.WorkingHourAdapter;
import com.pro.salon.cattocdi.models.WorkingHour;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkingHoursActivity extends AppCompatActivity {

    private TextView tvSave;
    private WorkingHourAdapter workingHourAdapter;
    private RecyclerView rv;
    private List<WorkingHour> workingHourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
        Intent intent = getIntent();
        workingHourList = (List<WorkingHour>) intent.getSerializableExtra("workingHours");
        RecyclerView rv = findViewById(R.id.activity_working_hours_rv);
        if(workingHourList == null){
            workingHourList = new ArrayList<WorkingHour>();
            for (int i = 0; i < 7; i++) {
                workingHourList.add(new WorkingHour());
            }
        }

        workingHourAdapter = new WorkingHourAdapter(this, workingHourList);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //workingHourAdapter = new WorkingHourAdapter(this, workingHourList);
        rv.setAdapter(new WorkingHourAdapter(this, workingHourList));
        tvSave = findViewById(R.id.activity_working_hours_save_tv);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updateWorkingHour("Bearer " + MyContants.TOKEN,workingHourAdapter.getWorkingHour())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                workingHourAdapter = new WorkingHourAdapter(WorkingHoursActivity.this, workingHourList);
                                Log.d("RESPONSE", response.toString());
                                if(response.code() == 200){
                                    goToProfileFragment();
                                }
                               /* else{
                                    showDialogLoginFail("Có lỗi xảy ra vui lòng xem lại kết nối, hoặc xem lại thông tin đã nhập");
                                }*/


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("FAILED", t.getMessage());
                                showDialogLoginFail("Có lỗi xảy ra vui lòng xem lại kết nối");
                            }
                        });

            }
        });

    }
    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
    private void showDialogLoginFail(String text){
        final AlertDialog dialog = new AlertDialog.Builder(WorkingHoursActivity.this).create();
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
