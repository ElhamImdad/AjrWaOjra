package com.example.smoot.ajerwaojra.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Adapter.RequestsAdapter;
import com.example.smoot.ajerwaojra.Helpers.RService;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Models.OmraInfo;
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

public class RequestsFragment extends Fragment implements ServiceConnection{
    RService rService;
    ServiceConnection mConnection;
    Fragment newReqestFragment;
    CardView cardView;
    RecyclerView recyclerView ;
    RequestsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<OmraInfo>  umraListInProgress ;
    private ArrayList<OmraInfo>  umraListDone ;
    private ArrayList<OmraInfo>  umraListPending;
    private ArrayList<String> omraPhotoList;
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewUmraName;
    private RequestQueue mQueue;
    private ImageView  norequestImg, settingIcon;
    private Button addRequestBtn, pendingBTN, doneBTN, inProgressBTN;
    private LinearLayout linearLayout;

    public void startService(View view){
     //bindService(new Intent(getContext(),RService.class),mConnection, Context.BIND_AUTO_CREATE);
    }

    public RequestsFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_requests, container, false);


        /*Intent i = new Intent(getContext(), RService.class);
        getContext().startService(i);
*/



        linearLayout = v.findViewById(R.id.linearLayout);
        addRequestBtn = v.findViewById(R.id.requestUmrabutton);
        norequestImg = (ImageView) v.findViewById(R.id.noRequestImg);
        pendingBTN = v.findViewById(R.id.waitingBtn);
        doneBTN = v.findViewById(R.id.donBtn);
        inProgressBTN = v.findViewById(R.id.inProgressBtn);

        umraListInProgress = new ArrayList<>();
        umraListDone = new ArrayList<>();
        umraListPending = new ArrayList<>();

        swipeRefreshLayout = v.findViewById(R.id.swapRefreshLayout);

        recyclerView = v.findViewById(R.id.recyclerView11);
        mQueue = Volley.newRequestQueue(getContext());
        showRequest();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

       /* adapter = new RequestsAdapter(umraListPending, getContext());
        recyclerView.setAdapter(adapter);
        Log.e("addapter", adapter.toString()+"++");*/
        visibleNorequestImage();
       // pendingBTN.setSelected(true);
        inProgressBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImProgress();
                doneBTN.setBackgroundColor(getResources().getColor(R.color.white));
                doneBTN.setTextColor(getResources().getColor(R.color.lightGreen));

                inProgressBTN.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                inProgressBTN.setTextColor(getResources().getColor(R.color.white));

                pendingBTN.setBackgroundColor(getResources().getColor(R.color.white));
                pendingBTN.setTextColor(getResources().getColor(R.color.lightGreen));
            }
        });
        pendingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPending();
                doneBTN.setBackgroundColor(getResources().getColor(R.color.white));
                doneBTN.setTextColor(getResources().getColor(R.color.lightGreen));

                inProgressBTN.setBackgroundColor(getResources().getColor(R.color.white));
                inProgressBTN.setTextColor(getResources().getColor(R.color.lightGreen));

                pendingBTN.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                pendingBTN.setTextColor(getResources().getColor(R.color.white));
            }
        });
        pendingBTN.performClick();
        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
                doneBTN.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                doneBTN.setTextColor(getResources().getColor(R.color.white));

                inProgressBTN.setBackgroundColor(getResources().getColor(R.color.white));
                inProgressBTN.setTextColor(getResources().getColor(R.color.lightGreen));

                pendingBTN.setBackgroundColor(getResources().getColor(R.color.white));
                pendingBTN.setTextColor(getResources().getColor(R.color.lightGreen));
            }
        });

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
        if (umraListInProgress.size() != 0 ) {
            umraListInProgress.clear();
        }
        if (umraListPending.size() !=0){
            umraListPending.clear();
        }
        if (umraListDone.size() !=0){
            umraListDone.clear();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout.isRefreshing()) {

                    showRequest();
                    if (umraListInProgress.size() != 0 ) {
                        umraListInProgress.clear();
                    }
                    if (umraListPending.size() !=0){
                        umraListPending.clear();
                    }
                    if (umraListDone.size() !=0){
                        umraListDone.clear();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        settingIcon = v.findViewById(R.id.setting1);
        settingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequesterAccountFragment requesterAccount = new RequesterAccountFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, requesterAccount);
                ft.commit();
            }
        });

        return v;
    }
    private void clickImProgress(){
        adapter.updateData(umraListInProgress );
        adapter.notifyDataSetChanged();
    }
    private void clickPending(){
        adapter = new RequestsAdapter(umraListPending, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void clickDone(){
        adapter.updateData(umraListDone );
        adapter.notifyDataSetChanged();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showRequest(){
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
                               omraInfoObject.setDoerOmraName(jsonObj.getJSONObject(i).getJSONObject("order").getString("doer_name"));
                               omraInfoObject.setUmraName(jsonObj.getJSONObject(i).getJSONObject("order").getString("name"));
                               omraInfoObject.setIsStartOmra(jsonObj.getJSONObject(i).getJSONObject("order").getString("start"));
                               omraInfoObject.setDoaa(jsonObj.getJSONObject(i).getJSONObject("order").getString("doaa"));
                               omraInfoObject.setStatus(status);
                               omraInfoObject.setTime(jsonObj.getJSONObject(i).getJSONObject("order").getString("time"));
                                String dateFromApi = jsonObj.getJSONObject(i).getJSONObject("order").getString("date");
                               String gregorianString = "";
                               if (dateFromApi != null){
                                   Log.e("----", dateFromApi);
                                   gregorianString = convertDte(dateFromApi);
                               }
                               omraInfoObject.setDate(gregorianString);

                               if (status.equals("2")){
                                   umraListInProgress.add(omraInfoObject);
                                   Log.e("my list is >---",status+"\n\n\n");
                                   adapter.notifyDataSetChanged();

                               }else if ((status.equals("1")) || (status.equals("4"))){
                                   umraListPending.add(omraInfoObject);
                                  adapter.notifyDataSetChanged();
                               }else if (status.equals("3")){
                                   omraPhotoList = new ArrayList<>();
                                   JSONArray omraImages = jsonObj.getJSONObject(i).getJSONObject("order").getJSONArray("omra_images");
                                   Log.e("array length---", String.valueOf(omraImages.length()));
                                   String urlPhoto;
                                   for (int j = 0; j< omraImages.length(); j++){
                                       Log.e("array length---","----------------------------------------------");
                                       omraPhotoList.add(omraImages.getJSONObject(j).getString("path"));
                                   }
                                   omraInfoObject.setPhotos(omraPhotoList);
                                   umraListDone.add(omraInfoObject);
                                //   omraPhotoList.clear();
                                  adapter.notifyDataSetChanged();
                               }

                            }
                            visibleNorequestImage();

                        } catch (JSONException excep) {
                            excep.printStackTrace();
                            Log.e("JSON Exception???",excep.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                Log.e("volleyErro in list req>",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token for this user",token+"-");
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };
       request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
    private void visibleNorequestImage(){
        if (umraListInProgress.size() != 0 || umraListPending.size() !=0 || umraListDone.size()!=0){
            norequestImg.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        if (umraListInProgress.size() == 0 && umraListPending.size() ==0 && umraListDone.size()==0){
            norequestImg.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        }
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
        Log.e("lungth of array", String.valueOf(datearr.length));
        Log.e("lungth of --", datearr[1]);
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
        Log.e("mydate is :",newdATE);
        return newdATE;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        RService.LocalBinder binder = (RService.LocalBinder)service;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
