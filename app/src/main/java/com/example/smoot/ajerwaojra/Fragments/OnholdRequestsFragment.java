package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Adapter.OnHoldRequestsAdapter;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.ServiceInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnholdRequestsFragment extends DialogFragment {
    ArrayList<ServiceInfo> myServicesList;
    RecyclerView recycler;
    OnHoldRequestsAdapter adapter;
    public OnholdRequestsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_onhold_requests, container, false);
        recycler = (RecyclerView) rootview.findViewById(R.id.mRecyclerOnHold);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
        viewServicesDate();
        adapter = new OnHoldRequestsAdapter(myServicesList, getContext());
        recycler.setAdapter(adapter);
        return rootview;
    }
    private void viewServicesDate() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_OFFER_NOTIFICATION_USER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("respons of servisec", response.toString());
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                ServiceInfo serviceInfo;
                                for (int i = 0; i < jsonArray.length(); i++){
                                    serviceInfo = new ServiceInfo();
                                    JSONObject onHoldReq = jsonArray.getJSONObject(i).getJSONObject("On hold Request");
                                    serviceInfo.setDoerName(onHoldReq.getString("doer_name"));
                                    serviceInfo.setDate(onHoldReq.getString("date"));
                                    serviceInfo.setOmraName(onHoldReq.getString("name"));
                                    serviceInfo.setNoCompletedOrder(jsonArray.getJSONObject(i).getInt("completed Orders"));
                                    serviceInfo.setOrder_id(jsonArray.getJSONObject(i).getString("order_id"));
                                    JSONArray orderInfo = jsonArray.getJSONObject(i).getJSONArray("doer");
                                    serviceInfo.setRating(orderInfo.getJSONObject(i).getString("review"));
                                    myServicesList.add(serviceInfo);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("catch eroor }},", e.toString());
                            }
                        }//end onResponse
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                              Log.e("erroooor response", error.toString());
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                 //   Log.e("token for user", token);
                    headers.put("Authorization", "Bearer " + token);
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
