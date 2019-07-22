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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        id = readDataStartOmra();
    /*    Bundle bundle0 = getArguments();


        umraOwner.setText(bundle0.getString("umraUner"));
        doaa.setText(bundle0.getString("doaa"));
        id = bundle0.getInt("id");*/

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
                if (city != null) {
                    if (city.equals("مكة")) {
                        Log.e("idOOOO////", id + "?");
                        postStartOmra(id);
                        startTime = SystemClock.uptimeMillis();
                        myHandler.postDelayed(updateTimerMethod, 0);
                        startButton.setVisibility(View.INVISIBLE);
                        stopUmraaButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        showMessage();
                    }
                }else{
                    showMessageLocation();
                }
            }
        });


        stopUmraaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

                String total = hours.getText()+":"+minuts.getText()+":"+secondss.getText();
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
               // Log.e("country is ",Neighborhood);
               // Toast.makeText(getContext(),Neighborhood,Toast.LENGTH_LONG).show();
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
    private void postStartOmra(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_START_OMRA_USER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("postStartOmra respon>>",response);
                        try {
                            JSONObject ob = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("account error>>",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("account response", error.toString());
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
                headers.put("Authorization", "Bearer "+token);

                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    public int readDataStartOmra() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_DOER_INBROGRESS_ORDER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("doaaaaa", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));
                            JSONArray doerArr = obj.getJSONArray("Doer InProgress Request");
                            for (int i=0 ; i< doerArr.length(); i++){
                                String nameOmra = doerArr.getJSONObject(i).getString("name");
                                String doaA = doerArr.getJSONObject(i).getString("doaa");
                                id = doerArr.getJSONObject(i).getInt("id");
                                umraOwner.setText(nameOmra);
                                doaa.setText(doaA);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   Log.e("catch proo }},", e.toString());
                        }
                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Log.e("erroooor proo", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
                //   Log.e("token for user", token);
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        return id;
    }
    public void showMessageLocation() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا.....");
        alert.setMessage("يجب اعطاء صلاحيات الوصول الى الموقع ");
        alert.show();
    }
}



