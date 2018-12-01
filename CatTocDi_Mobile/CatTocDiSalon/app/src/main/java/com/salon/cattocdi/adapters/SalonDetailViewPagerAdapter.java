package com.salon.cattocdi.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.salon.cattocdi.fragements.ReviewsFragment;
import com.salon.cattocdi.fragements.SalonDetailContactFragment;
import com.salon.cattocdi.fragements.SalonDetailServiceFragment;
import com.salon.cattocdi.fragements.TestTabFragment;
import com.salon.cattocdi.models.Salon;

public class SalonDetailViewPagerAdapter extends FragmentPagerAdapter{
    private String title[] = {"DỊCH VỤ", "LIÊN HỆ", "ĐÁNH GIÁ"};
    private Salon salon;

    public SalonDetailViewPagerAdapter(FragmentManager fm, Salon salon) {
        super(fm);
        this.salon = salon;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SalonDetailServiceFragment(salon);
            case 1:
                return new SalonDetailContactFragment(salon);
            case 2:
                return new ReviewsFragment(salon);
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
