package com.salon.cattocdi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.salon.cattocdi.fragements.AppointmentFragment;
import com.salon.cattocdi.fragements.FavoriteFragment;
import com.salon.cattocdi.fragements.HomeFragment;
import com.salon.cattocdi.fragements.ProfileFragment;
import com.salon.cattocdi.fragements.ReviewsFragment;
import com.salon.cattocdi.fragements.SearchFragment;
import com.salon.cattocdi.fragements.ShowServiceFragment;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private int currentPos = 0, nextPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_home_item:
                        currentPos = nextPos;
                        nextPos = 0;
                        HomeFragment homeFragment = new HomeFragment();
                        showFragment(homeFragment);
                        return true;
                    case R.id.bottom_nav_appointment_item:
                        currentPos = nextPos;
                        nextPos = 1;
                        AppointmentFragment appointmentFragment = new AppointmentFragment();
                        showFragment(appointmentFragment);
                        return true;
                    case R.id.bottom_nav_search_item:
                        currentPos = nextPos;
                        nextPos = 2;
                        SearchFragment searchFragment = new SearchFragment();
                        showFragment(searchFragment);
                        return true;
//                    case R.id.bottom_nav_favorite_item:
//                        currentPos = nextPos;
//                        nextPos = 3;
//                        FavoriteFragment favoriteFragment = new FavoriteFragment();
//                        showFragment(favoriteFragment);
//                        return true;
                    case R.id.bottom_nav_profile_item:
                        currentPos = nextPos;
                        nextPos = 4;
                        ProfileFragment profileFragment = new ProfileFragment();
                        showFragment(profileFragment);
                        return true;
                }
                return false;
            }
        });
        //HOME FRAGMENT will show first
        String flag = "";
        Intent intent = getIntent();
        flag = intent.getStringExtra("done");
        if (flag != null) {
            BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
            navigationView.getMenu().getItem(1).setChecked(true);
            AppointmentFragment appointmentFragment = new AppointmentFragment();
            showFragment(appointmentFragment);
        }
        else {
            showFragment(new HomeFragment());
        }
    }

    @SuppressLint("ResourceType")
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentPos < nextPos) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (currentPos > nextPos) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(R.id.activity_main_container_fl, fragment);
        transaction.commit();
    }

}
