package com.salon.cattocdi.fragements;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.CategoryAdapter;
import com.salon.cattocdi.adapters.SuggestServiceCardAdapter;
import com.salon.cattocdi.models.Category;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.CategoryApi;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.Model;
import com.salon.cattocdi.utils.MyContants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 */
public class ShowServiceFragment extends Fragment {

    private RecyclerView rcService;
    private TextView btnChoose;
    private List<Category> categories;
    CategoryAdapter adapter;

    public ShowServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_service, container, false);
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcService = view.findViewById(R.id.recyclerview);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcService.setLayoutManager(mLayoutManager);
        adapter = new CategoryAdapter(getActivity(), MyContants.SERVICE_CHECKBOX, new ArrayList<Service>(), categories);

        rcService.setAdapter(adapter);

        ApiClient.getInstance().create(CategoryApi.class)
                .getAllCategory("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        adapter = new CategoryAdapter(getActivity(), MyContants.SERVICE_CHECKBOX, new ArrayList<Service>(), response.body());
                        rcService.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {

                    }
                });

        btnChoose = view.findViewById(R.id.btn_get_service);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                List<Service> services = adapter.getCheckedList();
                String serviceName = "";
                for (Service service :
                        services) {
                    serviceName += service.getName() + ", ";
                }

                bundle.putString("service_name", serviceName);
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.activity_main_container_fl, searchFragment, null)
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }
//
//    private void loadData(RecyclerView rv) {
//        //Show RECYCLEVIEW
//        List<Model> items = new ArrayList<>();
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(mLayoutManager);
//        rv.setAdapter(new CategoryAdapter(getActivity(), MyContants.SERVICE_CHECKBOX, new ArrayList<Service>(), categories));
//
//    }

}
