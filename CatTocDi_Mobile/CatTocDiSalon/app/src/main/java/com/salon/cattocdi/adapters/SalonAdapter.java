package com.salon.cattocdi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.salon.cattocdi.R;
import com.salon.cattocdi.SalonAppointmentActivity;
import com.salon.cattocdi.SalonDetailActivity;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.salon.cattocdi.utils.MyContants.RV_ITEM_NORMAL;
import static com.salon.cattocdi.utils.MyContants.RV_ITEM_VOUCHER;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.MyCardViewHolder> {

    private int type;
    private Context context;
    private boolean isFavorite = false;
    private List<Salon> salons;

    public SalonAdapter(int type, Context context) {
        this.context = context;
        this.type = type;
    }

    public SalonAdapter(boolean isFavorite, int type, Context context) {
        this.context = context;
        this.type = type;
        this.isFavorite = isFavorite;
    }

    public SalonAdapter(int type, Context context, List<Salon> salons) {
        this.type = type;
        this.context = context;
        this.salons = salons;
    }

    @NonNull
    @Override
    public MyCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        if (type == MyContants.RV_ITEM_NORMAL) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_salon_rating, viewGroup, false);
        } else if (type == RV_ITEM_VOUCHER) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_salon_voucher, viewGroup, false);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_salon_rating, viewGroup, false);
        }
        return new MyCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCardViewHolder myCardViewHolder, final int i) {

//        myCardViewHolder.salonRatingBar.setRating(4.6f);
        if (type == RV_ITEM_NORMAL) {
            myCardViewHolder.salonTitle.setText(salons.get(i).getName());
            myCardViewHolder.salonAddress.setText(salons.get(i).getAddress());
            if(salons.get(i).getPromotion() == null){
                myCardViewHolder.tvDiscount.setVisibility(View.GONE);
            }else{
                if (salons.get(i).getPromotion().getDiscount() == 0) {
                    myCardViewHolder.tvDiscount.setVisibility(View.GONE);
                } else {
                    myCardViewHolder.tvDiscount.setText(salons.get(i).getPromotion().getDiscount() + "% OFF");
                }
            }

            myCardViewHolder.salonRatingBar.setRating(salons.get(i).getRatingNumber());
            if (myCardViewHolder.salonReviewsAmount != null) {
                myCardViewHolder.salonReviewsAmount.setText("(" + salons.get(i).getReviewsAmount() + ")");
            }
        }




        if(type == RV_ITEM_VOUCHER){
            myCardViewHolder.tvDiscount.setText(salons.get(i).getPromotion().getDiscount() + "% OFF");
            myCardViewHolder.tvDiscountDate.setText(salons.get(i).getPromotion().getStartDateStr() + " - " + salons.get(i).getPromotion().getEndDateStr());
            myCardViewHolder.tvDiscountContent.setText(salons.get(i).getPromotion().getDescription());
            myCardViewHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToSalonDetail(myCardViewHolder, i);
                }
            });
        }

        if(salons.get(i).getImageUrl() != null){
            Picasso.get().load(salons.get(i).getImageUrl()).into(myCardViewHolder.salonImage);
        }else{
                    myCardViewHolder.salonImage.setBackgroundResource(MyContants.SALON_IMAGE_IDS[0]);

        }

        if (type == RV_ITEM_NORMAL) {

            Button btnBook;
            btnBook = myCardViewHolder.item.findViewById(R.id.btn_book_service);
            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    goToSalonDetail(myCardViewHolder, i);

                }
            });
        }
    }

    private void goToSalonDetail(final MyCardViewHolder myCardViewHolder, int i){
        ApiClient.getInstance().create(SalonApi.class)
                .getSalonById("Bearer " + MyContants.TOKEN, salons.get(i).getSalonId())
                .enqueue(new Callback<Salon>() {
                    @Override
                    public void onResponse(Call<Salon> call, Response<Salon> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(context, SalonDetailActivity.class);
                            Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(
                                    myCardViewHolder.item, 0, 0, myCardViewHolder.item.getWidth(), myCardViewHolder.item.getHeight()).toBundle();
                            Salon salon = response.body();
                            intent.putExtra("salon", (Serializable) salon);
                            ActivityCompat.startActivity(context, intent, options);
                        } else {
                            AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng thử lại sau");
                        }

                    }

                    @Override
                    public void onFailure(Call<Salon> call, Throwable t) {
                        AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng kiểm tra lại kết nối mạng");
                    }
                });

    }

    @Override
    public int getItemCount() {
        if (salons == null) return 0;
        return salons.size();
    }

    public class MyCardViewHolder extends RecyclerView.ViewHolder {

        public ImageView salonImage;
        public TextView salonTitle, salonAddress, salonReviewsAmount;
        public RatingBar salonRatingBar;
        public CardView item;
        public ImageView icFavorite;
        public TextView tvDiscount, tvDiscountContent, tvDiscountDate;


        public MyCardViewHolder(@NonNull View itemView) {
            super(itemView);
            salonTitle = itemView.findViewById(R.id.fg_home_rv_item_title_tv);
            salonAddress = itemView.findViewById(R.id.fg_home_rv_item_address_tv);
            salonReviewsAmount = itemView.findViewById(R.id.fg_home_rv_item_amount_review_tv);
            salonRatingBar = itemView.findViewById(R.id.fg_home_rv_item_rb);
            salonImage = itemView.findViewById(R.id.fg_home_rv_item_img);
            icFavorite = itemView.findViewById(R.id.fg_home_rv_item_favorite_ic);
            tvDiscount = itemView.findViewById(R.id.rv_discount);
            tvDiscountContent = itemView.findViewById(R.id.rv_discount_content);
            tvDiscountDate = itemView.findViewById(R.id.rv_discount_date);
            item = (CardView) itemView;
        }
    }
}
