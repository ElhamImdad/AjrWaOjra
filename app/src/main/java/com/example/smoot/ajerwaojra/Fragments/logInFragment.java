package com.example.smoot.ajerwaojra.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.Models.Requester;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class logInFragment extends Fragment {
    EditText logInEmail;
    EditText inputPassword;
    EditText phoneNumber ;
    Button loginButton;
    private ProgressBar progressBar;
    String role ;
    String token;

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

        progressBar = v.findViewById(R.id.progressBar3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hellow ","hellow");

                login();

            }
        });

        return v;
    }
    public  void login (){
        final String email = logInEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("response Result == ", response);
                      //  progressBar.setVisibility(View.GONE);
                        JSONObject obj = new JSONObject(response);
                        token = obj.getString("access_token");

                        JSONObject client = obj.getJSONObject("user");
                        role = client.getString("role");

                        if (role.equalsIgnoreCase("Requester")){
                            Requester user = new Requester(token);
                            SharedPrefManager.getInstance(getContext()).userLogin(user);
                            Fragment fragment = new RequestsFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, fragment);
                            ft.commit();
                        }
                        else {
                            Doer doer = new Doer(token);
                            SharedPrefManager.getInstance(getContext()).userLogin(doer);
                            Fragment f = new doerHomeFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, f);
                            ft.commit();}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error in login", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String , String > paramas = new HashMap<>();
                    paramas.put("email",email);
                    paramas.put("password", password);
                    return paramas;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    //    headers.put("Content-Type", "application/json;  charset=UTF-8\"");
                //    headers.put("Accept","application/json");
                  ///     headers.put("Content-Type", "application/json");
                    //   headers.put("X-Requested-With","XMLHttpRequest");

                    String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                  //  Log.e("token for user",token);
                 //   headers.put("Authorization", "Bearer Token"+token);


                    Log.e("request fragment--","header");
                    return headers;
                }
            };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
