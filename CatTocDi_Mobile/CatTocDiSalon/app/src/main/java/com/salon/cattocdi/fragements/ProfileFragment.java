package com.salon.cattocdi.fragements;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salon.cattocdi.EditProfileActivity;
import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Customer;
import com.salon.cattocdi.requests.AccountApi;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.utils.MyContants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView editProfile, showLike, helpTv, showPoint, showHistory, tvName;
    //TextView logout;
    private Context context;
    private Customer customer;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity();
        loadProfile();
        tvName = view.findViewById(R.id.fg_profile_name_tv);

        editProfile = view.findViewById(R.id.edit_information_tv);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProifileFragment viewProifileFragment = new ViewProifileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fg_profile, viewProifileFragment, null).addToBackStack(null).commit();
            }


        });
//        showLike = view.findViewById(R.id.show_top_tv);
//        showLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowTopFragment showTopFragment = new ShowTopFragment();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fg_profile, showTopFragment, null).addToBackStack(null).commit();
//            }
//        });

        helpTv = view.findViewById(R.id.help_tv);
        helpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFragment helpFragment = new HelpFragment();
                getFragmentManager()
                        .beginTransaction().replace(R.id.fg_profile, helpFragment, null).addToBackStack(null).commit();
            }
        });
//        showPoint = view.findViewById(R.id.show_user_point);
//        showPoint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               PointFragment pointFragment = new PointFragment();
//               getFragmentManager().beginTransaction().replace(R.id.fg_profile, pointFragment, null).addToBackStack(null).commit();
//            }
//        });
        showHistory = view.findViewById(R.id.fg_show_history);
        showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                getFragmentManager().beginTransaction().replace(R.id.activity_main_container_fl, appointmentFragment, null)
                        .addToBackStack(null).commit();
                BottomNavigationView navigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
                navigationView.getMenu().getItem(1).setChecked(true);
            }
        });

        return view;
    }

    private void loadProfile() {
        ApiClient.getInstance().create(AccountApi.class)
                .getProfile("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        customer = response.body();
                        MyContants.Customer = customer;
                        tvName.setText(MyContants.Customer.getName());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
    }

}
