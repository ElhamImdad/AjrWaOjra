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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DService extends IntentService {
    public static boolean DserviceIsRun = false;
    private static ArrayList<String> progressList = new ArrayList<>();
    String orderID;
    int id = 0;
    String requester;
    //

    public DService() {
        super("Dservice");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(10000);
            Log.e("inside sleep","yes");
        }
        catch (Exception e ){
            e.printStackTrace();
        }
        progressList.clear();
        Log.e("array size is ",progressList.size()+" Item");
        int p =0 ;
        while (DserviceIsRun){
            // insert code here
            Log.e("inside while Dservice", "yes");
            progressOrders();
            if (orderID != null && id > p) {
                Log.e("orderId", orderID);
                Log.e("doers", progressList.toString());
                id = 0;
                sendBroadcast(new Intent(this, myBroadCast.class).setAction("AcceptOffer").putExtra("Requester Name", requester));
            }
            try {
                Thread.sleep(30000);
                Log.e("inside sleep","yes");
            }
            catch (Exception e ){
                e.printStackTrace();
            }
        }
    }
    public void progressOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_DOER_INBROGRESS_ORDER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("InProgress in back Res", response);
                        try {
                            JSONObject obj = new JSONObject((response));
                            JSONArray doerArr = obj.getJSONArray("Doer InProgress Request");
                            for (int i = 0; i < doerArr.length(); i++) {
                                 requester= doerArr.getJSONObject(i).getString("requester_name");
                                 orderID = doerArr.getJSONObject(i).getString("id");
                                 if (!progressList.contains(orderID)){
                                progressList.add(orderID);
                                id++; }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch proo }},", e.toString());
                        }
                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("erroooor proo", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getApplicationContext()).getDoer().getDoerToken();
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
