package com.example.smoot.ajerwaojra.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Requester;
import com.example.smoot.ajerwaojra.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestDetailsFragment extends Fragment {
    private ImageView returnBTN, image_1, image_2, image_3 ,setting;
    private RequestQueue mQueue;
    private TextView omraName, doerName, omraDate, omraDuration, doaaDone, review;
    private Button RateClose;
    private RatingBar RatingBar;
  //  private int rate;
    private int requestID, rateNum;
    private String help []=new String[1];
    private String doer_ID;
    String helperstring;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_request_details, container, false);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        omraName = view.findViewById(R.id.textViewOmraNameDone);
        doerName = view.findViewById(R.id.textDoeNameDone);
        omraDate = view.findViewById(R.id.textViewDateDone);
        omraDuration = view.findViewById(R.id.textViewTimeDone);
        doaaDone = view.findViewById(R.id.doaaDone);
        review = view.findViewById(R.id.noStar);
        setting = view.findViewById(R.id.settingDone);
        image_1 = view.findViewById(R.id.omraImage1);
        image_2 = view.findViewById(R.id.omraImage2);
        image_3 = view.findViewById(R.id.omraImage3);
        image_2.setVisibility(View.INVISIBLE);
        image_3.setVisibility(View.INVISIBLE);
        String urlImage1, urlImage2, urlImage3;
           RateClose = view.findViewById(R.id.rateCloseB);
           RatingBar = view.findViewById(R.id.ratingBarB);



        final ArrayList<String> urlsPHOTOS;
        final NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        if (getArguments() != null) {
            Log.e("data in bindle-->", getArguments().getString("doerName") + "-");
            omraName.setText(getArguments().getString("omraName"));
            doerName.setText(getArguments().getString("doerName"));
            omraDate.setText(getArguments().getString("date"));
            omraDuration.setText(getArguments().getString("time"));
            doaaDone.setText(getArguments().getString("doaa"));
            review.setText(getArguments().getString("review"));
            urlsPHOTOS = getArguments().getStringArrayList("photos");


            requestID = getArguments().getInt("id");
            doer_ID = getArguments().getString("doer_id");
            Log.e("doerID ,OrderID", doer_ID + " / " + requestID);
            switch (urlsPHOTOS.size()) {
         /*       case 3 :
                    image_3.setVisibility(View.VISIBLE);
                    Picasso.with(getContext())
                            .load(urlsPHOTOS.get(2))
                            .into(image_3);
                    image_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle3 = new Bundle();

                            bundle3.putString("omraName",getArguments().getString("omraName"));
                            bundle3.putString("doerName",getArguments().getString("doerName"));
                            bundle3.putString("date",getArguments().getString("date"));
                            bundle3.putString("time",getArguments().getString("time"));
                            bundle3.putString("doaa",getArguments().getString("doaa"));
                            bundle3.putString("review",getArguments().getString("review"));
                            bundle3.putStringArrayList("photos", getArguments().getStringArrayList("photos"));

                            bundle3.putString("image",urlsPHOTOS.get(2));
                            ZoomImageOmraFragment image3Obj = new ZoomImageOmraFragment();
                            image3Obj.setArguments(bundle3);
                            setFragment(image3Obj);

                        }
                    });
                case 2 :
                    image_2.setVisibility(View.VISIBLE);
                    Picasso.with(getContext())
                            .load(urlsPHOTOS.get(1))
                            .into(image_2);
                    image_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle2 = new Bundle();

                            bundle2.putString("omraName",getArguments().getString("omraName"));
                            bundle2.putString("doerName",getArguments().getString("doerName"));
                            bundle2.putString("date",getArguments().getString("date"));
                            bundle2.putString("time",getArguments().getString("time"));
                            bundle2.putString("doaa",getArguments().getString("doaa"));
                            bundle2.putString("review",getArguments().getString("review"));
                            bundle2.putStringArrayList("photos", getArguments().getStringArrayList("photos"));

                            bundle2.putString("image",urlsPHOTOS.get(1));
                            ZoomImageOmraFragment image2Obj = new ZoomImageOmraFragment();
                            image2Obj.setArguments(bundle2);
                            setFragment(image2Obj);

                        }
                    });*/
                case 1:
                    Picasso.with(getContext())
                            .load(urlsPHOTOS.get(0))
                            .into(image_1);
                    image_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle1 = new Bundle();

                            bundle1.putString("omraName", getArguments().getString("omraName"));
                            bundle1.putString("doerName", getArguments().getString("doerName"));
                            bundle1.putString("date", getArguments().getString("date"));
                            bundle1.putString("time", getArguments().getString("time"));
                            bundle1.putString("doaa", getArguments().getString("doaa"));
                            bundle1.putString("review", getArguments().getString("review"));
                            bundle1.putStringArrayList("photos", getArguments().getStringArrayList("photos"));

                            bundle1.putString("image", urlsPHOTOS.get(0));
                            ZoomImageOmraFragment image1Obj = new ZoomImageOmraFragment();
                            image1Obj.setArguments(bundle1);
                            setFragment(image1Obj);

                        }
                    });

            }
        }

        returnBTN = view.findViewById(R.id.returnBtnDone);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
             /*   RequestsFragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.details, f);
                ft.commit();*/
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        checkRating(requestID);
        return view;
    }
    public void setFragment(Fragment f){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.details,f);
        ft.commit();
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
           //     Log.e("token in details",token);
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

    public void uploadUserImage( final int id) {
         final float rate = RatingBar.getRating();

        Log.e("ratingBar", String.valueOf(rate)+"---");
        Log.e("click button rateClose", " yes");
        Requester r = SharedPrefManager.getInstance(getContext()).getRequester();
        final String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
        Log.e("Token Shared", token);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.UPL_RATING_DOER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("333333666333333 rating", response);
                    JSONObject ob = new JSONObject(response);
                  //  rateNum = ob.getJSONObject("doer review").getInt("rating");
                  //  Log.e("mmmmessage rating", String.valueOf(rateNum));
                    AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("تأكيد....");
                    alert.setMessage("تم التقييم وإنهاء الطلب بنجاح...شكرا لك.");
                    alert.setPositiveButton("حسنا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RequestsFragment requesterHome = new RequestsFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.details, requesterHome);
                            ft.commit();

                        }

                    });
                    alert.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("response of rateClose", "" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errrror volley  Rate", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("review",String.valueOf(rate));
                param.put("id", String.valueOf(id));
                Log.e("dooooer_id", String.valueOf(id)+"?");
                Log.e("do4444er_id", String.valueOf(rate)+"?");
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token for user", token);
                headers.put("Authorization", "Bearer " + token);
             //   headers.put("token", token);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
    public void checkRating( final int id) {


      //  Log.e("ratingBar", String.valueOf(rate));
        Log.e("click button rateClose", " yes");
        Requester r = SharedPrefManager.getInstance(getContext()).getRequester();
        final String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
        Log.e("Token Shared", token);

        StringRequest request = new StringRequest(Request.Method.POST, URLs.UPL_CHECK_RATING_DOER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("3333333999999 rating", response);
                    JSONObject ob = new JSONObject(response);
                    help[0] = ob.getString("Rating");
                    Log.e("MMMMkessage rating", help[0]);

                    if (help[0] != null){
                        Log.e("whaaaaat i'm gonna die",help[0]);
                        if (help[0].equalsIgnoreCase("there is rating")) {
                            RatingBar.setVisibility(View.INVISIBLE);
                            RateClose.setVisibility(View.INVISIBLE);
                        } else {
                            RateClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    uploadUserImage(requestID);
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Tresponse of rateClose", "" + e.toString());
                }
            //    helperstring = help[0];
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Terrrror volley  Rate", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put("id", String.valueOf(id));
                Log.e("Oooooerder_id", String.valueOf(id)+"?");
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                String token = SharedPrefManager.getInstance(getContext()).getRequester().getToken();
                Log.e("token for user", token);
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);

    }

}
