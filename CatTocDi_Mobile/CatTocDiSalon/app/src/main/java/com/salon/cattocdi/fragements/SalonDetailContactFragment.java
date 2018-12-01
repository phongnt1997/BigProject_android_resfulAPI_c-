package com.salon.cattocdi.fragements;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.salon.cattocdi.R;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.utils.MyContants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalonDetailContactFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
    private GoogleMap map;
    private SupportMapFragment supportMapFragment;
    private Salon salon;
    private TextView tvAddress, tvPhone, tvEmail;

    public SalonDetailContactFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SalonDetailContactFragment(Salon salon) {
        this.salon = salon;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon_detail_contact, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.salon_detail_map);
        supportMapFragment.getMapAsync(this);

        tvAddress = view.findViewById(R.id.contact_address);
        tvAddress.setText(salon.getAddress());

        tvPhone = view.findViewById(R.id.contact_phone);
        tvPhone.setText(salon.getPhone());

        tvEmail = view.findViewById(R.id.contact_email);
        tvEmail.setText(salon.getEmail());

        Button btnCall = view.findViewById(R.id.salon_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+salon.getPhone()));
                startActivity(intent);
            }
        });

        Button btnSms = view.findViewById(R.id.salon_sms);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:"+salon.getPhone());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "");
                startActivity(it);
            }
        });

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
}
