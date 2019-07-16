package com.example.smoot.ajerwaojra.Helpers;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Models.OmraInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RService extends IntentService {
public static boolean serviceIsRun = false;

    public RService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        while (serviceIsRun){

            String url = "http://ajrandojra.website/api/offerNotification";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("list order respons>>---",response.toString());
                    try {
                        JSONArray jsonObj = response.getJSONArray("orders");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

    }

    // to allow client to connect with the service
/*    RequestQueue    mQueue;
    final IBinder mBinder = new LocalBinder();
    private ArrayList<OmraInfo>  umraListPending;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(this);
        umraListPending = new ArrayList<>();
        Toast.makeText(this,"Service starts ",Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        sendRequest();
        // bind service not start service
        Toast.makeText(this,"Inside onBind ",Toast.LENGTH_LONG).show();
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendRequest();
        return RService.START_NOT_STICKY;
    }

    public class LocalBinder extends Binder{
    RService getService (){
        return RService.this;
    }}

    public void sendRequest(){
    mQueue.start();
        String url = "http://ajrandojra.website/api/listRequestsR";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("list order respons>>---",response.toString());
                        try {

                            JSONArray jsonObj = response.getJSONArray("orders");
                            OmraInfo omraInfoObject;
                            for (int i = 0; i < jsonObj.length(); i++){

                                String status = jsonObj.getJSONObject(i).getJSONObject("order").getString("status_id");
                                omraInfoObject = new OmraInfo();
                                omraInfoObject.setId(jsonObj.getJSONObject(i).getJSONObject("order").getInt("id"));

                                if (status.equals("1")) {
                                    umraListPending.add(omraInfoObject); } }
                        } catch (JSONException excep) {
                            excep.printStackTrace();
                            Log.e("JSON Exception???",excep.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("volleyErro in list req>",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                String token = SharedPrefManager.getInstance(getApplicationContext()).getRequester().getToken();
                Log.e("token for this user",token+"-");
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };
        mQueue.add(request);
    }*/
}
