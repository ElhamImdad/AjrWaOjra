package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsFragment extends Fragment {
    private ImageView returnBTN;
    private RequestQueue mQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_details, container, false);
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
        mQueue = Volley.newRequestQueue(getContext());

        return view;
    }

    private void showRequest(){
        mQueue.start();

        String url = "http://ajrandojra.website/api/listRequestsR";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("list order response>>>",response.toString());
                            JSONArray jsonObj = response.getJSONArray("orders");

                            OmraInfo omraInfoObject;
                            for (int i = 0; i < jsonObj.length(); i++){
                                omraInfoObject = new OmraInfo();
                                omraInfoObject.setUmraName(jsonObj.getJSONObject(i).getString("name"));
                                omraInfoObject.setStatus(jsonObj.getJSONObject(i).getString("status"));
                                omraInfoObject.setDoerOmraName(jsonObj.getJSONObject(i).getString("doer_name"));

                            }

                        } catch (JSONException excep) {
                            excep.printStackTrace();
                            Log.e("Exception details???",excep.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("volleyErro in detail>",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;  charset=UTF-8");
                //    headers.put("X-Requested-With","XMLHttpRequest");

                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token for user",token);
                headers.put("Authorization", token);
                //   headers.put("Accept","application/json");
//  headers.put("Authorization", "Bearer "+token);
                Log.e("------------","00555");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
