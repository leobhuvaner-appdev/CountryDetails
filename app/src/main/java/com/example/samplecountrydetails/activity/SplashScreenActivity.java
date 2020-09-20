package com.example.samplecountrydetails.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.samplecountrydetails.R;
import com.google.android.material.snackbar.Snackbar;


public class SplashScreenActivity extends AppCompatActivity{
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        constraintLayout = findViewById(R.id.splash_layout);
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
