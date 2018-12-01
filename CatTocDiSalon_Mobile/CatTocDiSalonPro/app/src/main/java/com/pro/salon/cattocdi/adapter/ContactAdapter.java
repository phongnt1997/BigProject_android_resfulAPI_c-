package com.pro.salon.cattocdi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.pro.salon.cattocdi.ContactDetailActivity;
import com.pro.salon.cattocdi.LoginActivity;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<Customer> customers;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    public ContactAdapter(Context context, List<Customer> list) {
        this.context = context;
        this.customers = list;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {

        final Customer currentCustomer = customers.get(position);
        holder.tvName.setText(currentCustomer.getFullName());
        String symbol1 = Character.toString(currentCustomer.getFirstname().charAt(0));
        holder.tvSymbol.setText(symbol1.toUpperCase());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getInstance().create(SalonClient.class)
                        .getCustomerDetail("Bearer " + MyContants.TOKEN, currentCustomer.getId())
                        .enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                if (response != null && response.code() == 200) {
                                    if (response.body() != null) {
                                        Intent intent = new Intent(context, ContactDetailActivity.class);
                                        intent.putExtra("customer", (Serializable) response.body());
                                        context.startActivity(intent);
                                    } else {
                                        showDialogLoginFail("Không lấy được thông tin khách hàng, vui lòng thử lại");
                                    }
                                } else {
                                    showDialogLoginFail("Có lỗi xảy ra vui lòng thử lại");
                                }
                            }

                            @Override
                            public void onFailure(Call<Customer> call, Throwable t) {
                                showDialogLoginFail("Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                            }
                        });
            }
        });
    }

    private void showDialogLoginFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Không chính xác");
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
        return customers != null ? customers.size() : 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public View item;
        public TextView tvSymbol, tvName;

        public ContactViewHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            this.tvSymbol = itemView.findViewById(R.id.contact_item_symbol);
            this.tvName = itemView.findViewById(R.id.contact_item_name);
        }
    }
}
