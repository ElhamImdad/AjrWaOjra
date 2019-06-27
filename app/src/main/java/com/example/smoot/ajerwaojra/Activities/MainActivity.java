package com.example.smoot.ajerwaojra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestsFragment;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.R;

public class MainActivity extends AppCompatActivity {



    private Intent intent;
    private Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
           // Requester RequestUser = SharedPrefManager.getInstance(this).getUserReq();
            setHomeFragment(new RequestsFragment());
        }else{
            setFragment(new FatwaFragment());
        }

      //  configureDoer_RequesterButton();
    }

    private void setHomeFragment( Fragment home) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,home);
        ft.commit();

    }
    private void setFragment( Fragment fatwa) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,fatwa);
        ft.commit();}

   /* private void configureDoer_RequesterButton(){
        ImageButton doerBtn = (ImageButton) findViewById(R.id.doerImag);
        ImageButton requesterBtn = (ImageButton) findViewById(R.id.requesterImag);

        doerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_doer_reg.class));
            //     intent = new Intent(getApplicationContext(), activity_doer_reg.class);
             //   startActivity(intent);
            }
        });

        requesterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ActivityRequesterReg.class));
            }
        });
    }*/
}
