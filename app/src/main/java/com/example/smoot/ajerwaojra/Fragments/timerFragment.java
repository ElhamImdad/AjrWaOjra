package com.example.smoot.ajerwaojra.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class timerFragment extends Fragment {
    /** Called when the activity is first created. */
    private TextView textTimer;
    private Button startButton;
    private Button pauseButton;
    private Button stopUmraaButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    TextView secondss;
    TextView minuts;
    TextView hours;

    TextView umraOwner ;
    TextView doaa;

    String city ;
    LocationManager locationManager;
    double longitude,latitude;
    final  int REQUEST_CODE=1;

    int id;

    String stringDate;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_timer, container, false);

        Date ddd = new Date();
        stringDate = DateFormat.getDateInstance().format(ddd);
        Log.e("Current Date is ",stringDate);

        umraOwner = v.findViewById(R.id.umraOwner);
        doaa = v.findViewById(R.id.doaa);

        Bundle bundle0 = getArguments();


        umraOwner.setText(bundle0.getString("umraUner"));
        doaa.setText(bundle0.getString("doaa"));
        id = bundle0.getInt("id");



        stopUmraaButton = v.findViewById(R.id.stopUmra);
        stopUmraaButton.setVisibility(View.INVISIBLE);
        secondss = v.findViewById(R.id.textView19);
        minuts = v.findViewById(R.id.textView18);
        hours = v.findViewById(R.id.textView17);


        startButton = v.findViewById(R.id.start);
        getLocation();
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getLocation();
                if (city.equals("مكة")){
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);
                startButton.setVisibility(View.INVISIBLE);
                stopUmraaButton.setVisibility(View.VISIBLE);}
                else {
                    showMessage();
                }
            }
        });


        stopUmraaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

                String total = hours.getText()+":"+minuts.getText()+":";
                Log.e("total ",total);

                Bundle bundle = new Bundle();
                bundle.putString("totalTime",total);
                bundle.putString("date",stringDate);
                bundle.putInt("id",id);

                UploadImagesFragment f = new UploadImagesFragment();
                f.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.disallowAddToBackStack();
                ft.replace(R.id.container,f);
                ft.commit();
            }
        });



return  v;
    }

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            int hour =  (milliseconds / 1000) / 36000;
         /*   textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliseconds));*/
            minuts.setText(String.format("%02d", minutes));
            secondss.setText(String.format("%02d", seconds));
            hours.setText(String.format("%02d", hour));



            myHandler.postDelayed(this, 0);
        }

    };

    protected void getLocation() {
        getPermission();
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("provider ", "enabled");
            if (checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(getContext(),
                    ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("There is no permission", "!!!");

                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                Log.e("TAG", "There is a location");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                city = addresses.get(0).getLocality().trim();
                //String country = addresses.get(0).getAdminArea();// return مكة المكرمة
                //String country = addresses.get(0).getFeatureName(); return number
                String Neighborhood = addresses.get(0).getSubLocality(); // return العدل
                Log.e("country is ",Neighborhood);
                //getThoroughfare() and getSubThoroughfare() does not work


                Log.e("t----", city);
            } else {
                Log.e("location", "null");
            }

        } else {

            Log.e("provider ", "is not enabled");

            // اتركييييه
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("عذرا.....");
            alert.setMessage("يجب تفعيل خدمات تحديد المواقع GPS ");
            alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
            alert.show();
        }
    }

    public void getPermission() {
        if (checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(getContext(),
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                Log.e("Permission is ", "granted");
            }
        }
    }
    public void showMessage() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا.....");
        alert.setMessage("يجب أن يكون موقعك الحالي مكة لبدء العمرة ");
        alert.show();
    }
}



