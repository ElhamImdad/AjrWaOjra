package com.example.smoot.ajerwaojra.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Countries;
import com.example.smoot.ajerwaojra.Models.Requester;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequesterRegistrationFragment extends Fragment {

    private Spinner howKnowus;
    private EditText textInputEmail, name, textInputPassword, textphone2;
    private ArrayList<Countries> goodModelArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private Button confirmBtn;
    private ProgressBar progressBar;
    private Spinner countrySpin;
    final String[] id = {""};
    Fragment logInFrag;
    String token;
    Context context;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                 //   "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");

    public RequesterRegistrationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public Context getContext() {
        return context= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_requester_registration, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        textInputEmail = v.findViewById(R.id.textInputEmail);
        textInputPassword = v.findViewById(R.id.textInputPassword);
        textphone2 = v.findViewById(R.id.editTextPhone);
        name = v.findViewById(R.id.requesterName);

        howKnowus = v.findViewById(R.id.spinner);
        countrySpin = v.findViewById(R.id.countrySpinner);

        progressBar = v.findViewById(R.id.progressBar);
        confirmBtn = v.findViewById(R.id.confirm);
        id[0]=getCountryListFromApi();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword() && isValidMobile()) {
                    registerRequester(); }
            }
        });

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.howDidKnowUs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.howDidKnowUs, R.layout.textview_with_font_change);
        adapter.setDropDownViewResource(R.layout.textview_with_font_change);
        howKnowus.setAdapter(adapter);
        Intent i = new Intent();
        i.getExtras();
        TextView toLogIn = v.findViewById(R.id.logIn);
        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInFrag = new logInFragment();
              //  UploadImagesFragment f = new UploadImagesFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, logInFrag);
                ft.commit();
            }
        });

        return v;
    }

    private void registerRequester() {
        final String phoneNomber = textphone2.getText().toString().trim();
        final String email = textInputEmail.getText().toString().trim();
        final String password = textInputPassword.getText().toString().trim();
        final String Name = name.getText().toString().trim();
        final String country = id[0];
        Log.e("country int >>", country +"----");
        final String howKnowUs = howKnowus.getSelectedItem().toString().trim();
        final String role = "Requester";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                  //      Log.v("Res", response.toString());
                        try {

                            JSONObject ob = new JSONObject(response);
                            token = ob.getString("access_token");
                            Log.v("access_token", token);
                            String countryId = ob.getJSONObject("message").getString("country_id");
                            Requester requester = new Requester(token);
                            requester.setCountry(countryId);
                            SharedPrefManager.getInstance(getContext()).userLogin(requester);
                            Toast.makeText(getContext(), "تم تسجيلك بنجاح", Toast.LENGTH_LONG).show();
                            Fragment f = new RequestsFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, f);
                            ft.commit();
                           /*
                            Requester RequestUser = SharedPrefManager.getInstance(getContext()).getRequester();
                            */
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("new error>>",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error response", error.toString());
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNomber);
                params.put("email", email);
                params.put("password", password);
                params.put("name", Name);
                params.put("country_id", String.valueOf(807));
                params.put("knowUs", howKnowUs);
                params.put("role", role);
                params.put("payment", String.valueOf(0));
                params.put("review", String.valueOf(0));

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
//    private void getCountryList() {
//        Locale[] locales = Locale.getAvailableLocales();
//        ArrayList<String> countries = new ArrayList<String>();
//        for (Locale locale : locales) {
//            String country = locale.getDisplayCountry();
//            if (country.trim().length() > 0 && !countries.contains(country)) {
//                countries.add(country);
//            }
//        }
//        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countries);
//        countrySpin.setAdapter(adapter);
//
//    }
    private String getCountryListFromApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_COUNTRY,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceType")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                     //   Log.i("ccccountry>>", response);
                        try {

                            JSONObject obj = new JSONObject(response);
                            goodModelArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("countries");

                            for (int i = 0; i < dataArray.length(); i++) {

                                Countries playerModel = new Countries();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                playerModel.setName(dataobj.getString("name"));
                                playerModel.setId(dataobj.getString("id"));
                                playerModel.setImgURL(dataobj.getString("image"));

                                goodModelArrayList.add(playerModel);
                            }

                            for (int i = 0; i < goodModelArrayList.size(); i++){
                                names.add(goodModelArrayList.get(i).getName().toString());
                            }

                       //     final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, names);
                        //    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    context,R.layout.textview_with_font_change,names
                            ){
                                public View getDropDownView(int position, View convertView,ViewGroup parent) {

                                    View v = super.getDropDownView(position, convertView,parent);

                                    ((TextView) v).setGravity(Gravity.RIGHT);

                                    return v;

                                }
                            };
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.textview_with_font_change);
                            countrySpin.setAdapter(spinnerArrayAdapter);
                            final String[] selectedItemText = new String[1];

                            countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                                     selectedItemText[0] = (String) parent.getItemAtPosition(position);
                                    spinnerArrayAdapter.notifyDataSetChanged();
                                    // Notify the selected item text
                                 //   Toast.makeText(getContext(), "Selected : " + selectedItemText[0], Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            selectedItemText[0] = countrySpin.getSelectedItem().toString();

                            for (int i = 0; i < goodModelArrayList.size(); i++){
                                if (selectedItemText[0].equals(goodModelArrayList.get(i).getName().toString())){
                                    countrySpin.setSelection(i);
                                    id[0] = goodModelArrayList.get(i).getId();
                                }
                            }

                            Log.e("iuuuuuuuuuuuuu",id[0]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), "لايوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);
Log.e("id of country method ",id[0]+"??");
    return id[0].toString();
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("حقل البريد الالكتروني فارغ");
            textInputEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("الرجاء إدخال بريد الكتروني صحيح");
            textInputEmail.requestFocus();
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("حقل كلمة السر فارغ");
            textInputPassword.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("كلمة السر يجب أن تحتوي على حروف صغيرة وارقام");
            textInputPassword.requestFocus();
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean isValidMobile() {
        String regexStr = "^\\+[0-9]{10,13}$";
//|| number.matches(regexStr) == false
        String number = textphone2.getText().toString();
      /*  if (!textphone2.getText().toString().contains("+")) {
            textphone2.setError("يجب أن يحتوي على + ");
            textphone2.requestFocus();
            return false;
        }else*/ if (textphone2.getText().toString().length() < 10 || number.length() > 13 ) {
            textphone2.setError("ادخل رقم الجوال بشكل صحيح");
            textphone2.requestFocus();
            return false;
        } else {
            textphone2.setError(null);
            return true;
        }
    }

}
