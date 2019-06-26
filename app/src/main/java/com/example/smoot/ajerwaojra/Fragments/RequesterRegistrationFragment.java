package com.example.smoot.ajerwaojra.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequesterRegistrationFragment extends Fragment {

    private Spinner spinner;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone2;
    private Button confirm;
    private Spinner countrySpin;
    Fragment logInFrag;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");
    public RequesterRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v=  inflater.inflate(R.layout.fragment_requester_registration, container, false);
        textInputEmail = v.findViewById(R.id.textInputEmail);
        textInputPassword = v.findViewById(R.id.textInputPassword);
        textphone2 =  v.findViewById(R.id.editTextPhone);
        confirm = (Button) v.findViewById(R.id.confirm);
        countrySpin = (Spinner) v.findViewById(R.id.countrySpinner);
        getCountryList();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword() && isValidMobile()){
                    Log.d("every thing is fine", textphone2.getText().toString());
                }
            }
        });

        spinner = v.findViewById(R.id.spinner);
     //   ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.howDidKnowUs,android.R.layout.simple_spinner_item);
     //   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     //   spinner.setAdapter(adapter);
        Intent i = new Intent();
        i.getExtras();

        //
        TextView toLogIn =v.findViewById(R.id.logIn);
        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInFrag = new logInFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, logInFrag);
                ft.commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    private void getCountryList(){
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
       //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
       // countrySpin.setAdapter(adapter);

    }
    private boolean validateEmail(){
        String emailInput = textInputEmail.getText().toString().trim();
        if (emailInput.isEmpty()){
            textInputEmail.setError("حقل البريد الالكتروني فارغ");
            textInputEmail.requestFocus();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("الرجاء إدخال بريد الكتروني صحيح");
            textInputEmail.requestFocus();
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getText().toString().trim();
        if (passwordInput.isEmpty()){
            textInputPassword.setError("حقل كلمة السر فارغ");
            textInputPassword.requestFocus();
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            textInputPassword.setError("كلمة السر يجب أن تحتوي على حروف صغيرة وكبيرة وارقام");
            textInputPassword.requestFocus();
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private boolean isValidMobile() {
        String regexStr = "^\\+[0-9]{10,13}$";

        String number=textphone2.getText().toString();

        if(textphone2.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            textphone2.setError("ادخل رقم الجوال بشكل صحيح");
            textphone2.requestFocus();
            return false;
        }else {
            textphone2.setError(null);
            return true;
        }
    }

}
