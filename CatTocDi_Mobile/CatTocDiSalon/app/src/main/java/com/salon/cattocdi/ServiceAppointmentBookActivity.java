package com.salon.cattocdi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.salon.cattocdi.adapters.CategoryAdapter;
import com.salon.cattocdi.adapters.SuggestServiceCardAdapter;
import com.salon.cattocdi.models.Category;
import com.salon.cattocdi.models.DateSlot;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.AppointmentApi;
import com.salon.cattocdi.requests.SlotApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceAppointmentBookActivity extends Activity {

    private Salon salon;
    private List<Service> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_show_service);

        checkedList = (List<Service>) getIntent().getSerializableExtra("checked_list");
        salon = (Salon) getIntent().getSerializableExtra("salon");
        final RecyclerView rvService = findViewById(R.id.recyclerview);
        rvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final CategoryAdapter adapter = new CategoryAdapter(this, MyContants.SERVICE_CHECKBOX, checkedList, salon.getCategories());
        rvService.setAdapter(adapter);
        TextView tvChoose = findViewById(R.id.btn_get_service);
        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Service> checkedList = adapter.getCheckedList();

                ApiClient.getInstance().create(SlotApi.class)
                        .getSlots("Bearer " + MyContants.TOKEN, salon.getSalonId(), getTotalDuration(checkedList))
                        .enqueue(new Callback<List<DateSlot>>() {
                            @Override
                            public void onResponse(Call<List<DateSlot>> call, Response<List<DateSlot>> response) {
                                Intent intent = new Intent(ServiceAppointmentBookActivity.this, SalonAppointmentActivity.class);
                                intent.putExtra("checked_list", (Serializable) checkedList);
                                intent.putExtra("slots", (Serializable) response.body());
                                intent.putExtra("salon", salon);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<DateSlot>> call, Throwable t) {
                                AlertError.showDialogLoginFail(ServiceAppointmentBookActivity.this, "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                            }
                        });

            }
        });
    }

    private int getTotalDuration(List<Service> checkedList){
        int total = 0;
        if(checkedList != null){
            for (Service service :
                    checkedList) {
                total += service.getMinutes();
            }
        }
        return total;
    }

}
