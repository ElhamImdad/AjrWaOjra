package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Adapter.RequestsAdapter;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestsFragment extends Fragment implements RequestsAdapter.MyViewHolder.onCardClick2{
    private TextView textViewUmraName;
    private RequestQueue mQueue;
    private ImageView addRequestBtn, norequestImg;
    Fragment newReqestFragment;
    CardView cardView;

    RecyclerView recyclerView ;
    RequestsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    ArrayList<OmraInfo>  umraListInProgress ;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_requests, container, false);
        addRequestBtn = v.findViewById(R.id.requestUmrabutton);
        umraListInProgress = new ArrayList<>();
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



            Log.e("Hello my request","Fragment class");
            recyclerView = v.findViewById(R.id.recyclerView11);
        mQueue = Volley.newRequestQueue(getContext());
        showRequest();
            layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter = new RequestsAdapter(umraListInProgress, this);

        recyclerView.setAdapter(adapter);
        norequestImg =(ImageView) v.findViewById(R.id.noRequestImg);
        Log.e("list size ", String.valueOf(umraListInProgress.size()));
        if (umraListInProgress.size() == 0){
            Log.e("visible","???");
            norequestImg.setVisibility(View.VISIBLE);
        }
        return v;
    }
  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //  OmraInfo omraInfo = (OmraInfo) recyclerView.getIt
        //open alart dialog page information for doer
        Fragment f = new RequestDetailsFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, f);
        ft.commit();

    }*/

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
                               omraInfoObject.setId(jsonObj.getJSONObject(i).getInt("id"));
                                umraListInProgress.add(omraInfoObject);
                               Log.e("my list is >---",umraListInProgress.toString());

                            }
                            if (umraListInProgress.size() != 0){
                                Log.e("invisible","invisible");
                                norequestImg.setVisibility(View.INVISIBLE);
                            }
                            adapter.notifyDataSetChanged();
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
                 //    headers.put("Content-Type", "application/json;  charset=UTF-8\"");
                headers.put("Accept","application/json");
                //     headers.put("Content-Type", "application/json");
                //   headers.put("X-Requested-With","XMLHttpRequest");

                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
            //    Log.e("token for user",token);
                headers.put("Authorization", "Bearer "+token);


                Log.e("request fragment--","header");
                return headers;
            }
        };
       request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }


    @Override
    public void onCardClickLis(int position) {
        OmraInfo umra =umraListInProgress.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",umra.getId());
        RequestDetailsFragment f = new RequestDetailsFragment();
        Log.d("Click card", "requester home");
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setArguments(bundle);
        ft.replace(R.id.container,f);
        ft.addToBackStack(null);
        ft.commit();

    }
}
