package com.example.smoot.ajerwaojra.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

public class logInFragment extends Fragment {
    EditText logInEmail;
    EditText inputPassword;
    EditText phoneNumber ;
    Button loginButton;
    private ProgressBar progressBar;


    AlertDialog builder;

    public logInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);

        TextView goToRegistration = v.findViewById(R.id.toRegistration);
        goToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FatwaFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        });
        loginButton = v.findViewById(R.id.loginButton);
        inputPassword = v.findViewById(R.id.inputPassword);
        logInEmail = v.findViewById(R.id.logInEmail);
       // phoneNumber = v.findViewById(R.id.editTextPhone);

        progressBar = v.findViewById(R.id.progressBar3);
        //mobile = phoneNumber.getText().toString();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hellow ","hellow");
              //  login();
            }
        });

        return v;
    }


}
