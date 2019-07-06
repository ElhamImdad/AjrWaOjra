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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OmrahRequestFragment extends Fragment {
    private EditText inputOmrahName;
    private EditText inputOmrahPrayer;
    private Button sendBtn;
    private ImageView payPalBtn;
    private ImageView masterCardBtn;
    private ImageView returnBTN;
    private ProgressBar progressBar;
    private ArrayList<OmraInfo> umraListInProgress;
    private String url= "http://ajrandojra.website/api/createRequest";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_omra_request, container, false);
        inputOmrahName = view.findViewById(R.id.OmraName);
        inputOmrahPrayer = view.findViewById(R.id.prayer);
        payPalBtn = view.findViewById(R.id.payPal);
        masterCardBtn = view.findViewById(R.id.masterCard);
        progressBar = view.findViewById(R.id.progressBar2);
        sendBtn = view.findViewById(R.id.sendRequestButton);
        umraListInProgress = new ArrayList<>();

        /*payPalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageview =(ImageView) view.findViewById(R.id.checkbutton2);
                imageview.setVisibility(View.VISIBLE);
            }
        });
        masterCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageview =(ImageView) view.findViewById(R.id.checkbutton1master);
                imageview.setVisibility(View.VISIBLE);
            }
        });*/
        returnBTN = view.findViewById(R.id.returnBtn);
       returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call method json
                    addNewRequest();

                Fragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        });
        return view;
    }//end onCreateView method

    private void addNewRequest() {
        final String name = inputOmrahName.getText().toString().trim();
        final String pryer = inputOmrahPrayer.getText().toString().trim();
        if (name.isEmpty() || pryer.isEmpty()) {
            Toast.makeText(getContext(), "الرجاء إدخال جميع البيانات", Toast.LENGTH_LONG).show();
        } else {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("respons of request", response.toString());
                        try {

                            Log.e("Hi girl", ":((");
                            //converting response to json object
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject orderr = jsonObj.getJSONObject("order");
                            OmraInfo omraInfoObject;
                            //   for (int i = 0; i < jsonArray.length(); i++){
                            omraInfoObject = new OmraInfo();
                            omraInfoObject.setUmraName(orderr.getString("name"));
                            omraInfoObject.setStatus(orderr.getString("status"));
                            omraInfoObject.setUmraPrayer(orderr.getString("doaa"));
                            umraListInProgress.add(omraInfoObject);
                            Log.e("list container", umraListInProgress.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch eroor request,", e.toString());

                        }

                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("error response", error.toString());
                        Log.e("hellllll", error.toString());
                        //    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("doaa", pryer);
                //   params.put("requester_id", String.valueOf(3));
                Log.e("doaa>-----", pryer);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                //     headers.put("Content-Type", "application/json");
                //   headers.put("X-Requested-With","XMLHttpRequest");

                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token for user", token);
                headers.put("Authorization", "Bearer " + token);


                Log.e("--------omrah request", "header");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        Log.e("umra Rqquest", VolleySingleton.getInstance(getContext()).getRequestQueue().toString());

    }
    }//end method add new request

}//end class


