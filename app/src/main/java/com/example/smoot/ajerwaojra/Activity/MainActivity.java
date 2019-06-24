package com.example.smoot.ajerwaojra.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smoot.ajerwaojra.R;

public class MainActivity extends AppCompatActivity {
     Button b ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    b = (Button) findViewById(R.id.btnSignUp);
    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext() , activity_doer_reg.class);
           startActivity(i);
        }
    });
    }


}
