package com.example.smoot.ajerwaojra.Fragments;

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

    ArrayList<UmraRequest> umraRequests = new ArrayList<UmraRequest>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterHD  adapterHD ;

    public doerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("doerhomefragment", "inside on create");
        View v=  inflater.inflate(R.layout.fragment_doer_home, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getAllRequeste();
        adapterHD= new RecyclerAdapterHD(umraRequests,this);
        recyclerView.setAdapter(adapterHD);
        return v;

    }

    public void getAllRequeste(){
        Log.d("doerhomefragment", "inside get all requests");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.URL_GET_REQUSTES_HD, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("doerhomefragment", "get responce");
                    JSONArray jsonArray = response.getJSONArray("orders");
                    int size = jsonArray.length();
                    Log.d("Array Size is ", " "+size);
                    // print the number of requests
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
                        umraRequest.setDoaa(object.getString("doaa"));
                        umraRequest.setUmraOwner(object.getString("name"));
                        umraRequest.setId(object.getInt("id"));
                        // add the umra object to the arrayList
                        umraRequests.add(umraRequest);
                        adapterHD.notifyDataSetChanged();

                    }
                    // getContext() may makes error


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
        UmraRequest umra =umraRequests.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",umra.getId());
        RequestDetailFragment f = new RequestDetailFragment();
        Log.d("Click", "Yes Clicked");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setArguments(bundle);
        ft.replace(R.id.container,f);
        ft.addToBackStack(null);
        ft.commit();

    }
}
