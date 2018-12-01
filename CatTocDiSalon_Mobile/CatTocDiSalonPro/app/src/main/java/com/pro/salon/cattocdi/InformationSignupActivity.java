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
import android.text.TextUtils;
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
import com.pro.salon.cattocdi.models.Account;
import com.pro.salon.cattocdi.models.Salon;
import com.pro.salon.cattocdi.models.WorkingHour;
import com.pro.salon.cattocdi.service.ApiClient;
import com.pro.salon.cattocdi.service.SalonClient;
import com.pro.salon.cattocdi.utils.MyContants;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationSignupActivity extends AppCompatActivity {

    private EditText edtSalonName, edtCapital, edtPhone, edtAddress, edtmail;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    List<Address> addressList;
    FusedLocationProviderClient mFusuedLocationClient;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information_signup);
        TextView saveTv = findViewById(R.id.activity_info_signup_save_tv);
        edtSalonName = findViewById(R.id.signup_activity_salon_name_edt);
        edtCapital = findViewById(R.id.signup_activity_capacity_edt);
        edtPhone = findViewById(R.id.signup_activity_phone_edt);
        edtAddress = findViewById(R.id.signup_activity_address_edt);
        edtmail = findViewById(R.id.signup_activity_mail_edt);

        mFusuedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1200);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        ApiClient.getInstance()
                .create(SalonClient.class)
                .getSalonProfile("Bearer " + MyContants.TOKEN)
                .enqueue(new Callback<Salon>() {
                    @Override
                    public void onResponse(Call<Salon> call, Response<Salon> response) {
                        String salonName = response.body().getName();
                        edtSalonName.setText(salonName);
                        String address = response.body().getAddress();
                        edtAddress.setText(address);
                        String phone = response.body().getPhone();
                        edtPhone.setText(phone);
                        String email = response.body().getEmail();
                        edtmail.setText(email);
                    }

                    @Override
                    public void onFailure(Call<Salon> call, Throwable t) {

                    }
                });

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiClient.getInstance()
                        .create(SalonClient.class)
                        .updateProfile("Bearer " + MyContants.TOKEN, edtSalonName.getText().toString(),
                                edtAddress.getText().toString(), Integer.parseInt(edtCapital.getText().toString()),
                                edtPhone.getText().toString(), edtmail.getText().toString(), mLastLocation != null ? mLastLocation.getLongitude() : 0, mLastLocation != null ? mLastLocation.getLatitude() : 0)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.d("RESPONSE", response.toString());
                                if (response.code() == 200) {
                                    Intent intent = new Intent(InformationSignupActivity.this, WorkingHourSignupActivity.class);
                                    startActivity(intent);
                                } else {
                                    showDialogLoginFail("Có lỗi xảy ra. Vui lòng kiểm tra kết nối");

                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("FAIL", t.getMessage());
                                showDialogLoginFail("Có lỗi xảy ra. Vui lòng kiểm tra kết nối");
                            }
                        });


            }
        });
    }

    private void showDialogLoginFail(String text) {
        final AlertDialog dialog = new AlertDialog.Builder(InformationSignupActivity.this).create();
        dialog.setTitle("Không chính xác");
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


    public void clickToGetCurrentLocation(View view) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());

            } else {
                checkLocationPermission();
            }
        }
    }
}
