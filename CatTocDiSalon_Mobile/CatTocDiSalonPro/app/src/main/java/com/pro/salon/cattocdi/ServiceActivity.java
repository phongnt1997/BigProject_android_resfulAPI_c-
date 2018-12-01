package com.pro.salon.cattocdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pro.salon.cattocdi.adapter.CategoryRecycleViewAdapter;
import com.pro.salon.cattocdi.adapter.ServiceRecycleViewAdapter;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView rvService;
    private TextView tvSave;
    private TextView btnAddService;
    private Salon salon;
    private List<Service> services;


    private ServiceRecycleViewAdapter serviceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        services = new ArrayList<Service>();
        rvService = findViewById(R.id.activity_service_rv);
        rvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //serviceAdapter = new ServiceRecycleViewAdapter(this, MyContants.MANAGER_SERVICE_PAGE, new ArrayList<Service>());
       rvService.setAdapter(new ServiceRecycleViewAdapter(this, MyContants.MANAGER_SERVICE_PAGE, services));
        ApiClient.getInstance()
                .create(SalonClient.class)
                .getService("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<List<Service>>() {
                    @Override
                    public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                        List<Service> services = response.body();
                        serviceAdapter = new ServiceRecycleViewAdapter(ServiceActivity.this,MyContants.MANAGER_SERVICE_PAGE, services);
                        rvService.setAdapter(serviceAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<Service>> call, Throwable t) {

                    }
                });

        tvSave = findViewById(R.id.activity_service_save_tv);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToProfileFragment();
            }
        });

        btnAddService = findViewById(R.id.activity_service_add_tv);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, ServiceSignupActivity.class);
                intent.putExtra("from_page", MyContants.MANAGER_SERVICE_PAGE);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToProfileFragment();
    }

    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
}
