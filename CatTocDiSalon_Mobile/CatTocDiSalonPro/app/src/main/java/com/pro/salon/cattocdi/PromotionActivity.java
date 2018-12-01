package com.pro.salon.cattocdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pro.salon.cattocdi.adapter.PromotionAdapter;
import com.pro.salon.cattocdi.models.Promotion;
import com.pro.salon.cattocdi.models.Salon;

import java.io.Serializable;
import java.util.List;

public class PromotionActivity extends AppCompatActivity implements Serializable {

    private TextView tvOK, tvAdd;
    private RecyclerView rvPromotion;
    private Salon salon;
    private List<Promotion> promotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        Intent intent = getIntent();
        promotions = (List<Promotion>) intent.getSerializableExtra("promotion");
        tvOK = findViewById(R.id.activity_promotion_save_tv);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileFragment();
            }
        });

        rvPromotion = findViewById(R.id.promotion_activity_rv);
        rvPromotion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPromotion.setAdapter(new PromotionAdapter(this, promotions));

        tvAdd = findViewById(R.id.promotion_activity_add_tv);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromotionActivity.this, AddPromotionActivity.class);
                startActivity(intent);
            }
        });

    }

    private void goToProfileFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToProfileFragment();
    }
}
