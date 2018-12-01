package com.pro.salon.cattocdi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.AlertError;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity{
    private EditText edtSalonName, edtCapital, edtPhone, edtAddress, edtmail, edtLong, edtLat;
    private TextView tvOK;
    private Salon salon;
    private Button btnAddLocaion;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    List<Address> addressList;
    FusedLocationProviderClient mFusuedLocationClient;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        edtSalonName = findViewById(R.id.activity_info_salon_name);
        edtCapital = findViewById(R.id.activity_info_capacity);
        edtPhone = findViewById(R.id.activity_info_phone);
        edtAddress = findViewById(R.id.activity_info_address);
        //geocodeAddress(address.toString(), geocode);
        edtmail = findViewById(R.id.activity_info_mail);
        btnAddLocaion = findViewById(R.id.btn_get_location);
        mFusuedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1200);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        btnAddLocaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());

                    } else {
                        checkLocationPermission();
                    }
                }

            }
        });

        salon = (Salon)

                getIntent().

                        getSerializableExtra("salon");

        edtSalonName.setText(salon.getName());

        edtCapital.setText(String.valueOf(salon.getCapital()));

        edtAddress.setText(salon.getAddress());

        edtPhone.setText(salon.getPhone());

        edtmail.setText(salon.getEmail());


        tvOK = findViewById(R.id.activity_info_save_tv);
        tvOK.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updateProfile("Bearer " + MyContants.TOKEN, edtSalonName.getText().toString(),
                                edtAddress.getText().toString(), Integer.parseInt(edtCapital.getText().toString()),
                                edtPhone.getText().toString(), edtmail.getText().toString(), mLastLocation != null ? mLastLocation.getLongitude() : salon.getLongtitude(),mLastLocation != null ? mLastLocation.getLatitude() : salon.getLatitude())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.d("RESPONSE", response.toString());
                                if (response.code() == 200) {
                                    Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                                    intent.putExtra("fragment_id", 3);
                                    startActivity(intent);
                                } else {
                                    showDialogLoginFail("Có lỗi xảy ra. Vui lòng xem lại kết nối mạng");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("FAIL", t.getMessage());
                                showDialogLoginFail("Có lỗi xảy ra. Vui lòng xem lại kết nối mạng");
                            }
                        });
                goToProfileFragment();
            }
        });

    }

    private void goToProfileFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }

    private void showDialogLoginFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(InformationActivity.this).create();
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



    LocationCallback mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {

                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;

                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    if (address.getThoroughfare() == null || address.getThoroughfare().length() == 0) {
                        sb.append(address.getPremises()).append(", ");
                    } else {
                        sb.append(address.getThoroughfare()).append(", ");
                    }
                    sb.append(address.getSubAdminArea()).append(", ");
                    sb.append(address.getLocality());

                    edtAddress.setText(sb);

                } catch (Exception e) {
                    Log.d("IO", e.getMessage());
                }



            }


        }
    };

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Nedded")
                        .setMessage("This app needs the Location permissio, Please accept")
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 99);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                    }
                } else {
                    Toast.makeText(this, "permisssion denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



}
