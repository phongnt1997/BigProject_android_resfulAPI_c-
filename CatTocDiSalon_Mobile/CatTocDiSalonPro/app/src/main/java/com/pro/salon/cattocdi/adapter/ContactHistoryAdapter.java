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
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.models.enums.AppointmentStatus;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactHistoryAdapter extends RecyclerView.Adapter<ContactHistoryAdapter.AppointmentViewHolder> {

    private Context context;
    private int mode;
    private Customer customer;

    public ContactHistoryAdapter(Context context, int mode, Customer customer) {
        this.context = context;
        this.mode = mode;
        this.customer = customer;
    }


    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (mode) {
            case MyContants.APPOINTMENT_SMALL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_cart_view, parent, false);
                break;
            case MyContants.APPOINTMENT_FULL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_appointment_cart_view, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_appointment_cart_view, parent, false);
                break;
        }
        return new AppointmentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        final Appointment appointment = customer.getAppointments().get(position);

        if (mode == MyContants.APPOINTMENT_FULL) {
            holder.tvName.setText("");
            if (appointment.getend().getTime() < Calendar.getInstance().getTimeInMillis()) {
                holder.tvStatus.setText("Đã hoàn thành");
            }
            if (appointment.getstart().getTime() < Calendar.getInstance().getTimeInMillis()
                    && Calendar.getInstance().getTimeInMillis() < appointment.getend().getTime()) {
                holder.tvStatus.setText("Đang phục vụ");
            }

            if (appointment.getstart().getTime() > Calendar.getInstance().getTimeInMillis()) {
                holder.tvStatus.setText("Sắp tới");
            }

            if(appointment.getStatus() == AppointmentStatus.CANCEL.getStatus()){
                holder.tvStatus.setText("Đã hủy");
            }

            holder.tvDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(customer.getAppointments().get(position).getstart()));
            holder.tvTime.setText(appointment.getStartToEnd());
            //temp edit later
            final Intent intent = new Intent(context, AppointmentDetailActivity.class);

            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("customer", (Serializable) customer);
                    intent.putExtra("appointment", (Serializable) appointment);
                    intent.putExtra("from_page", MyContants.CLIENT_PAGE);
                    intent.putExtra("expired", 1);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return customer.getAppointments() != null ? customer.getAppointments().size() : 0;
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        public View item;
        public TextView tvStatus, tvDate, tvName, tvTime;
        public RelativeLayout rl;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            tvStatus = itemView.findViewById(R.id.fg_appointment_upcomming_tv);
            tvDate = itemView.findViewById(R.id.fg_appointment_date_tv);
            tvName = itemView.findViewById(R.id.fg_contact_history_customer_name);
            tvTime = itemView.findViewById(R.id.fg_appointment_time);
            rl = itemView.findViewById(R.id.fg_appointment_rv_item_rl);
        }
    }


}
