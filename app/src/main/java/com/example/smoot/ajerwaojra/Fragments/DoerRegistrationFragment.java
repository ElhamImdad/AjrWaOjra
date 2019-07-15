package com.example.smoot.ajerwaojra.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class DoerRegistrationFragment extends Fragment {
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
    final String role = "Doer";
    LocationManager locationManager;
    double longitude, latitude;
    String exactLocation = "";
    final int REQUEST_CODE = 1;

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //   getPermission();

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


        //    getLocation();
        // logIn
        TextView toLogIn = view.findViewById(R.id.logIn);
        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logInFrag = new logInFragment();
                SettingFragment f = new SettingFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, logInFrag);
                ft.commit();
            }
        });
        getLocation();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

                if (isValidMobile() && validateEmail() && validatePassword()) {
                    if (exactLocation.equals("السعودية")){
                    Log.e("Are you in Saudi", "yes");
                    doerRegister();
                }
                else{
                    Log.e("not from saudi","hhh");
                    RegistrationFailFragment f = new RegistrationFailFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container,f);
                    ft.commit();
                    }
                }
            }
        });

        return view;
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
                params.put("country_id", "807");
                params.put("knowUs", howKnowUs);
                params.put("password", password);
                params.put("payment", String.valueOf(0));
                params.put("review", String.valueOf(0));
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
            textInputPassword.setError("يجب أن تحتوي على حروف وأرقام");
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
        }else {
            textphone.setError(null);
            return true;
        }
    }



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
                exactLocation = addresses.get(0).getCountryName().trim();

                Log.e("t----", exactLocation);
            } else {
                Log.e("location", "null");
            }

        } else {

            Log.e("provider ", "is not enabled");

            // اتركييييه
            Log.e("i am in if", "hi ");
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
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
}
