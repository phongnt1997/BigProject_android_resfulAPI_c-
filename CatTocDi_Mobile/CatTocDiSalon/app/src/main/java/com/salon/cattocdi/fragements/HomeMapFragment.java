package com.salon.cattocdi.fragements;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salon.cattocdi.MapsActivity;
import com.salon.cattocdi.R;
import com.salon.cattocdi.SalonDetailActivity;
import com.salon.cattocdi.models.Salon;
import com.salon.cattocdi.requests.ApiClient;
import com.salon.cattocdi.requests.SalonApi;
import com.salon.cattocdi.utils.AlertError;
import com.salon.cattocdi.utils.MyContants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationmMarker;
    FusedLocationProviderClient mFusuedLocationClient;
    Geocoder geocoder;
    List<LatLng> addressList;
    LayoutInflater mInflater;
    List<Salon> salons;

    public HomeMapFragment() {
        // Required empty public constructor
        this.salons = new ArrayList<>(MyContants.SalonList.values());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_map, container, false);

        mFusuedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.salons_map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_styled));
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1200);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusuedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper());
                mMap.setMyLocationEnabled(false);
                LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                List<String> providers = mLocationManager.getProviders(true);
                Location currentLocation = null;
                for (String provider : providers) {
                    Location l = mLocationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (currentLocation == null || l.getAccuracy() < currentLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        currentLocation = l;
                    }
                }
                if (currentLocation != null) {
                    LatLng currentPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(currentPosition)
                            .zoom(17f)
                            .build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate);
                    Marker myLocationMarker = createMarker(currentPosition);
                    myLocationMarker.setTag("Me");
                } else {
                    LatLng currentPosition = new LatLng(MyContants.LATITUDE_DEFAULT, MyContants.LONGTITUDE_DEFAULT);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(currentPosition)
                            .zoom(MyContants.ZOOM_DEFAULT)
                            .build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate);


                }

                makeMarker();

            } else {
                checkLocationPermission();
            }
        }
    }

    private Marker createMarker(LatLng location) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_my_location_arrow);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        MarkerOptions markerOptions = new MarkerOptions().position(location)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        return mMap.addMarker(markerOptions);
    }

    LocationCallback mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            Location location = locationResult.getLastLocation();
            if (location != null) {
                mLastLocation = location;
                if (mCurrLocationmMarker != null) {
                    mCurrLocationmMarker.remove();
                }
            }

        }
    };

    private void makeMarker() {
        Bitmap bm;
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (salons != null) {

            for (int i = 0; i < salons.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(salons.get(i).getLatLng());
//            builder.include(addressList.get(i));
                bm = createBitmapFromLayoutWithText(salons.get(i));
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bm));
                mCurrLocationmMarker = mMap.addMarker(markerOptions);
                mCurrLocationmMarker.setTag(salons.get(i).getSalonId());
            }
        }
    }

//    private void loadAllSalon(){
//        ApiClient.getInstance().create(SalonApi.class)
//                .getAllSalon("Bearer " + MyContants.TOKEN)
//                .enqueue(new Callback<List<Salon>>() {
//                    @Override
//                    public void onResponse(Call<List<Salon>> call, Response<List<Salon>> response) {
//                        if(response.body() != null ){
//                            salons = response.body();
//                        }else{
//                            salons = new ArrayList<>();
//                        }
//                        makeMarker();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Salon>> call, Throwable t) {
//                        Log.d("FAIL_GET", t.getMessage());
//                        salons = new ArrayList<>();
//                    }
//                });
//    }

    public Bitmap createBitmapFromLayoutWithText(Salon salon) {
        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout view = new LinearLayout(getActivity());
        View layout = mInflater.inflate(R.layout.info_window_marker, view, true);

        TextView tvDiscount = layout.findViewById(R.id.salon_image);
        if(salon.getPromotion() != null){
            tvDiscount.setText(salon.getPromotion().getDiscount() + "%");
        }

        TextView tvName = layout.findViewById(R.id.salon_name_map);
        tvName.setText(salon.getName());

        RatingBar rb = layout.findViewById(R.id.salon_rating_map);
        rb.setRating(salon.getRatingNumber());

        if (salon.isFull()) {
            tvDiscount.setBackgroundResource(R.drawable.ic_discount_map_disable);

            RelativeLayout rl = layout.findViewById(R.id.marker_info_window);
            rl.setBackgroundResource(R.drawable.ic_marker_background_disable);

            ImageView iv = layout.findViewById(R.id.marker_icon);
            iv.setImageResource(R.drawable.ic_marker_disable);

            TextView tv = layout.findViewById(R.id.info_window_status);
            tv.setText("Hết chỗ");
            tv.setTextColor(Color.parseColor("#616161"));
        }

        if(salon.getPromotion() == null || salon.getPromotion().getDiscount() == 0){
            tvDiscount.setVisibility(View.GONE);
        }

        //Provide it with a layout params. It should necessarily be wrapping the
        //content as we not really going to have a parent for it.
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //Pre-measure the view so that height and width don't remain null.
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        //Assign a size and position to the view and all of its descendants
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        //Create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        //Create a canvas with the specified bitmap to draw into
        Canvas c = new Canvas(bitmap);

        //Render this view (and all of its children) to the given Canvas
        view.draw(c);
        return bitmap;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Nedded")
                        .setMessage("This app needs the Location permissio, Please accept")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION
                                        }, 88);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 88);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        if (!marker.getTag().equals("Me")) {
            ApiClient.getInstance().create(SalonApi.class)
                    .getSalonById("Bearer " + MyContants.TOKEN, MyContants.SalonList.get((int)marker.getTag()).getSalonId())
                    .enqueue(new Callback<Salon>() {
                        @Override
                        public void onResponse(Call<Salon> call, Response<Salon> response) {
                            if(response.code() == 200 && response.body() != null){
                                Intent intent = new Intent(getActivity(), SalonDetailActivity.class);
                                intent.putExtra("salon", response.body());
                                startActivity(intent);
                            }else{
                                AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra vui lòng thử lại sau");
                            }
                        }

                        @Override
                        public void onFailure(Call<Salon> call, Throwable t) {
                            AlertError.showDialogLoginFail(getActivity(), "Có lỗi xảy ra vui lòng kiểm tra lại kết nối");
                        }
                    });



        }
        return true;
    }
}
