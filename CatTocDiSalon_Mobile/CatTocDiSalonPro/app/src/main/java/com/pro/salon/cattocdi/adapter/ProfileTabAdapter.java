package com.pro.salon.cattocdi.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.pro.salon.cattocdi.fragments.ReviewsFragment;
import com.pro.salon.cattocdi.fragments.SalonDetailContactFragment;
import com.pro.salon.cattocdi.fragments.SalonDetailServiceFragment;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileTabAdapter extends FragmentPagerAdapter {
    private String title[] = {"DỊCH VỤ", "LIÊN HỆ", "ĐÁNH GIÁ"};
    private boolean isPreview = false;
    private Salon salon;

    public ProfileTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public ProfileTabAdapter(FragmentManager fm, boolean isPreview, Salon salon) {
        super(fm);
        this.isPreview = isPreview;
        this.salon = salon;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SalonDetailServiceFragment(isPreview, salon);
            case 1:
                return new SalonDetailContactFragment(isPreview, salon);
            case 2:
                return new ReviewsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
