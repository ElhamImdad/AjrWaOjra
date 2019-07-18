package com.example.smoot.ajerwaojra.Fragments;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoerAccountFragment extends Fragment {
    private TextView ouWebsit, myName, myEmail, myPhone, winFromApp, noOmraDoneDoer;
    private Button edit;
    private ImageView setting;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public DoerAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doer_account, container, false);
        myName = view.findViewById(R.id.nameAccDoer);
        myEmail = view.findViewById(R.id.emailAccDoer);
        myPhone = view.findViewById(R.id.phoneAccDoer);
        winFromApp = view.findViewById(R.id.winFromApp);
        noOmraDoneDoer = view.findViewById(R.id.noOmraDoneDoer);
        edit = view.findViewById(R.id.editDoerAccount);
        setting = view.findViewById(R.id.settingImage);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        navigationView = getActivity().findViewById(R.id.nav_view);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             drawerLayout.openDrawer(navigationView);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDoerAccountFragment f = new UpdateDoerAccountFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container,f);
                ft.commit();
            }
        });
        getAccount();
        return view;
    }
    private void getAccount(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_LIST_USER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject ob = new JSONObject(response);
                            JSONObject USER = ob.getJSONObject("user");
                            myName.setText(USER.getString("name"));
                            myEmail.setText(USER.getString("email"));
                            myPhone.setText(USER.getString("phone"));
                            winFromApp.setText(USER.getString("payment") + "دولار");
                            noOmraDoneDoer.setText(ob.getString("Done Orders"));
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

}
