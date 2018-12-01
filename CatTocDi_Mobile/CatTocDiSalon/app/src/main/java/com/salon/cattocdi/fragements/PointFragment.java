package com.salon.cattocdi.fragements;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salon.cattocdi.R;
import com.salon.cattocdi.adapters.FragementAppointmentTestAdapter;
import com.salon.cattocdi.adapters.FragementUserPointAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class PointFragment extends Fragment {

RecyclerView explainPoint;

    public PointFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        /*explainPoint = view.findViewById(R.id.fg_explain_point);
        testRecycleViewAdapter(explainPoint);*/
        return view;
    }
  /*  private void testRecycleViewAdapter(RecyclerView rv){
        //Show RECYCLEVIEW
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        FragementUserPointAdapter adapter = new FragementUserPointAdapter();
        rv.setAdapter(adapter);
    }*/


}
