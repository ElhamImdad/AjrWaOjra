package com.example.smoot.ajerwaojra.Fragments;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.R;

public class FatwaFragment extends Fragment {
    ImageButton doer ;
    ImageButton requester;
    Fragment doerFrag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fatwa, container, false);
        doer = view.findViewById(R.id.doer);
        requester = view.findViewById(R.id.requester);
        doerFrag = new doerRegistrationFragment();
        doer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              setFragment(new doerRegistrationFragment());

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void setFragment(doerRegistrationFragment doerRegistrationFragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
       // ft.replace(R.id.container,doerRegistrationFragment);
        ft.commit();
    }
}
