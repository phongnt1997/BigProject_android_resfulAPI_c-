package com.pro.salon.cattocdi.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pro.salon.cattocdi.InformationActivity;
import com.pro.salon.cattocdi.R;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SalonDetailContactFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
    private GoogleMap map;
    private LatLng latLng;
    private SupportMapFragment supportMapFragment;
    private Button btManageInfo;
    private boolean isPreview;
    private TextView tvAdd, tvPhone, tvMail;
    private Salon salon;

    @SuppressLint("ValidFragment")
    public SalonDetailContactFragment(boolean isPreview, Salon salon) {
        // Required empty public constructor
        this.isPreview = isPreview;
        this.salon = salon;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon_detail_contact, container, false);
        tvAdd = view.findViewById(R.id.fg_contact_address);
        tvPhone = view.findViewById(R.id.fg_contact_phone);
        tvMail = view.findViewById(R.id.fg_contact_mail);
        String address = salon.getAddress();
        String mail = salon.getEmail();
        String phone = salon.getPhone();
        tvAdd.setText(address);
        tvMail.setText(mail);
        tvPhone.setText(phone);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.salon_detail_map);
        supportMapFragment.getMapAsync(this);

        btManageInfo = view.findViewById(R.id.salon_info_manage_btn);
        if(!isPreview){
            btManageInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InformationActivity.class);
                    intent.putExtra("salon", (Serializable) salon);
                    getActivity().startActivity(intent);
                }
            });
        }else {
            btManageInfo.setVisibility(View.GONE);
        }


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_styled));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(salon.getLatLng());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(salon.getLatLng(), 13));
        map.addMarker(markerOptions);
    }
    private void showDialogLoginFail(String text){
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("Có lỗi xảy ra");
        dialog.setMessage(text);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
