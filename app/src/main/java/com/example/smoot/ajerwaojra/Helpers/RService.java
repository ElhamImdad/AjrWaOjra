package com.example.smoot.ajerwaojra.Helpers;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Models.ServiceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RService extends IntentService {
    public static boolean serviceIsRun = false;
    private ArrayList<ServiceInfo> myServicesList;
    private ArrayList<String> doers = new ArrayList<>();
    public String doerName;
    String doerID = "";
    int id = 0;

    public RService() {
        super("Rservice");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        myServicesList = new ArrayList<>();
        try {
            Thread.sleep(10000);
            Log.e("inside sleep","yes");
        }
        catch (Exception e ){
            e.printStackTrace();
        }

        doers.clear();
        Log.e("doers", doers.toString());
        int p = 0;
        while (serviceIsRun) {
            Log.e("inside while of service", "yes");
            viewServicesDate();
            if (doerName != null && id > p) {
                Log.e("doerId", doerID);
                Log.e("doers", doers.toString());
                id = 0;
                Intent i = new Intent("NewOffer");
                i.putExtra("Message", "Hi I am Raghad I call you from Service");
                i.putExtra("Doer Name", doerName);
                sendBroadcast(new Intent(this, myBroadCast.class).setAction("NewOffer").putExtra("Doer Name", doerName));
            }
            try {
                Thread.sleep(30000);
                Log.e("inside sleep", "yes");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void viewServicesDate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_OFFER_NOTIFICATION_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("respons of servisec", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));

                            JSONArray jsonArray = obj.getJSONArray("doer offer");
                            ServiceInfo serviceInfo;
                            Log.e("ssssss}}}", String.valueOf(jsonArray.length()));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                serviceInfo = new ServiceInfo();
                                JSONObject onHoldReq = jsonArray.getJSONObject(i).getJSONObject("On hold Request");
                                serviceInfo.setDoerName(onHoldReq.getString("doer_name"));
                                doerName = onHoldReq.getString("doer_name");
                                doerID = onHoldReq.getString("doer_id");
                                if (!doers.contains(doerID)) {
                                    doers.add(doerID);
                                    id++;
                                    Log.e("id  ++ ", id + " PPPP");
                                }
                                serviceInfo.setDate(onHoldReq.getString("date"));
                                serviceInfo.setOmraName(onHoldReq.getString("name"));
                                serviceInfo.setNoCompletedOrder(jsonArray.getJSONObject(i).getInt("completed Orders"));
                                //  serviceInfo.setOrder_id(jsonArray.getJSONObject(i).getString("id"));
                                int ih = jsonArray.getJSONObject(i).getInt("completed Orders");
                                Log.e("completed ", ih + " --");
                                JSONArray orderInfo = jsonArray.getJSONObject(i).getJSONArray("doer");
                                for (int j = 0; j < orderInfo.length(); j++) {
                                    serviceInfo.setRating(orderInfo.getJSONObject(j).getString("review"));
                                    Log.e("raaating}}}", orderInfo.getJSONObject(j).getString("review"));
                                }
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
                String token = SharedPrefManager.getInstance(getApplicationContext()).getRequester().getToken();
                //   Log.e("token for user", token);
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
