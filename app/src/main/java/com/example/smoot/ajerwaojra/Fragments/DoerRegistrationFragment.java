package com.example.smoot.ajerwaojra.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DoerRegistrationFragment extends Fragment {
    private FusedLocationProviderClient client;
    private Spinner howKnowus;
    private EditText textName;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone;
    private ProgressBar progressBar;
    private Button confirm;
    Fragment logInFrag;
    String phoneNomber;
    String username;
    String email;
    String password;
    String howKnowUs;
    String token;
    String exactLocation ;
    final String role = "Doer";
    double longitude, latitude;
   public String city;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                  //  "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");

    public DoerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         city = getLocation();


        View view = inflater.inflate(R.layout.fragment_doer_registration, container, false);
        howKnowus = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.howDidKnowUs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howKnowus.setAdapter(adapter);

        Intent i = new Intent();
        i.getExtras();

        textName = view.findViewById(R.id.doerName);
        textInputEmail = view.findViewById(R.id.textInputEmaildoer);
        textInputPassword = view.findViewById(R.id.inputPassword);

        textphone = view.findViewById(R.id.phoneNumber);
        //  progressBar = view.findViewById(R.id.progressBarr);
        confirm = view.findViewById(R.id.doerRegisterButton);


        // logIn
        TextView toLogIn = view.findViewById(R.id.logIn);
        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInFrag = new logInFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, logInFrag);
                ft.commit();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (exactLocation == "مكة") {
                                               Log.e("outer  if ", "yse");
                                               if (isValidMobile() && validateEmail() && validatePassword()) {
                                                   Log.e("inner if ", "yes");
                                                   doerRegister();
                                               }
                                           } else {
                                               showMessage();
                                           }
                                       }
                                   });

        // Inflate the layout for this fragment
        return view;
    }

    public String getLocation() {
        client = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Log.e("afte if of permission" ,"uuu");
        client.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                try {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> addresses = null;
                    addresses = geocoder.getFromLocation(latitude,longitude,1);
                    String city , county ,state ;
                    city = addresses.get(0).getLocality().concat("");


                    exactLocation=  city ;
                     Log.e("lll", ""+exactLocation);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return  exactLocation;
    }


    private void doerRegister() {
        phoneNomber = textphone.getText().toString().trim();
        username = textName.getText().toString().trim();
        email = textInputEmail.getText().toString().trim();
        password = textInputPassword.getText().toString().trim();
        howKnowUs = howKnowus.getSelectedItem().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.v("Res", response);
                            JSONObject ob = new JSONObject(response);
                            token = ob.getString("access_token");
                            Log.v("access_token", token);
                            Doer doer = new Doer(token);
                            Log.e("token From obj ", doer.getDoerToken());
                            SharedPrefManager.getInstance(getContext()).userLogin(doer);
                            String print = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
                            Log.e("Token from shared pref", print);
                            Fragment f = new doerHomeFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, f);
                            ft.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("email", email);
                params.put("phone", phoneNomber);
                params.put("role", role);
                params.put("country", "sss");
                params.put("knowUs", howKnowUs);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    private boolean validateEmail() {
        String emailInput = textInputEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("حقل البريد الالكتروني فارغ");
            textInputEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("الرجاء إدخال بريد الكتروني صحيح");
            textInputEmail.requestFocus();
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("حقل كلمة السر فارغ");
            textInputPassword.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("كلمة السر ضعيفة");
            textInputPassword.requestFocus();
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean isValidMobile() {
        String regexStr = "^\\+[0-9]{10,13}$";

        String number = textphone.getText().toString();

        if (textphone.getText().toString().length() < 10 || number.length() > 13 || number.matches(regexStr) == false) {
            textphone.setError("ادخل رقم الجوال بشكل صحيح");
            textphone.requestFocus();
            return false;
        } else if (!textphone.getText().toString().contains("+")) {
            textphone.setError("يجب أن يحتوي على + ");
            textphone.requestFocus();
            return false;
        } else {
            textphone.setError(null);
            return true;
        }
    }

    public void showMessage(){

        AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا.....");
        alert.setMessage("يجب أن يكون موقعك الحالي مكة لإكمال تسجيل الاشتراك ");
        alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FatwaFragment fragment = new FatwaFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        });
        alert.show();
    }
}
