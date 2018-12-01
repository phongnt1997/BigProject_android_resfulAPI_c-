package com.pro.salon.cattocdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pro.salon.cattocdi.models.Promotion;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionDetailActivity extends AppCompatActivity {

    private TextView tvOK, tvStop, tvDes, tvDate, tvDiscount;
    private Promotion promotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);
        tvDes = findViewById(R.id.activity_promotion_detail_des);
        tvDiscount = findViewById(R.id.activity_promotion_detail_discount);
        tvDate = findViewById(R.id.activity_promotion_detail_date);

        Intent intent = getIntent();
        promotion = (Promotion) intent.getSerializableExtra("promotion");
        tvDate.setText(promotion.getStartToEndstr());
        tvDiscount.setText(String.valueOf(promotion.getDiscount()) + "%");
        tvDes.setText(promotion.getDescription());


        tvOK = findViewById(R.id.promotion_detail_save_tv);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPromotionActivity();
            }
        });

        tvStop = findViewById(R.id.service_detail_delete_tv);
        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updatePromotion("Bearer " + MyContants.TOKEN,promotion.getId()) //1: Cancel
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200){
                                    AlertError.showDialofSuccess(PromotionDetailActivity.this,"Bạn đã hủy thành công");
                                    goToPromotionActivity();
                                }if(response.code() == 400){
                                    AlertError.showDialofSuccess(PromotionDetailActivity.this,"Khuyến mãi này đang diễn ra. Không thể xóa");
                                }
                                else{
                                    AlertError.showDialogLoginFail(PromotionDetailActivity.this, "Có lỗi xảy ra vui lòng xem lại kết nối");
                                    Log.d("FAILED", "Failed Update");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                AlertError.showDialogLoginFail(PromotionDetailActivity.this, "Có lỗi xảy ra vui lòng xem lại kết nối");
                                Log.d("FAILED", "Failed Update");
                            }
                        });

            }
        });

    }
    private void goToPromotionActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
