package com.pro.salon.cattocdi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.salon.cattocdi.PromotionDetailActivity;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Promotion;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.enums.PromotionStatus;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> implements Serializable{

    private Context context;
    private List<Promotion> promotions;

    public PromotionAdapter(Context context, List<Promotion> promotions) {
        this.context = context;
        this.promotions = promotions;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_item, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, final int position) {
        //just for test
       /* if(position > 1){
            holder.icState.setImageResource(R.drawable.ic_stop);
        }*/
        //salon.getPromotions().get(position).getStartPeriod();
       holder.tvDate.setText(promotions.get(position).getStartToEndstr());
       holder.tvDiscount.setText(String.valueOf(promotions.get(position).getDiscount()) + "%");
       holder.tvName.setText(promotions.get(position).getDescription());
       if(Calendar.getInstance().getTimeInMillis() > promotions.get(position).getEndPeriod().getTime()
               || promotions.get(position).getStatus() == PromotionStatus.CANCEL.getStatus()){
           holder.icState.setImageResource(R.drawable.ic_stop);
       }


       //if(Calendar.getInstance().getTimeInMillis() >= salon.getPromotions().get(position).getStartPeriod())

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PromotionDetailActivity.class);
              intent.putExtra("promotion", (Serializable) promotions.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(promotions == null) return 0;
        return promotions.size();
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder{
        private View item;
        private ImageView icState;
        private TextView tvDiscount, tvDate, tvName;
        public PromotionViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            tvDiscount = itemView.findViewById(R.id.promotion_discount_tv);
            tvDate = itemView.findViewById(R.id.promotion_date);
            tvName = itemView.findViewById(R.id.promotion_detail_name);
            icState = itemView.findViewById(R.id.promotion_state_iv);
        }
    }
}
