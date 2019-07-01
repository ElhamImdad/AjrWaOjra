package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Adapter.RequestsAdapter;
import com.example.smoot.ajerwaojra.Helpers.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestsFragment extends Fragment implements AdapterView.OnItemClickListener{
    private TextView textViewUmraName;
    private RequestQueue mQueue;
    private ImageView addRequestBtn;
    Fragment newReqestFragment;
    CardView cardView;

    RecyclerView recyclerView ;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<OmraInfo>  umraListInProgress = new ArrayList<>();

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_requests, container, false);
        addRequestBtn = v.findViewById(R.id.requestUmrabutton);
        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newReqestFragment = new OmrahRequestFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, newReqestFragment);
                ft.commit();
            }
        });
        if (umraListInProgress.size() == 0){
            ImageView imageview =(ImageView) v.findViewById(R.id.noRequestImg);
            imageview.setVisibility(View.VISIBLE);
        }else {


            recyclerView = v.findViewById(R.id.recyclerView11);

            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            mQueue = Volley.newRequestQueue(getContext());


            // call json method
               jsonParse();


        }
        // Inflate the layout for this fragment
        return v;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //  OmraInfo omraInfo = (OmraInfo) recyclerView.getIt
        //open alart dialog page information for doer

    }

    private void jsonParse(){
        String url = "";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("order");
                            OmraInfo omraInfoObject;
                            for (int i = 0; i < jsonArray.length(); i++){
                                omraInfoObject = new OmraInfo();
                                omraInfoObject.setDoerName(jsonArray.getJSONObject(i).getString("name"));

                                umraListInProgress.add(omraInfoObject);
                                adapter = new RequestsAdapter(getContext(), umraListInProgress);

                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException excep) {
                            excep.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


}
