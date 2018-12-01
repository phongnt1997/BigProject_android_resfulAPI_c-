package com.pro.salon.cattocdi.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.ServiceSignupActivity;
import com.pro.salon.cattocdi.SignupActivity;
import com.pro.salon.cattocdi.WorkingHourSignupActivity;
import com.pro.salon.cattocdi.models.Account;
import com.pro.salon.cattocdi.models.Service;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceSignupRecycleViewAdapter extends RecyclerView.Adapter<ServiceSignupRecycleViewAdapter.ServiceSignupViewHolder> {
    private Context context;
    private List<Service> serviceList;
    private int serviceId;
    TextView serviceNameDialog;

    public ServiceSignupRecycleViewAdapter(Context context, List<Service> services) {
        this.context = context;
        this.serviceList = services;
    }


    @Override
    public ServiceSignupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_service_signup_recycle_view, parent, false);
        return new ServiceSignupRecycleViewAdapter.ServiceSignupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ServiceSignupViewHolder holder, final int position) {
        if (serviceList != null) {
            holder.serviceTitle.setText(serviceList.get(position).getName());
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    serviceId = serviceList.get(position).getServiceId();
                    System.out.println(serviceId);
                    if (holder.addBtn.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_add).getConstantState())) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.fragment_service_update_dialog);
                        // holder.serviceId.setText(serviceList.get(position).getServiceId());
                        dialog.setTitle("Thêm dịch vụ");
                        serviceNameDialog = dialog.findViewById(R.id.service_name);
                        serviceNameDialog.setText(serviceList.get(position).getName());
                        String[] durations = context.getResources().getStringArray(R.array.service_duration_array);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, durations);
                        Spinner spinner = dialog.findViewById(R.id.fragment_service_update_durations_spinner);
                        spinner.setAdapter(adapter);
                        dialog.show();

                        // Set on click for save service
                        TextView dialogSaveBtn = dialog.findViewById(R.id.fragment_service_update_save_tv);
                        dialogSaveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView priceTxt = dialog.findViewById(R.id.fragment_service_update_price_txt);
                                Spinner spinnerDetail = dialog.findViewById(R.id.fragment_service_update_durations_spinner);
                                String price = priceTxt.getText().toString();
                                String duration = spinnerDetail.getSelectedItem().toString();


                                if (!price.isEmpty()) {
                                    // Set service description for service
                                    //String detailSrc = "Giá: " + price + " - " + "Thời gian: " + duration;
                                    ApiClient.getInstance()
                                            .create(SalonClient.class)
                                            .updateServices("Bearer " + MyContants.TOKEN, serviceList.get(position).getSalonServiceId(),
                                                    serviceList.get(position).getServiceId(),
                                                    Double.parseDouble(price), Integer.parseInt(duration))
                                            .enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    Log.d("RESPONSE", response.toString());
                                                    if (response.code() == 400) {
                                                        showDialogFail("Cannot update");
                                                    } else {
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Log.d("FAILED", t.getMessage());
                                                }
                                            });
                                    holder.servicePrice.setText("Giá: " + price);
                                    //holder.serviceId.setText(serviceId);
                                    holder.serviceDuration.setText("Thời gian: " + duration);
                                    holder.addBtn.setImageResource(R.drawable.ic_add_white);
                                    holder.servicePrice.setVisibility(View.VISIBLE);
                                    holder.serviceDuration.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                }


                            }
                        });
                        // Set on click for cancel service;
                        TextView dialogCancelBtn = dialog.findViewById(R.id.fragment_service_update_cancel_tv);
                        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    } else {
                        // Remove
                        holder.serviceDuration.setText("");
                        holder.servicePrice.setText("");
                        holder.serviceDuration.setVisibility(View.GONE);
                        holder.servicePrice.setVisibility(View.GONE);
                        holder.addBtn.setImageResource(R.drawable.ic_add);
                    }
                    return false;
                }
            });
        } else {
            holder.serviceTitle.setText("Chọn các danh mục để thêm dịch vụ");
            holder.addBtn.setVisibility(View.GONE);
        }
    }


    private void showDialogFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Lỗi");
        dialog.setMessage(text);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (serviceList == null) return 0;
        return serviceList.size();
    }

    public class ServiceSignupViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceTitle, serviceName;
        public TextView servicePrice, serviceDuration, serviceId;
        public ImageView addBtn;

        public ServiceSignupViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTitle = itemView.findViewById(R.id.service_signup_title);
            addBtn = itemView.findViewById(R.id.service_signup_add);
            servicePrice = itemView.findViewById(R.id.service_signup_price);
            serviceDuration = itemView.findViewById(R.id.service_signup_duration);
            serviceId = itemView.findViewById(R.id.service_signup_id);
        }
    }
}
