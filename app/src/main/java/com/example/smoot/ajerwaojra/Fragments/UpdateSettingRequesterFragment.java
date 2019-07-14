package com.example.smoot.ajerwaojra.Fragments;

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
import android.widget.ImageView;
import android.widget.Toast;

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

public class UpdateSettingRequesterFragment extends Fragment {
    private ImageView settingIcon;
    private Button save;
    private EditText newEmail, newPhone;
    public UpdateSettingRequesterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_setting_requester, container, false);
        save = view.findViewById(R.id.saveBtn);
        newEmail = view.findViewById(R.id.newEmail);
        newPhone = view.findViewById(R.id.newPhone);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccount();
            }
        });


        settingIcon = view.findViewById(R.id.settingIconAcc2);
        settingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequesterAccountFragment requesterAccount = new RequesterAccountFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, requesterAccount);
                ft.commit();
            }
        });
        return view;
    }
    private void getAccount(){
        final String emailUser = newEmail.getText().toString().trim();
        final String mobileUser = newPhone.getText().toString().trim();
        if (emailUser.isEmpty() || mobileUser.isEmpty()) {
            Toast.makeText(getContext(), "الرجاء إدخال جميع البيانات", Toast.LENGTH_LONG).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_UPDATE_USER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject ob = new JSONObject(response);
                                JSONObject USER = ob.getJSONObject("user");
                                RequesterAccountFragment requesterAccount = new RequesterAccountFragment();
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.container, requesterAccount);
                                ft.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("aaaaaaa error>>", e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //  Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("accoooount response", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("phone", mobileUser);
                    params.put("email", emailUser);

                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
              //      Log.e("token for user", token);
                    headers.put("Authorization", "Bearer " + token);
                 //   headers.put("Content-type", "application/json");
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

}
