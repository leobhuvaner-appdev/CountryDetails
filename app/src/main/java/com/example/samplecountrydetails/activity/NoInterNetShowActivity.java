package com.example.samplecountrydetails.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.samplecountrydetails.R;
import com.google.android.material.snackbar.Snackbar;


public class NoInterNetShowActivity extends AppCompatActivity implements View.OnClickListener {
    Button retryBtn;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_inter_net_show);
        linearLayout = findViewById(R.id.no_internet_lin_layout);
        retryBtn = findViewById(R.id.retry_button);
        retryBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry_button:
                checkInterNetConnection();
                break;
        }
    }

    /*check internet connection and narrow down to activity*/
    public void checkInterNetConnection(){
        if (isConnected()) {
            moveToSplashActivity();
        } else {
            Snackbar snackbar = Snackbar.make(linearLayout,"You are offline",Snackbar.LENGTH_SHORT);
            snackbar.show();
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

    /*move to splash activity*/
    private void moveToSplashActivity(){
        Intent splashIntent = new Intent(NoInterNetShowActivity.this, SplashScreenActivity.class);
       startActivity(splashIntent);
        finish();
    }
}
