package com.example.smoot.ajerwaojra.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DoerRegistrationFragment extends Fragment {
  //  private FusedLocationProviderClient client;
    private Spinner howKnowus;
    private EditText textName;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private EditText textphone;
    private ProgressBar progressBar;
    private Button confirm;
    private String url = "http://testtamayoz.tamayyozz.net/api/register";
    Fragment logInFrag;
    String exactLocation;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    // "(?=.*[@#$%^&+=!])" +
                    "(?=\\S+$).{4,}$");

    public DoerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doer_registration, container, false);
        howKnowus = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.howDidKnowUs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howKnowus.setAdapter(adapter);

        Intent i = new Intent();
        i.getExtras();

        textName = view.findViewById(R.id.doerName);
        textInputEmail = view.findViewById(R.id.textInputEmaildoer);
        textInputPassword = view.findViewById(R.id.inputPassword);

        textphone = view.findViewById(R.id.phoneNumber);
        //  progressBar = view.findViewById(R.id.progressBarr);
        confirm = view.findViewById(R.id.doerRegisterButton);
        requestPermission();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword() && isValidMobile()) {
                //    doerRegister();
                    Fragment f = new RequestsFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, f);
                    ft.commit();

            }
            }


        });


        // logIn
        TextView toLogIn = view.findViewById(R.id.logIn);
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
        return view;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
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

        String number = textphone.getText().toString();

        if (textphone.getText().toString().length() < 10 || number.length() > 13 || number.matches(regexStr) == false) {
            textphone.setError("ادخل رقم الجوال بشكل صحيح");
            textphone.requestFocus();
            return false;
        } else if (!textphone.getText().toString().contains("+")) {
            textphone.setError("يجب أن يحتوي على + ");
            textphone.requestFocus();
            return false;
        } else {
            textphone.setError(null);
            return true;
        }
    }

    private void checkLocation() {


    }
}



