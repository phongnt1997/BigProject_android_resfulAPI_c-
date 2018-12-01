package com.pro.salon.cattocdi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pro.salon.cattocdi.AppointmentDetailActivity;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Appointment;
import com.pro.salon.cattocdi.models.enums.AppointmentStatus;
import com.pro.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private Context context;
    private List<Appointment> appointments;

    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_cart_view, parent, false);
        return new AppointmentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        final Appointment appointment = appointments.get(position);
        holder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(appointment.getstart()));
        holder.tvName.setText(appointment.getCustomer().getPhone());
        holder.tvTime.setText(appointment.getStartToEnd());
        holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentDetailActivity.class);
                intent.putExtra("from_page", MyContants.HOME_PAGE);
                intent.putExtra("appointment", (Serializable) appointment);
                intent.putExtra("customer", (Serializable) appointment.getCustomer());
                context.startActivity(intent);
            }
        });
        holder.tvStatus.setText(appointment.getCustomer().getFullName());
        if(appointment.getStatus() == AppointmentStatus.CANCEL.getStatus()) {
            holder.theTrueStatus.setVisibility(View.VISIBLE);
            holder.theTrueStatus.setText("Đã Hủy");
            holder.theTrueStatus.setTextColor(context.getResources().getColor(R.color.errorColor));
        } else if (appointment.getStatus() == AppointmentStatus.APPROVED.getStatus()) {
            holder.theTrueStatus.setVisibility(View.VISIBLE);
            holder.theTrueStatus.setText("Đã Xác Nhận");
            holder.theTrueStatus.setTextColor(context.getResources().getColor(R.color.approved));
        }else if(appointment.getstart().getTime()  + 15 * 60 * 1000  <= Calendar.getInstance().getTimeInMillis()) {
            holder.theTrueStatus.setVisibility(View.VISIBLE);
            holder.theTrueStatus.setText("Đã Quá Hạn");
            holder.theTrueStatus.setTextColor(context.getResources().getColor(R.color.textColorTint));
        }
        else {
            holder.theTrueStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return appointments != null ? appointments.size() : 0;
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        public View item;
        public TextView tvStatus, tvDate, tvName, tvTime, theTrueStatus;
        public RelativeLayout rl;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            tvStatus = itemView.findViewById(R.id.fg_appointment_upcomming_tv);
            tvDate = itemView.findViewById(R.id.fg_appointment_date_tv);
            tvName = itemView.findViewById(R.id.fg_appointment_customer_name);
            tvTime = itemView.findViewById(R.id.fg_appointment_time);
            rl = itemView.findViewById(R.id.fg_appointment_rv_item_rl);
            theTrueStatus = itemView.findViewById(R.id.status);
        }
    }


}
