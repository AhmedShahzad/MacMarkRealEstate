package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestate.MainActivity;
import com.example.realestate.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
finish();

            }
        },2000);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}