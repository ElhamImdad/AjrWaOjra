package com.example.smoot.ajerwaojra.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.smoot.ajerwaojra.Fragments.DoerAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Fragments.InprogressRequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.OnholdRequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.PendingRequestFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestDetailsFragment;
import com.example.smoot.ajerwaojra.Fragments.RequesterAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.doerHomeFragment;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.R;

public class OmrahDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omrah_details);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        Bundle bundle;
        bundle = intent.getExtras();
        String omraStatus = bundle.getString("omraStatus");
        String OrderOption = bundle.getString("option");

        if (bundle != null ){
            if (omraStatus != null) {
                switch (omraStatus) {
                    case "3":
                        Bundle bundle2 = new Bundle();
                        Log.e("omra name,,,", bundle.getString("omraName"));
                        bundle2.putString("omraName", bundle.getString("omraName"));
                        bundle2.putString("doerName", bundle.getString("doerName"));
                        bundle2.putString("date", bundle.getString("date"));
                        bundle2.putString("time", bundle.getString("time"));
                        bundle2.putString("doaa", bundle.getString("doaa"));
                        bundle2.putStringArrayList("photos", bundle.getStringArrayList("photos"));

                        RequestDetailsFragment doneObj = new RequestDetailsFragment();
                        doneObj.setArguments(bundle2);
                        setFragment2(doneObj);
                        break;
                    case "2":
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("omraName", bundle.getString("omraName"));
                        bundle3.putString("doerName", bundle.getString("doerName"));
                        bundle3.putString("date", bundle.getString("date"));
                        bundle3.putString("time", bundle.getString("time"));
                        bundle3.putString("doaa", bundle.getString("doaa"));

                        InprogressRequestsFragment inProgressObj = new InprogressRequestsFragment();
                        inProgressObj.setArguments(bundle3);
                        setFragment2(inProgressObj);
                        break;
                    case "1":
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("omraName", bundle.getString("omraName"));
                        bundle4.putString("doaa", bundle.getString("doaa"));

                        PendingRequestFragment pendingObj = new PendingRequestFragment();
                        pendingObj.setArguments(bundle4);
                        setFragment2(pendingObj);
                        break;
                }
            }
            if (OrderOption != null) {
                switch (OrderOption) {
                    case "positive":
                  //      Bundle bundleP = new Bundle();
                  //      bundleP.putString("option", "positive");
                   //     bundleP.putInt("OrderIdP", bundle.getInt("OrderIdP"));

                        RequestsFragment posativeObj = new RequestsFragment();
                   //     posativeObj.setArguments(bundleP);
                        setFragment2(posativeObj);
                        break;
                    case "negative":
                     //   Bundle bundleN = new Bundle();
                     //   bundleN.putString("option", "negative");
                     //   bundleN.putInt("OrderIdN", bundle.getInt("OrderIdN"));

                        RequestsFragment negativeObj = new RequestsFragment();
                       // negativeObj.setArguments(bundleN);
                        setFragment2(negativeObj);
                        break;

                }

            }
        }
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();}
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logOut:
                confirmLogout();
                break;
            case R.id.account:
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    Log.e("Tag ", "outer if ");
                    if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")) {
                        setHomeFragment(new DoerAccountFragment());
                        drawerLayout.closeDrawers();
                    }
                    else {
                        setHomeFragment(new RequesterAccountFragment());
                        drawerLayout.closeDrawers();
                    }
                }
                break;
            case R.id.home:
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    Log.e("Tag ", "outer if ");
                    if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")) {
                        setHomeFragment(new doerHomeFragment());
                        drawerLayout.closeDrawers();
                    }
                    else {
                        setHomeFragment(new RequestsFragment());
                        drawerLayout.closeDrawers();
                    }
                }
                break;
            case R.id.orders:
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    Log.e("Tag ", "outer if ");
                    if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")) {
                        // setHomeFragment(new doerHomeFragment());
                        //  drawerLayout.closeDrawers();
                    }
                    else {
                        setFragmentDialog();
                        drawerLayout.closeDrawers();
                    }
                }
                break;

        }
        return true;
    }
    public void setFragment2(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.details,f);
        ft.commit();
    }
    public void confirmLogout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("تأكيد");
        alert.setMessage("هل ترغب بتسجيل الخروج؟");
        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Log.e("logOut ","uuhbhhbh");
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FatwaFragment()).commit();
                drawerLayout.closeDrawers();
            }
        });
        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawerLayout.closeDrawers();
            }
        });
        alert.show();
    }
    private void setFragmentDialog(){
        FragmentManager fm = getSupportFragmentManager();
        OnholdRequestsFragment onhold =new  OnholdRequestsFragment();
        onhold.show(fm, "service_info");
        //when the button clicked

    }
    private void setHomeFragment(Fragment home) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,home);
        ft.commit();
    }

}
