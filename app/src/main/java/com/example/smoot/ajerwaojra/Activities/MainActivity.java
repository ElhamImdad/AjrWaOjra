package com.example.smoot.ajerwaojra.Activities;

import android.content.DialogInterface;
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
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Fragments.DoerAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Fragments.RequesterAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.OnholdRequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.doerHomeFragment;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userName = findViewById(R.id.userName);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Log.e("Tag ", "outer if ");
            if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")){
               // userName.setText(SharedPrefManager.getInstance(this).getDoer().getDoerName());
                Log.e("inner "," ineer if ");
                setHomeFragment(new doerHomeFragment());
            }else{
                Log.e("Tag", "inner else ");
               // userName.setText(SharedPrefManager.getInstance(this).getRequester().getName());
                setHomeFragment(new RequestsFragment());}

        }else{

            setFragment(new FatwaFragment());
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
        super.onBackPressed();}
    }

    private void setHomeFragment(Fragment home) {
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
                        FragmentManager fm = getSupportFragmentManager();
                        OnholdRequestsFragment onhold =new  OnholdRequestsFragment();

                        //when the button clicked
                        onhold.show(fm, "services_tag");
                      //  setHomeFragment(new RequestsFragment());
                        drawerLayout.closeDrawers();
                    }
                }
                break;

        }
        return true;
    }
   /*private void setFragmentDetails(Fragment details){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.reqDetailPage,details);
        ft.commit();
    }*/
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
   private void setFragmentDialog(Fragment dialog){
        FragmentManager fm = getSupportFragmentManager();
       OnholdRequestsFragment onhold =new  OnholdRequestsFragment();

        //when the button clicked
       onhold.show(fm, "services_tag");
    }

}

