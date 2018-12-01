package com.salon.cattocdi.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.SalonAppointmentActivity;
import com.salon.cattocdi.ServiceAppointmentBookActivity;
import com.salon.cattocdi.models.DateSlot;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SlotApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRecycleViewAdapter extends RecyclerView.Adapter<ServiceRecycleViewAdapter.ServiceViewHolder> {
    private Context context;
    private List<Service> services;
    private Salon salon;

    public ServiceRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public ServiceRecycleViewAdapter(Context context, List<Service> services, Salon salon) {
        this.context = context;
        this.services = services;
        this.salon = salon;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_salon_detail_service_recycle_view, viewGroup, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {

        final Service service = services.get(i);
        serviceViewHolder.tvServiceTitle.setText(service.getName());
        serviceViewHolder.tvPriceTime.setText(NumberFormat.getNumberInstance(Locale.US).format(service.getPrice()) + " vnd trong " + service.getMinutes() + " phút");


        serviceViewHolder.btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getInstance().create(SlotApi.class)
                        .getSlots("Bearer " + MyContants.TOKEN, salon.getSalonId(), service.getMinutes())
                        .enqueue(new Callback<List<DateSlot>>() {
                            @Override
                            public void onResponse(Call<List<DateSlot>> call, Response<List<DateSlot>> response) {
                                Intent intent = new Intent(context, SalonAppointmentActivity.class);
                                intent.putExtra("service_choosen", (Serializable) service);
                                intent.putExtra("slots", (Serializable) response.body());
                                intent.putExtra("salon", salon);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<DateSlot>> call, Throwable t) {
                                AlertError.showDialogLoginFail(context, "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        public View item;
        public Button btnAddService;
        public TextView tvServiceTitle, tvPriceTime;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView;
            this.btnAddService = itemView.findViewById(R.id.btn_add_service_to_appointment);
            this.tvPriceTime = itemView.findViewById(R.id.service_price_time);
            this.tvServiceTitle = itemView.findViewById(R.id.service_title);
        }
    }
}

