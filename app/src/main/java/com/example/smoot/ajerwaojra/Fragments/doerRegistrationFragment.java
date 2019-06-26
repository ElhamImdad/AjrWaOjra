package com.example.smoot.ajerwaojra.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.smoot.ajerwaojra.R;

import java.util.regex.Pattern;

public class doerRegistrationFragment extends Fragment {

    Spinner spinner;
    private EditText textName;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone2;
    private Button confirm;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");
    public doerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_doer_registration, container, false);
        spinner =view.findViewById(R.id.spinner);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.howDidKnowUs,android.R.layout.simple_spinner_item);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        Intent i = new Intent();
        i.getExtras();

        textName = view.findViewById(R.id.doerName);
        textInputEmail = view.findViewById(R.id.doerEmail);
        textInputPassword = view.findViewById(R.id.inputPassword);
        textphone2 =  view.findViewById(R.id.doaa);
        confirm = (Button) view.findViewById(R.id.doerRegisterButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword() && isValidMobile()){
                    Log.d("every thing is fine", textphone2.getText().toString());
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
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
            textInputPassword.setError("كلمة السر ضعيفة");
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
