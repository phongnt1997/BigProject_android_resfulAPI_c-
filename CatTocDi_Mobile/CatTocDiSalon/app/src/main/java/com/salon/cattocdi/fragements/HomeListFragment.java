package com.salon.cattocdi.fragements;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.salon.cattocdi.ListSalonActivity;
import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.SalonAdapter;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.MyContants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeListFragment extends Fragment {

    private RecyclerView rvNew, rvRating, rvSale;
    private EditText etSearch;
    private TextView voucherSeeAllTv, newSeeAllTv;

    public HomeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentX
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        rvRating = view.findViewById(R.id.fg_home_rv_rating);
        rvSale = view.findViewById(R.id.fg_home_rv_sale);

        etSearch = view.findViewById(R.id.fg_home_search_et);

        voucherSeeAllTv = view.findViewById(R.id.fg_home_voucher_see_all_tv);

        //set layout
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSale.setLayoutManager(mLayoutManager);

        rvRating.setLayoutManager(new GridLayoutManager(getActivity(),1));

        testRecycleViewAdapter(rvRating, MyContants.RV_ITEM_NORMAL, new ArrayList<Salon>(MyContants.SalonList.values()));
        testRecycleViewAdapter(rvSale, MyContants.RV_ITEM_VOUCHER, MyContants.getPromotions());

        ViewCompat.setNestedScrollingEnabled(rvRating, false);

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();
                showFragment(searchFragment);
                BottomNavigationView navigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_nav);
                navigationView.getMenu().getItem(2).setChecked(true);
            }
        });


        voucherSeeAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListSalonActivity.class);
                Bundle option = ActivityOptionsCompat.makeScaleUpAnimation(voucherSeeAllTv,0,0,voucherSeeAllTv.getWidth(), voucherSeeAllTv.getLineHeight()).toBundle();
                intent.putExtra("title", "Khuyễn mãi");
                intent.putExtra("type", MyContants.RV_ITEM_VOUCHER);
                intent.putExtra("salon", new ArrayList<>(MyContants.getPromotions()));
                ActivityCompat.startActivity(getActivity(), intent, option);

            }
        });

//        loadAllSalon();
        return view;
    }

//
    private void testRecycleViewAdapter(RecyclerView rv, int type, List<Salon> salons){
        //Show RECYCLEVIEW
        rv.setItemAnimator(new DefaultItemAnimator());
        SalonAdapter adapter = new SalonAdapter(type, getActivity(),salons);
        rv.setAdapter(adapter);
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container_fl, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
