package com.salon.cattocdi.fragements;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    private RecyclerView rvNew, rvRating, rvSale;
//    private EditText etSearch;
//    private TextView voucherSeeAllTv, newSeeAllTv;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        loadAllSalon(view);
        return view;
    }

    private void loadAllSalon(final View view){
        ApiClient.getInstance().create(SalonApi.class)
                .getAllSalon("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<List<Salon>>() {
                    @Override
                    public void onResponse(Call<List<Salon>> call, Response<List<Salon>> response) {
                        if(response.body() != null ){
                            MyContants.SalonList = parseToMap(response.body());
                        }
                        ViewPager viewPager = view.findViewById(R.id.detail_pager);
                        setupViewPager(viewPager);
                        // Set Tabs inside Toolbar
                        TabLayout tabs = view.findViewById(R.id.detail_tab_layout);
                        tabs.setupWithViewPager(viewPager);
                    }

                    @Override
                    public void onFailure(Call<List<Salon>> call, Throwable t) {
                        Log.d("FAIL_GET", t.getMessage());
                    }
                });
    }

    private HashMap<Integer, Salon> parseToMap(List<Salon> salons){
        HashMap<Integer, Salon> map = new HashMap<>();
        if(salons != null && salons.size() > 0){
            for (Salon salon :
                    salons) {
                map.put(salon.getSalonId(), salon);
            }
            return map;
        }
        return map;
    }

// Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeMapFragment(), "Bản đồ");
        adapter.addFragment(new HomeListFragment(), "Danh sách");
        viewPager.setAdapter(adapter);

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
