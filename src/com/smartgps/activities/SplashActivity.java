package com.smartgps.activities;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.smartgps.R;

public class SplashActivity extends Activity {

    /**
     * For loading the currency list.
     */

    private ScheduledThreadPoolExecutor executor;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        long splashLength = 3000;

        executor = new ScheduledThreadPoolExecutor(1);

        executor.schedule(new Runnable() {

            @Override
            public void run() {
            	   Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                finish();
            }
        }, splashLength, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        executor.shutdownNow();
    }
}
