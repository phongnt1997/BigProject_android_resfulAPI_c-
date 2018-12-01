package com.salon.cattocdi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.salon.cattocdi.R;

public class FragementUserPointAdapter extends RecyclerView.Adapter<FragementUserPointAdapter.AppointmentCardViewHolder> {

    @NonNull
    @Override
    public AppointmentCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_item_point_explain, viewGroup, false);
        return new AppointmentCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentCardViewHolder viewHolder, int i) {
//        viewHolder.tvName.setText("Salon " + i);
//        viewHolder.tvAddress.setText("abc " + i + i + i);
//        viewHolder.tvServices.setText("Cắt, uống, nhuộm");
//        viewHolder.tvTime.setText("Thứ 2 1/10/2018, 3:00PM");
//        viewHolder.tvStylist.setText("Tran Van A");

//        viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class AppointmentCardViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvAddress, tvServices, tvStylist, tvTime;
        public ImageView img;
        public Button btnCancel;
        public View item;

        public AppointmentCardViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvName = itemView.findViewById(R.id.fg_appointment_name_tv);
//            tvAddress = itemView.findViewById(R.id.fg_appointment_address_tv);
//            tvServices = itemView.findViewById(R.id.fg_appointment_services_tv);
//            tvStylist = itemView.findViewById(R.id.fg_appointment_stylist_tv);
//            tvTime = itemView.findViewById(R.id.fg_appointment_time_tv);
//            img = itemView.findViewById(R.id.fg_appointment_iv);
//            btnCancel = itemView.findViewById(R.id.fg_appointment_cancel_btn);
            item = itemView;
        }

    }
}
