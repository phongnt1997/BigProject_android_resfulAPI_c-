package com.pro.salon.cattocdi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent = new Intent(this, LoginActivity.class);

        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    sleep(2000);
                } catch (InterruptedException e)
                {
                    // do nothing
                } finally
                {
                    finish();
                    startActivity(intent);
                }
            }
        };
        splashThread.start();
    }
}
