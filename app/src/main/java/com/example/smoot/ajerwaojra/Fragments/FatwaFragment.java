package com.example.smoot.ajerwaojra.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;

public class FatwaFragment extends Fragment {
    ImageButton doer ;
    ImageButton requester;
    Fragment doerFrag;
    Fragment requesterFrag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fatwa, container, false);
        doer = view.findViewById(R.id.doer);
        requester = view.findViewById(R.id.requester);
         // if the user is logged in so go to home page
        // TODO : change the fragment from requester to doer
        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()){
            Doer user = SharedPrefManager.getInstance(getContext()).getDoer();
           Fragment f = new RequesterHomeFragment();
           FragmentManager fm = getFragmentManager();
           FragmentTransaction ft = fm.beginTransaction();
           ft.replace(R.id.container, f);
           ft.commit();
        }
        // if the user is not logged in so go
       // else{ }



        doerFrag = new DoerRegistrationFragment();
        doer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doerFrag = new DoerRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, doerFrag);
                ft.commit();
            }
        });

        requester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesterFrag = new RequesterRegistrationFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, requesterFrag);
                ft.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }}


