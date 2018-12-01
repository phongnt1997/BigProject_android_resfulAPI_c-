package com.pro.salon.cattocdi.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pro.salon.cattocdi.LoginActivity;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.adapter.ContactAdapter;
import com.pro.salon.cattocdi.models.Customer;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private RecyclerView rvContactList;
    private EditText etSearch;
    private List<Customer> customers;

    public ClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        loadAllContact();
        rvContactList = view.findViewById(R.id.fg_client_contact_rv);
        rvContactList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
       rvContactList.setAdapter(new ContactAdapter(getActivity(), null));

        etSearch = view.findViewById(R.id.fg_client_search_et);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void loadAllContact(){
        ApiClient.getInstance().create(SalonClient.class)
                .getAllCustomer("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if(response != null && response.code() == 200){
                            customers = response.body();
                            rvContactList.setAdapter(new ContactAdapter(getActivity(), customers));
                        }else{
                            showDialogLoginFail("Có lỗi xảy ra vui lòng thử lại");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        showDialogLoginFail("Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                    }
                });
    }

    private void showDialogLoginFail(String text){
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
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

    private void search(String value){

        if( customers != null ){
            String[] values = value.trim().split(" ");
             ArrayList<Customer> result = new ArrayList<>();
            for (Customer cutomer :
                    customers) {
                for (String item :
                        values) {
                    if(cutomer.getFullName().toLowerCase().contains(item) || cutomer.getPhone().contains(item)){
                        result.add(cutomer);
                        break;
                    }
                }
            }

            rvContactList.setAdapter(new ContactAdapter(getActivity(), result));

        }
    }
}
