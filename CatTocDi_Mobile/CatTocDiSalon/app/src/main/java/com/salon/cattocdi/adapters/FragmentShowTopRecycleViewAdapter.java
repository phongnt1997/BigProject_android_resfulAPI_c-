package com.salon.cattocdi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.utils.MyContants;

public class FragmentShowTopRecycleViewAdapter extends RecyclerView.Adapter<FragmentShowTopRecycleViewAdapter.MyCardViewHolder>{

    @NonNull
    @Override
    public MyCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_top, viewGroup, false);
        return new MyCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardViewHolder myCardViewHolder, int i) {
        myCardViewHolder.salonRatingBar.setRating(4.6f);
        myCardViewHolder.salonTitle.setText("Cửa hàng " + (i + 1));
        myCardViewHolder.salonReviewsAmount.setText("("+ (i * 10) +")");
        myCardViewHolder.salonAddress.setText( i + i + i + " abc");
        myCardViewHolder.salonImage.setBackgroundResource(MyContants.SALON_IMAGE_IDS[i]);

        myCardViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyCardViewHolder extends RecyclerView.ViewHolder{

        public ImageView salonImage;
        public TextView salonTitle, salonAddress, salonReviewsAmount;
        public RatingBar salonRatingBar;
        public CardView item;

        public MyCardViewHolder(@NonNull View itemView) {
            super(itemView);
            salonImage = itemView.findViewById(R.id.fg_home_rv_item_img);
            salonTitle = itemView.findViewById(R.id.fg_home_rv_item_title_tv);
            salonAddress = itemView.findViewById(R.id.fg_home_rv_item_address_tv);
            salonReviewsAmount = itemView.findViewById(R.id.fg_home_rv_item_amount_review_tv);
            salonRatingBar = itemView.findViewById(R.id.fg_home_rv_item_rb);
            item = (CardView) itemView;
        }
    }
}
