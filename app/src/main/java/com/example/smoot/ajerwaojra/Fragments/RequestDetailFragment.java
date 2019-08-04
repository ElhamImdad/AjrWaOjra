package com.example.smoot.ajerwaojra.Fragments;


import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.smoot.ajerwaojra.Helpers.URLs.URL_OFFER_SERVICE;


public class RequestDetailFragment extends Fragment {
    private ImageView returnBTN;
    ImageView flag;
    TextView requester;
    TextView country;
    TextView date;
    TextView umraOwner;
    TextView doaa;
    Button offerService;
    int id;
    CheckBox checkBox;
    Button goSetting ;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public RequestDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_request_detail, container, false);
        flag = v.findViewById(R.id.countryFlag);
        requester = v.findViewById(R.id.requesterName);
        country = v.findViewById(R.id.country);
        date = v.findViewById(R.id.date);
        umraOwner = v.findViewById(R.id.umraOwner);
        doaa = v.findViewById(R.id.doaa);
        offerService = v.findViewById(R.id.btn_offerService);
        // get the data from the bundle
        Bundle bundle = getArguments();
        Log.e("Bundle ", bundle.toString());
        id = bundle.getInt("id");
        requester.setText(bundle.getString("requester"));
        country.setText(bundle.getString("country"));
        date.setText(bundle.getString("date"));
        umraOwner.setText(bundle.getString("umraOwner"));
        doaa.setText(bundle.getString("doaa"));
        String flagPath = bundle.getString("imagePath");
        if (flagPath != null) {
            Picasso.with(getContext())
                    .load(flagPath)
                    .into(flag);
        }
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        navigationView = getActivity().findViewById(R.id.nav_view);
        goSetting = v.findViewById(R.id.goSetting);
        goSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        final Bundle bundle1 = new Bundle();
        bundle1.putString("umraUner", bundle.getString("umraOwner"));
        bundle1.putString("doaa", bundle.getString("doaa"));
        bundle1.putInt("id", id);

        offerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        offerService();
                        isOfferedMessage();
                        //الكود اللي رح ياخذ بيانات الدور لصفحة التايمر
                       /* timerFragment f = new timerFragment();
                        f.setArguments(bundle1);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.disallowAddToBackStack();
                        ft.replace(R.id.container, f);
                        ft.commit();*/
                    } else {
                        showMessage();
                    }


            }
        });
        checkBox = v.findViewById(R.id.checkBox2);

  /*      umraDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Locale[] locale = Locale.getAvailableLocales();
                TimeZone tz1 = TimeZone.getTimeZone("AST");
                Calendar cal = Calendar.getInstance(tz1);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar,
                        object, year, month, day);
                // dialog.getWindow().setBackgroundDrawableResource(R.color.DarkGreen);
                dialog.show();

            }
        });*/
        returnBTN = v.findViewById(R.id.returnBtndoer);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doerHomeFragment f = new doerHomeFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, f);
                ft.commit();
            }
        });

        return v;
    }

    public void offerService() {
        Log.e("Raghad click button", " yes");
        Doer r = SharedPrefManager.getInstance(getContext()).getDoer();

        final String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
        Log.e("Token Shared", token);


        StringRequest request = new StringRequest(Request.Method.POST, URL_OFFER_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("message");
                    Log.e("Message of API :", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramas = new HashMap<>();
                paramas.put("token", token);
                paramas.put("id", String.valueOf(id));
                return paramas;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + token);
                headers.put("token", token);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //String text = getArguments().getString("message tag");
        // textView.setText(text);

    }

    public void showMessage() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا.....");
        alert.setMessage("يجب الموافقة على الشرط ");
        alert.show();
    }
    public void isOfferedMessage() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("تم عرض الخدمة على  "+requester.getText());
        alert.setMessage("سيتم إعلامك في حال موافقة  "+requester.getText()+"  لتبدأ بالعمرة ");
        alert.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             doerHomeFragment f = new doerHomeFragment();
             FragmentManager fm = getFragmentManager();
             FragmentTransaction ft = fm.beginTransaction();
             ft.replace(R.id.container,f);
             ft.commit();
            }
        });
        alert.show();
    }
}
