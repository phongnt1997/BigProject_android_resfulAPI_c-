package com.pro.salon.cattocdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.salon.cattocdi.adapter.CategoryRecycleViewAdapter;
import com.pro.salon.cattocdi.adapter.ServiceRecycleViewAdapter;
import com.pro.salon.cattocdi.adapter.ServiceSignupRecycleViewAdapter;
import com.pro.salon.cattocdi.fragments.SalonDetailServiceFragment;
import com.pro.salon.cattocdi.models.Category;
import com.pro.salon.cattocdi.models.ResponseMsg;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceSignupActivity extends AppCompatActivity {
    private RecyclerView rvCategory;
    private RecyclerView rvService;
    private TextView tvPrice;
    private TextView tvDuration, tvServiceId;
    private String serviceId;
    private int from_page = -1;

    private CategoryRecycleViewAdapter adapter;

    // String[] categoryList = { "Cắt tóc","Trẻ em","Nhuộm màu","Uốn và Duỗi","Phục hồi tóc","Massage","Nail","Dịch vụ khác"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_signup);


        rvService = findViewById(R.id.activity_service_signup_rv);
        rvCategory = findViewById(R.id.activity_category_signup_rv);
        rvService.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CategoryRecycleViewAdapter(ServiceSignupActivity.this, new ArrayList<Category >(), rvService);
        rvCategory.setLayoutManager(new LinearLayoutManager(ServiceSignupActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(adapter);
        ApiClient.getInstance()
                .create(SalonClient.class)
                .getCategoried("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        List<Category> listCategories = response.body();
                        adapter = new CategoryRecycleViewAdapter(ServiceSignupActivity.this, listCategories, rvService);
                        rvCategory.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                    }
                });
        Intent intent = getIntent();
        from_page = intent.getIntExtra("from_page", -1);

        TextView saveTv = findViewById(R.id.activity_service_save_tv);
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvServiceId = view.findViewById(R.id.service_signup_id);
                tvPrice = view.findViewById(R.id.service_signup_price);
                tvDuration = findViewById(R.id.service_signup_duration);
                // Change to Activity


                backToPrevious(from_page);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToPrevious(from_page);
    }

    private void backToPrevious(int from_page) {
        if (from_page == MyContants.SIGNUP_PAGE) {
            Intent intent = new Intent(ServiceSignupActivity.this, InformationSignupActivity.class);
            startActivity(intent);
        } else if (from_page == MyContants.MANAGER_SERVICE_PAGE) {
            goToProfileFragment();
            /*Intent intent = new Intent(ServiceSignupActivity.this, );
            startActivity(intent);*/
        }
    }
    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
}
