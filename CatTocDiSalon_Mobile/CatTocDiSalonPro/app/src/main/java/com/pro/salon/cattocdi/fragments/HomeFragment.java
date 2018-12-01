package com.pro.salon.cattocdi.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.AppointmentListHome;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private EditText etSearch;
    private ViewPager viewPager;
    private TabLayout tabs;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.detail_pager);
        tabs = (TabLayout) view.findViewById(R.id.detail_tab_layout);
        loadAppointment();

        return view;
    }

    private void loadAppointment() {
        ApiClient.getInstance().create(SalonClient.class)
                .getAppointmentHome("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<AppointmentListHome>() {
                    @Override
                    public void onResponse(Call<AppointmentListHome> call, Response<AppointmentListHome> response) {
                        if (response != null && response.code() == 200) {
                            MyContants.CAPACITY = response.body().getCapacity();
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                            adapter.addFragment(new NextAppointmentFragment(response.body().getNextAppointments()), "Tiếp theo");
                            adapter.addFragment(new HistoryAppointmentFragment(response.body().getNotApproveAppointments()), "Lịch sử");
                            adapter.addFragment(new CancelAppointmentFragment(response.body().getCancelAppointments()), "Đã hủy");
                            viewPager.setAdapter(adapter);

                            tabs.setupWithViewPager(viewPager);
                        }else{
                            AlertError.showDialogLoginFail(getActivity(),"Có lỗi xảy ra. Vui lòng thử lại");
                        }

                    }

                    @Override
                    public void onFailure(Call<AppointmentListHome> call, Throwable t) {
                       // AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra. Vui lòng kiểm tra lại kết nối");
                    }
                });
    }


    // Add Fragments to Tabs
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//        adapter.addFragment(new NextAppointmentFragment(appointments), "Tiếp theo");
//        adapter.addFragment(new HistoryAppointmentFragment(appointments), "Chưa xác nhận");
//        viewPager.setAdapter(adapter);
//    }

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
