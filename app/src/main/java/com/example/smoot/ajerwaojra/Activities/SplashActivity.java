package com.example.smoot.ajerwaojra.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.smoot.ajerwaojra.R;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    progressBar.getProgressDrawable().setColorFilter(
                            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
