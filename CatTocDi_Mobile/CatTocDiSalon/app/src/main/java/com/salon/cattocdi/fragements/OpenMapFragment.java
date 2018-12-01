package com.salon.cattocdi.fragements;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.salon.cattocdi.MapsActivity;
import com.salon.cattocdi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenMapFragment extends Fragment {
FloatingActionButton btnOpenMap;

    public OpenMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_map, container, false);
        btnOpenMap = view.findViewById(R.id.btOpenMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
