package com.pro.salon.cattocdi.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.adapter.AppointmentAdapter;
import com.pro.salon.cattocdi.models.Appointment;

import java.util.ArrayList;
import java.util.List;

/**

 */
@SuppressLint("ValidFragment")
public class HistoryAppointmentFragment extends Fragment {
    private RecyclerView rvAppointment;
    private List<Appointment> appointments;
    private EditText etSearch;


    @SuppressLint("ValidFragment")
    public HistoryAppointmentFragment(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_appoinment, container, false);
        rvAppointment = view.findViewById(R.id.fg_home_appointment_rv);
        rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvAppointment.setAdapter(new AppointmentAdapter(getActivity(), appointments));

        etSearch = view.findViewById(R.id.fg_home_phone_et);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void search(String value) {

        if (appointments != null) {
            String[] values = value.trim().split(" ");
            ArrayList<Appointment> result = new ArrayList<>();
            for (Appointment appointment :
                    appointments) {
                for (String item :
                        values) {
                    if (appointment.getCustomer().getFullName().toLowerCase().contains(item) || appointment.getCustomer().getPhone().contains(item)) {
                        result.add(appointment);
                        break;
                    }
                }
            }

            rvAppointment.setAdapter(new AppointmentAdapter(getActivity(), result));

        }
    }

}
