package com.example.smoot.ajerwaojra.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.doerHomeFragment;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.R;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Log.e("Tag ", "outer if ");
            if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")){
                Log.e("inner "," ineer if ");
                setHomeFragment(new doerHomeFragment());
            }else{
                Log.e("Tag", "inner else ");
                setHomeFragment(new RequestsFragment());}

        }else{
            setFragment(new FatwaFragment());
        }

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
   /*private void setFragmentDetails(Fragment details){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.reqDetailPage,details);
        ft.commit();
    }*/

}

