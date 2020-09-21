package com.example.samplecountrydetails.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.example.samplecountrydetails.R;

public class SplashScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        checkInterNetConnection();
    }

    /*check internet connection and narrow down to activity*/
    public void checkInterNetConnection(){
        if (isConnected()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent openMainActivity =  new Intent(SplashScreenActivity.this, ListofCountriesActivity.class);
                    startActivity(openMainActivity);
                    finish();
                }
            }, 1500);
        } else {
            Intent noInternetIntent = new Intent(SplashScreenActivity.this, NoInterNetShowActivity.class);
            startActivity(noInternetIntent);
            finish();
        }
    }

    /*check the internet connectivity*/
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}
