package com.pro.salon.cattocdi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Promotion;
import com.pro.salon.cattocdi.models.enums.PromotionStatus;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;
import java.util.List;

public class SalonDetailPromotionRecycleView extends RecyclerView.Adapter<SalonDetailPromotionRecycleView.PromotionViewHolder>{

    private Context context;
    private List<Promotion> promotions;

    public SalonDetailPromotionRecycleView(Context context, List<Promotion> promotions) {
        this.context = context;
        this.promotions = promotions;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_salon_detail_promotion_recycle_view, viewGroup, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PromotionViewHolder promotionViewHolder, int i) {

            Promotion promotion = promotions.get(i);

                promotionViewHolder.tvDate.setText(promotion.getStartToEndstr());
                promotionViewHolder.tvDiscount.setText(promotion.getDiscount() + "%");
                promotionViewHolder.tvDescription.setText(promotion.getDescription() != null ? promotion.getDescription() : "");
            if(promotion.getStatus() == PromotionStatus.CANCEL.getStatus()){
                promotionViewHolder.icState.setImageResource(R.drawable.ic_stop);
            }




    }


    @Override
    public int getItemCount() {
        if(promotions == null) return 0;
        return promotions.size();
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder{
        public View item;
        public RelativeLayout headerRl;
        public ExpandableLayout expandPromotion;
        public TextView tvDiscount, tvDescription, tvDate;
        public ImageView icState;
        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView;
            this.headerRl = itemView.findViewById(R.id.frame_promotion_title);
          //  this.expandPromotion = itemView.findViewById(R.id.promotion_expand_layout);
//            this.icExpand = itemView.findViewById(R.id.promotion_ic_expand);
            this.tvDate = itemView.findViewById(R.id.promotion_date);
            this.tvDescription = itemView.findViewById(R.id.promotion_description);
            this.tvDiscount = itemView.findViewById(R.id.promotion_discount_tv);
            this.icState = itemView.findViewById(R.id.promotion_state_iv);
        }
    }
}
