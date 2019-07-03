package com.example.smoot.ajerwaojra.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Adapter.RecyclerAdapterHD;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Models.UmraRequest;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class doerHomeFragment extends Fragment implements RecyclerAdapterHD.MyViewHolder.onCardClick {


    String url = URLs.URL_GET_REQUSTES_HD;
    ArrayList<UmraRequest> umraRequests = new ArrayList<UmraRequest>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public doerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = container.findViewById(R.id.recyclerView);
        return inflater.inflate(R.layout.fragment_doer_home, container, false);
    }

    public void getAllRequeste(){
        final RecyclerAdapterHD adapterHD = new RecyclerAdapterHD(umraRequests,this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("orders");
                    int size = jsonArray.length();
                    // print the number of requests
                    Toast.makeText(getContext(),size+" ",Toast.LENGTH_LONG).show();
                    for (int i =0; i<size; i++ ){
                        UmraRequest umraRequest= new UmraRequest();
                        // get objects from the array
                        JSONObject object = jsonArray.getJSONObject(i);
                        // get the data from the object
                        // set the attributes of the umra object
                        umraRequest.setCountry(object.getString("country"));
                        umraRequest.setCountryFlagImagePath(object.getString("image"));
                        umraRequest.setDate(object.getString("date"));
                        umraRequest.setRequesterName(object.getString("requester_name"));
                        // add the umra object to the arrayList
                        umraRequests.add(umraRequest);
                    }
                    // getContext() may makes error

                    layoutManager = new GridLayoutManager(getContext(),1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapterHD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }


    @Override
    public void onCardClickLis(int position) {
        umraRequests.get(position);
        Log.d("Click", "Yes Clicked");
        Fragment f = new RequestDetailFragment();
        FragmentManager fm = f.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,f);
        ft.commit();
    }
}
