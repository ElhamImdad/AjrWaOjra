package com.example.smoot.ajerwaojra.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.smoot.ajerwaojra.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;

public class ActivityRequesterReg extends Activity {
    private Spinner spinner;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone2;
    private Button confirm;
    private Spinner countrySpin;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_reg);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textphone2 =  findViewById(R.id.editTextPhone);
        confirm = (Button)findViewById(R.id.confirm);
        countrySpin = (Spinner)findViewById(R.id.countrySpinner);
        getCountryList();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword() && isValidMobile()){
                    Log.d("every thing is fine", textphone2.getText().toString());
                }
            }
        });

        spinner =findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.howDidKnowUs,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Intent i = new Intent();
        i.getExtras();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countrySpin.setAdapter(adapter);

    }
}
