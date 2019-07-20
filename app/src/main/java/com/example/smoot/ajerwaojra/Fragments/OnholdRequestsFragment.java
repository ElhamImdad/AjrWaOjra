package com.example.smoot.ajerwaojra.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnholdRequestsFragment extends DialogFragment {
    private ArrayList<ServiceInfo> myServicesList;
    RecyclerView recycler;
    OnHoldRequestsAdapter adapter;
    int orderIdP, orderIdN;

    public ArrayList<ServiceInfo> getMyServicesList() {
        return myServicesList;
    }

    public OnholdRequestsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_onhold_requests, container);
        myServicesList = new ArrayList<>();
        recycler = (RecyclerView) rootview.findViewById(R.id.mRecyclerOnHold);
        LinearLayoutManager layoutManager
              = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);

        // READ DATA FROM API
        viewServicesDate();

        //ADAPTER
        adapter = new OnHoldRequestsAdapter(myServicesList, this.getActivity());
        recycler.setAdapter(adapter);
        if (myServicesList.size() == 0){
            noOrder();
        }

//        this.getDialog().setTitle("طلبات العمرة");
        return rootview;
    }
    private void viewServicesDate() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_OFFER_NOTIFICATION_USER,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(String response) {
                            Log.e("respons of servisec", response.toString());
                            try {
                                JSONObject obj = new JSONObject((response));

                                JSONArray jsonArray = obj.getJSONArray("doer offer");
                                ServiceInfo serviceInfo;

                                for (int i = 0; i < jsonArray.length(); i++){
                                    serviceInfo = new ServiceInfo();
                                    JSONObject onHoldReq = jsonArray.getJSONObject(i).getJSONObject("On hold Request");
                                    serviceInfo.setDoerName(onHoldReq.getString("doer_name"));
                                    serviceInfo.setOrder_id(onHoldReq.getInt("id"));
                                    String dateFromApi = onHoldReq.getString("date");
                                    String gregorianString = "";
                                    if (dateFromApi != null){
                                        gregorianString = convertDte(dateFromApi);
                                    }
                                    serviceInfo.setDate(gregorianString);
                                    serviceInfo.setOmraName(onHoldReq.getString("name"));
                                    serviceInfo.setNoCompletedOrder(jsonArray.getJSONObject(i).getInt("completed Orders"));
                                    JSONArray orderInfo = jsonArray.getJSONObject(i).getJSONArray("doer");
                                    for (int j= 0 ; j<orderInfo.length(); j++){
                                        serviceInfo.setRating(orderInfo.getJSONObject(j).getString("review"));
                                        Log.e("raaating}}}", orderInfo.getJSONObject(j).getString("review"));
                                    }
                                    Log.e("ssssss}}}", myServicesList.toString());
                                    myServicesList.add(serviceInfo);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("cAAatch eroor }},", e.toString());
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
    public void acceptOrder(final int orderIdP) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_ACCEPT_OFFER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Log.e("wwwwhat response]]]]]", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                          //  Log.e("catch eroor }},", e.toString());
                        }
                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("wwwwhat response", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramas = new HashMap<>();
                paramas.put("id", String.valueOf(orderIdP));
                return paramas;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
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
    public void rejectOrder(final int orderIdN) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_REJECT_OFFER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Log.e("respons of servisec", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramas = new HashMap<>();
                paramas.put("id", String.valueOf(orderIdN));
                return paramas;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertDte(String mydate)
    {
        String newdATE;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate gregorianDate = LocalDate.parse(mydate, dateFormatter);
        HijrahDate islamicDate = HijrahDate.from(gregorianDate);
        newdATE = islamicDate.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        String [] datearr = newdATE.split("/");
        switch (datearr[1]){
            case "01":
                newdATE = datearr[0] + " محرم " +datearr[2] + " هـ " ; break;
            case "02":
                newdATE = datearr[0] + " صفر " +datearr[2] + " هـ " ; break;
            case "03":
                newdATE = datearr[0] + " ربيع الاول " +datearr[2] + " هـ " ; break;
            case "04":
                newdATE = datearr[0] + " ربيع الآخر " +datearr[2] + " هـ " ; break;
            case "05":
                newdATE = datearr[0] + " جماد الاول " +datearr[2] + " هـ " ; break;
            case "06":
                newdATE = datearr[0] + " جماد الآخر " +datearr[2] + " هـ " ; break;
            case "07":
                newdATE = datearr[0] + " رجب " +datearr[2] + " هـ " ; break;
            case "08":
                newdATE = datearr[0] + " شعبان " +datearr[2] + " هـ " ; break;
            case "09":
                newdATE = datearr[0] + " رمضان " +datearr[2] + " هـ " ; break;
            case "10":
                newdATE = datearr[0] + " شوال " +datearr[2] + " هـ " ; break;
            case "11":
                newdATE = datearr[0] + " ذو القعدة " +datearr[2] + " هـ " ; break;
            case "12":
                newdATE = datearr[0] + " ذو الحجة " +datearr[2] + " هـ " ; break;
        }
        return newdATE;
    }
    private void noOrder(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرًا..");
        alert.setMessage("لاتوجد لديك طلبات لم تتم الموافقة عليها");
        alert.show();
    }


}
