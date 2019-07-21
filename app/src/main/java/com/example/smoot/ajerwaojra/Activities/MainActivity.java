package com.example.smoot.ajerwaojra.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Fragments.DoerAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Fragments.OnholdRequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.RequesterAccountFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.doerHomeFragment;
import com.example.smoot.ajerwaojra.Fragments.timerFragment;
import com.example.smoot.ajerwaojra.Helpers.RService;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView userName;
    View mHeaderView;
    ImageView userIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // NavigationView Header
        mHeaderView =  navigationView.getHeaderView(0);

        // View
        userName = (TextView) mHeaderView.findViewById(R.id.userame);
        userIcon = (ImageView) mHeaderView.findViewById(R.id.userPicture);

        navigationView.setNavigationItemSelectedListener(this);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Log.e("Tag ", "outer if ");
            if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")) {
                String nn = SharedPrefManager.getInstance(this).getDoer().getDoerName();
                Log.e("nnnnnnnnnn",nn+"????");
                if (nn!= null){
                userName.setText(nn);}
                userIcon.setImageResource(R.drawable.avatar);
                Log.e("inner ", " ineer if ");
                setHomeFragment(new doerHomeFragment());
            } else {
                String nn = SharedPrefManager.getInstance(this).getRequester().getName();
                if (nn!= null){
                    userName.setText(nn);}
                userIcon.setImageResource(R.drawable.person_icon);
                Log.e("Tag", "inner else ");
                setHomeFragment(new RequestsFragment());
            }

        } else {

            setFragment(new FatwaFragment());
        }
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Requester")) {
                if (RService.serviceIsRun == false) {
                    Log.e("inside if false", "yes");
                    RService.serviceIsRun = true;
                    Log.e("make it true", "yes");
                    Intent intent = new Intent(this, RService.class);
                    Log.e("before start service ", "yes");
                    startService(intent);

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setHomeFragment(Fragment home) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, home);
        ft.commit();
    }
    private void setFragment( Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,fragment);
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
                    } else {
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
                    } else {
                        setHomeFragment(new RequestsFragment());
                        drawerLayout.closeDrawers();
                    }
                }
                break;
            case R.id.orders:
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    Log.e("Tag ", "outer if ");
                    if (SharedPrefManager.getInstance(this).getDoer().getRole().equalsIgnoreCase("Doer")) {
                        doerHomeFragment ddd = new  doerHomeFragment();
                        if(ddd.isImpty() == false){
                            setFragment(new timerFragment());
                        }else {
                            confirmMyOrder();
                        }
                        drawerLayout.closeDrawers();
                       // setHomeFragment(new doerHomeFragment());
                      //  drawerLayout.closeDrawers();
                    }
                    else {
                        setFragmentDialog();

                        // setHomeFragment(new doerHomeFragment());
                          drawerLayout.closeDrawers();
                    }
                }

                break;

        }
        return true;
    }

    public void confirmLogout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("تأكيد");
        alert.setMessage("هل ترغب بتسجيل الخروج؟");
        alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (RService.serviceIsRun == true) {
                    RService.serviceIsRun = false;
                }
                if (drawerLayout.isDrawerOpen(navigationView)){drawerLayout.closeDrawers();}
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Log.e("logOut ", "uuhbhhbh");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FatwaFragment()).commit();
                drawerLayout.closeDrawers();
            }
        });
        alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (drawerLayout.isDrawerOpen(navigationView)){drawerLayout.closeDrawers();}
                drawerLayout.closeDrawers();
            }
        });
        alert.show();
    }

    private void setFragmentDialog() {
        FragmentManager fm = getSupportFragmentManager();
        OnholdRequestsFragment onhold = new OnholdRequestsFragment();
        onhold.show(fm, "service_info");
        //when the button clicked

    }
    public void confirmMyOrder(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("عذراً..");
        alert.setMessage("لاتوجد لديك طلبات لم يتم تنفيذها");
        alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawerLayout.closeDrawers();
            }
        });
        alert.show();
    }

}

