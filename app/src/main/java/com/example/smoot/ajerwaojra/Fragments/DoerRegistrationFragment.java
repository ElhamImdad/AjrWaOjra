package com.example.smoot.ajerwaojra.Fragments;


import android.app.Activity;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DoerRegistrationFragment extends Fragment {
  //  private FusedLocationProviderClient client;
    private Spinner howKnowus;
    private EditText textName;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone;
    private ProgressBar progressBar;
    private Button confirm;
    private String url = "http://testtamayoz.tamayyozz.net/api/register";
    Fragment logInFrag;
    String exactLocation;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");

    public DoerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        requestPermission();
      //  client = LocationServices.getFusedLocationProviderClient(getContext());


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
              /*  client.getLastLocation().addOnSuccessListener( (Activity) getContext(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        try {
                            Geocoder geocoder = new Geocoder(getContext());
                            List<Address> addresses = null;
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            String city, county, state;
                            city = addresses.get(0).getLocality().concat(" ");
                            county = addresses.get(0).getCountryName().concat(" ");
                            state = addresses.get(0).getAdminArea().concat(" ");

                            exactLocation = city;


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
               //  if (exactLocation=="مكة"){
                if (validateEmail() && validatePassword() && isValidMobile()) {
                    doerRegister();
                    Fragment f = new RequestsFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, f);
                    ft.commit();

            //    }
            }
                 Toast.makeText(getContext(),"You must be in Dammam",Toast.LENGTH_LONG).show();
            }


        });


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
        // Inflate the layout for this fragment
        return view;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void doerRegister() {
        final String phoneNomber = textphone.getText().toString().trim();
        final String username = textName.getText().toString().trim();
        final String email = textInputEmail.getText().toString().trim();
        final String password = textInputPassword.getText().toString().trim();
        final String howKnowUs = howKnowus.toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressBar.setVisibility(View.GONE);
                try {
                    /*JSONObject json = new JSONObject(response);
                    JSONObject obj = json.getJSONObject("success");
                    String token = obj.getString("token");
                     Toast.makeText(getContext(),token,Toast.LENGTH_LONG).show();

                        Toast.makeText(getContext(), obj.getString("success"), Toast.LENGTH_SHORT).show();
                        JSONObject userJson = obj.getJSONObject("user");

                        Doer user = new Doer(userJson.getString(
                                "email"), userJson.getString("username"),userJson.getString("mobile")

                        );*/
                    JSONObject obj = new JSONObject(response);
                    JSONObject userDoer = obj.getJSONObject("success");
                    String token = userDoer.getString("token");

                    Log.e("Token From API ==", token);

                    Doer user = new Doer(token);

                    SharedPrefManager.getInstance(getContext()).userLogin(user);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", phoneNomber);
                params.put("name", username);
                params.put("email", email);
                params.put("password", password);
                params.put("howKnowUs", howKnowUs);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        Log.e("string Rqquest", VolleySingleton.getInstance(getContext()).getRequestQueue().toString());

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

    private void checkLocation() {


    }
}



