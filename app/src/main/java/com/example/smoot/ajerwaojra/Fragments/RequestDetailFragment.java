package com.example.smoot.ajerwaojra.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

import static com.example.smoot.ajerwaojra.Helpers.URLs.URL_OFFER_SERVICE;


public class RequestDetailFragment extends Fragment {

    ImageView flag;
    TextView requester;
    TextView country ;
    TextView date ;
    TextView umraOwner ;
    TextView doaa ;
    Button offerService ;
    int id;
    public RequestDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_request_detail, container, false);
        flag = v.findViewById(R.id.countryFlag);
        requester = v.findViewById(R.id.requesterName);
        country = v.findViewById(R.id.country);
        date = v.findViewById(R.id.date);
        umraOwner = v.findViewById(R.id.umraOwner);
        doaa = v.findViewById(R.id.doaa);
        offerService = v.findViewById(R.id.btn_offerService);

        Bundle bundle = getArguments();
         id = bundle.getInt("id");
        //date.setText(id);
        offerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerService();
            }
        });



        return v;
    }
    public  void offerService(){
        Log.e("Raghad click button"," yes");
        Doer r = SharedPrefManager.getInstance(getContext()).getDoer();

        final  String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
         Log.e("Token Shared",token);


        StringRequest request = new StringRequest( Request.Method.POST, URL_OFFER_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    Log.e("Message of API :", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > paramas = new HashMap<>();
                paramas.put("token",token);
                paramas.put("id",String.valueOf(id));
                return paramas;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String > headers = new HashMap<>();
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer "+token);
                headers.put("token",token);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);

    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //String text = getArguments().getString("message tag");
        // textView.setText(text);

    }
}
