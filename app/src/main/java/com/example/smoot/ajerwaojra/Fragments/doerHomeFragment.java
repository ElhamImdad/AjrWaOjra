package com.example.smoot.ajerwaojra.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Adapter.RecyclerAdapterHD;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.UmraRequest;
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

public class doerHomeFragment extends Fragment implements RecyclerAdapterHD.MyViewHolder.onCardClick {

    ArrayList<UmraRequest> umraRequests = new ArrayList<UmraRequest>();
    private static ArrayList<String> inHoldList = new ArrayList<>();
    private  static ArrayList<String> progressList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterHD  adapterHD ;
    SwipeRefreshLayout swipeRefreshLayout;
    Button goSetting;

    public doerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("doerhomefragment", "inside on create");
        View v=  inflater.inflate(R.layout.fragment_doer_home, container, false);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        final NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

        goSetting = v.findViewById(R.id.goSetting);
        recyclerView = v.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(),1);
       // recyclerView.setLayoutManager(new CustomLinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = v.findViewById(R.id.swapRefresh);
       // swipeRefreshLayout.setColorSchemeColors(c1 , c2 , c3 );
        getAllRequeste();
        // it is so importent to clear the array list in order to prevent items from duplication
        if (umraRequests.size() != 0){
            Log.e("size",String.valueOf(umraRequests.size()));
            umraRequests.clear();}

        adapterHD= new RecyclerAdapterHD(getContext(),umraRequests,this);
        recyclerView.setAdapter(adapterHD);
        adapterHD.notifyDataSetChanged();

        // set onRefresh method
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(swipeRefreshLayout.isRefreshing()){

                getAllRequeste();
                    if (umraRequests.size() != 0){  umraRequests.clear();}
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        inholdOrders();
        progressOrders();
        goSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawerLayout.openDrawer(navigationView);
            }
        });

        return v;

    }

    public void getAllRequeste(){
        Log.d("doerhomefragment", "inside get all requests");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLs.URL_GET_REQUSTES_HD, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("orders");
                    int size = jsonArray.length();

                    Log.d("Array Size is ", " "+size);
                    // print the number of requests
                    for (int i =0; i<size; i++ ) {
                        UmraRequest umraRequest = new UmraRequest();
                        // get objects from the array
                        JSONObject object = jsonArray.getJSONObject(i);
                        // get the data from the object
                        // set the attributes of the umra object
                       // umraRequest.setCountry(object.getString("country"));
                        umraRequest.setCountry_id(object.getString("country_id"));
                        umraRequest.setCountryFlagImagePath(object.getJSONObject("country").getString("image"));
                        umraRequest.setCountry(object.getJSONObject("country").getString("name"));
                     //   umraRequest.setDate(object.getString("date"));
                        umraRequest.setRequesterName(object.getString("requester_name"));
                        umraRequest.setDoaa(object.getString("doaa"));
                        umraRequest.setUmraOwner(object.getString("name"));
                        umraRequest.setId(object.getInt("id"));

                        String dateFromApi = object.getString("date");
                        String gregorianString = "";
                        if (dateFromApi != null){
                            gregorianString = convertDte(dateFromApi);
                        }
                        umraRequest.setDate(gregorianString);
                        // add the umra object to the arrayList

                      //  int initialSize = umraRequests.size();
                        umraRequests.add(umraRequest);
                      //  adapterHD.notifyItemRangeInserted(initialSize, umraRequests.size()-1);
                    }
                    adapterHD.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             error.printStackTrace();
            }
        }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
    public void inholdOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_DOER_INHOLD_ORDER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hooooold", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));
                            JSONArray doerArr = obj.getJSONArray("Doer onHold Request");
                            for (int i=0 ; i< doerArr.length(); i++){
                                String nameD = doerArr.getJSONObject(i).getString("doer_name");
                                Log.e("hoooold name>>", nameD);
                                inHoldList.add(nameD);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("catch hoold }},", e.toString());
                        }
                    }//end onResponse
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("erroooor hooold", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
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
    public void progressOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPL_DOER_INBROGRESS_ORDER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("proo", response.toString());
                        try {
                            JSONObject obj = new JSONObject((response));
                            JSONArray doerArr = obj.getJSONArray("Doer InProgress Request");
                            for (int i=0 ; i< doerArr.length(); i++){
                                String nameP = doerArr.getJSONObject(i).getString("doer_name");
                                Log.e("prooo name>>", nameP);
                                progressList.add(nameP);
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
                String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
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
    @Override
    public void onCardClickLis(int position) {
        inholdOrders();
        progressOrders();
        Log.e("holdSize--", String.valueOf(inHoldList.size())+"??");
        Log.e("nameInArrarString ***",inHoldList.toString()+"   :)");
        if (inHoldList.size() ==0 && progressList.size() ==0) {
            UmraRequest umra = umraRequests.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id", umra.getId());
            Log.e("id", " " + umra.getId());
            bundle.putString("requester", umra.getRequesterName());
            bundle.putString("date", umra.getDate());
            bundle.putString("country", umra.getCountry());
            bundle.putString("doaa", umra.getDoaa());
            bundle.putString("umraOwner", umra.getUmraOwner());
            bundle.putString("country_id", umra.getCountry_id());
            bundle.putString("imagePath", umra.getCountryFlagImagePath());
            RequestDetailFragment f = new RequestDetailFragment();
            f.setArguments(bundle);
            Log.d("Click", "Yes Clicked");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, f);
            ft.addToBackStack(null);
            ft.commit();
        }else if (inHoldList.size() !=0){
            AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
            alert.setTitle("عذرا......");
            alert.setMessage("لديك طلب عمرة قيد الموافقة");
            alert.show();
            inHoldList.clear();
        }else {
            AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
            alert.setTitle("عذرا......");
            alert.setMessage("لديك طلب عمرة سابق لم يتم تنفيذه");
            alert.show();
            progressList.clear();
        }

    }
    public Boolean isImpty(){
        if (progressList.size() != 0){
            return false;
        }else {
            return true;
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

}
