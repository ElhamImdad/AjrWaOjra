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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OmrahRequestFragment extends Fragment {
    private EditText inputOmrahName;
    private EditText inputOmrahPrayer;
    private Button sendBtn;
    private ImageView payPalBtn;
    private ImageView masterCardBtn;
    private String url= "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_omra_request, container, false);
        inputOmrahName = view.findViewById(R.id.OmraName);
        inputOmrahPrayer = view.findViewById(R.id.prayer);
        payPalBtn = view.findViewById(R.id.payPal);
        masterCardBtn = view.findViewById(R.id.masterCard);
        sendBtn = view.findViewById(R.id.sendRequestButton);

        payPalBtn.setOnClickListener(new View.OnClickListener() {
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
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call method json
             //   addNewRequest();
                Fragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        });
        return view;
    }

    private void addNewRequest(){
        final String name = inputOmrahName.getText().toString().trim();
        final String pryer = inputOmrahPrayer.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);


                            Fragment f = new RequestsFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, f);
                            ft.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error response", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("doaa", pryer);

                Log.e("doaa", pryer);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        Log.e("string Rqquest", VolleySingleton.getInstance(getContext()).getRequestQueue().toString());
    }//end method add new request

}//end class


