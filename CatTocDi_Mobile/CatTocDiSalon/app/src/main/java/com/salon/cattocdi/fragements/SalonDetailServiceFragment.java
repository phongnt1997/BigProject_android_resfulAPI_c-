package com.salon.cattocdi.fragements;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.CategoryAdapter;
import com.salon.cattocdi.adapters.SalonDetailPromotionRecycleView;
import com.salon.cattocdi.adapters.ServiceRecycleViewAdapter;
import com.salon.cattocdi.adapters.WorkingHourAdapter;
import com.salon.cattocdi.models.Category;
import com.salon.cattocdi.models.Promotion;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.models.Service;
import com.salon.cattocdi.utils.MyContants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalonDetailServiceFragment extends Fragment {

    private Salon salon;

    public SalonDetailServiceFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SalonDetailServiceFragment(Salon salon) {
        this.salon = salon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salon_detail_service, container, false);

        if (salon.getCategories() != null) {
            RecyclerView serviceRecycleView = view.findViewById(R.id.salon_service_recycle_view);
            serviceRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            serviceRecycleView.setAdapter(new CategoryAdapter(getActivity(), MyContants.SERVICE_ADD, salon.getCategories(), salon));
        }

        if (salon.getPromotions() != null) {
            RecyclerView promotionRecycleView = view.findViewById(R.id.salon_promotion_recycle_view);
            promotionRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            promotionRecycleView.setAdapter(new SalonDetailPromotionRecycleView(salon.getPromotions()));

        }

        if(salon.getWorkingHours() != null){
            RecyclerView workingHourRecycleView = view.findViewById(R.id.working_hour_rv);
            workingHourRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            workingHourRecycleView.setAdapter(new WorkingHourAdapter(salon.getWorkingHours()));
        }
        return view;
    }

}
