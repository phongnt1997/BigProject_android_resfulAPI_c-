package com.pro.salon.cattocdi;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.salon.cattocdi.adapter.ProfileTabAdapter;
import com.pro.salon.cattocdi.models.Salon;

public class ReviewProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvEdit;
    private ImageView icFavorite;
    private Salon salon;

    public ReviewProfileActivity(Salon salon) {
        this.salon = salon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_profile);

        viewPager = (ViewPager) findViewById(R.id.detail_pager);
        ProfileTabAdapter adapter = new ProfileTabAdapter(getSupportFragmentManager(), true, salon);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tvEdit = findViewById(R.id.fg_profile_edit_tv);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileFragment();
            }
        });

    }
    private void goToProfileFragment(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment_id", R.id.bottom_nav_profile_item);
        startActivity(intent);
    }
}
