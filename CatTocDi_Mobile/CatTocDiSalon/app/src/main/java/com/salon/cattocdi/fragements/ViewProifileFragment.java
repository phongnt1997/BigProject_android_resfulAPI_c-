package com.salon.cattocdi.fragements;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.salon.cattocdi.EditProfileActivity;
import com.salon.cattocdi.R;
import com.salon.cattocdi.utils.MyContants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class ViewProifileFragment extends Fragment {

    Button btnEditProfile;
    Context context;

    public ViewProifileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_proifile, container, false);

        TextView tvFirstName = view.findViewById(R.id.fg_view_profile_firstname_tv);
        TextView tvLastName = view.findViewById(R.id.fg_view_profile_lastname_tv);
        TextView tvName = view.findViewById(R.id.fg_view_profile_name_tv);
        TextView tvPhone = view.findViewById(R.id.fg_view_profile_phone_tv);
        TextView tvEmail = view.findViewById(R.id.fg_view_profile_email_tv);

        tvName.setText(MyContants.Customer.getName());
        tvFirstName.setText(MyContants.Customer.getName());
        tvLastName.setText(MyContants.Customer.getLastname());
        tvPhone.setText(MyContants.Customer.getPhone());
        tvEmail.setText(MyContants.Customer.getEmai());

        btnEditProfile = (Button) view.findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }


}
