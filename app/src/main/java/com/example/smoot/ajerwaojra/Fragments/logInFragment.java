package com.example.smoot.ajerwaojra.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class logInFragment extends Fragment {
    EditText logInEmail;
    EditText inputPassword;
    EditText phoneNumber ;
    Button loginButton;
    String email, password,mobile ;

    AlertDialog builder;

    public logInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);

        TextView goToRegistration = v.findViewById(R.id.toRegistration);
        goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FatwaFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        });
        loginButton = v.findViewById(R.id.loginButton);
        inputPassword = v.findViewById(R.id.inputPassword);
        logInEmail = v.findViewById(R.id.logInEmail);
        phoneNumber = v.findViewById(R.id.editTextPhone);
        email = logInEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        //mobile = phoneNumber.getText().toString();
        login();
        return v;
    }

    public  void login (){
        if (email.equals("") || password.equals("")) {
            // TODO 1 : // insert your code
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray obj = new JSONArray(response);
                        JSONObject success = obj.getJSONObject(0);
                        String token = success.getString("success");

                        Log.i("Token Result == ", token);
                        Toast.makeText(getContext(),token,Toast.LENGTH_LONG).show();
                        Fragment fragment = new RequesterHomeFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.container, fragment);
                        ft.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String , String > pramas = new HashMap<String, String>();
                    pramas.put("email",email);
                    pramas.put("password", password);
                    return pramas;
                }
            };

            VolleySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest);
        }
    }
}