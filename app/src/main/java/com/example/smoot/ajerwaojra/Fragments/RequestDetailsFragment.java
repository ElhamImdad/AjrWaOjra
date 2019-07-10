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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestDetailsFragment extends Fragment {
    private ImageView returnBTN;
    private RequestQueue mQueue;
    private TextView omraName, doerName, omraDate, omraDuration, doaaDone;
    private TextView doaaTextViewProgress, doaaProgress, photoText,doaaTextViewDone, pageTitle;
    private ScrollView scrollViewProg,scrollViewDone;
    private int id;
    private String status, status2, status3;
    ArrayList <OmraInfo> doneList ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_details, container, false);

        omraName = view.findViewById(R.id.textViewOmraNameDone);
        doerName = view.findViewById(R.id.textDoeNameDone);
        omraDate = view.findViewById(R.id.textViewDateDone);
        omraDuration = view.findViewById(R.id.textViewTimeDone);
        doaaDone = view.findViewById(R.id.doaaDone);

        if (getArguments() != null) {
            Log.e("data in bindle-->",getArguments().getString("doerName")+"-");
            omraName.setText(getArguments().getString("omraName"));
            doerName.setText(getArguments().getString("doerName"));
            omraDate.setText(getArguments().getString("date"));
            omraDuration.setText(getArguments().getString("time"));
            doaaDone.setText(getArguments().getString("doaa"));
        }

       /* Bundle bundle = getArguments();
        id = bundle.getInt("id");
        status = bundle.getString("status");
        status2 = bundle.getString("status2");
        status3 = bundle.getString("status3");
        Log.e("status of omra","----"+status);
        Log.e("status of omra 2","----"+status2);
        if (status.equalsIgnoreCase("Done")){
            omraName.setText(bundle.getString("omraName"));
            doerName.setText(bundle.getString("doerOmraName"));
            omraDate.setText(bundle.getString("date"));
            omraDuration.setText(bundle.getString("time"));
            doaaDone.setText(bundle.getString("doaa"));
            doaaTextViewProgress.setVisibility(View.INVISIBLE);
            doaaProgress.setVisibility(View.INVISIBLE);
            scrollViewProg.setVisibility(View.INVISIBLE);
        }else if (status2.equalsIgnoreCase("In Progress")){
            Log.e("الصبر","ياارب");
            pageTitle.setText("عمرة قيد التنفيذ");
            omraName.setText(bundle.getString("omraName2"));
            doerName.setText(bundle.getString("doerOmraNam2e"));
            omraDate.setText(bundle.getString("date2"));
            omraDuration.setText(bundle.getString("time2"));
            photoText.setVisibility(View.INVISIBLE);
            doaaTextViewDone.setVisibility(View.INVISIBLE);
            scrollViewDone.setVisibility(View.INVISIBLE);
            doaaDone.setVisibility(View.INVISIBLE);

        }else if (status3.equalsIgnoreCase("Pending")){
            pageTitle.setText("عمرة قيد الانتظار");
            Log.e("رحمتك","ياارب");

        }*/


        returnBTN = view.findViewById(R.id.returnBtnDone);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsFragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.details, f);
                ft.commit();
            }
        });
     //   mQueue = Volley.newRequestQueue(getContext());

        return view;
    }

    private void showRequest(){
        mQueue.start();

        String url = "http://ajrandojra.website/api/listRequest";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("list order response>>>",response.toString());
                        try {

                            JSONObject orderInfo = response.getJSONObject("order");

                            omraName.setText(orderInfo.getString("name"));
                            Log.e("omra name >>.",(orderInfo.getString("name") ));
                            doerName.setText(orderInfo.getString("doer_name"));
                            omraDate.setText(orderInfo.getString("date"));
                            omraDuration.setText(orderInfo.getString("time"));
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
                headers.put("Accept","application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token in details",token);
                headers.put("Authorization", "Bearer "+token);


                Log.e("--------omrah request","header");
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
