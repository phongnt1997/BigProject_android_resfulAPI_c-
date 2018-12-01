package com.salon.cattocdi.fragements;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.SalonAdapter;
import com.salon.cattocdi.utils.MyContants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class ShowTopFragment extends Fragment {
    RecyclerView rvTopUpcoming;


    public ShowTopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_top, container, false);
        rvTopUpcoming = view.findViewById(R.id.fg_show_top_rv_upcoming);
        testRecycleViewAdapter(rvTopUpcoming);
        return view;
    }

    private void testRecycleViewAdapter(RecyclerView rv){
        //Show RECYCLEVIEW
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
//        FragmentShowTopRecycleViewAdapter adapter = new FragmentShowTopRecycleViewAdapter();
        rv.setAdapter(new SalonAdapter(MyContants.RV_ITEM_NORMAL, getActivity()));
    }

}
