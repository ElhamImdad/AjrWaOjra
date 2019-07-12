package com.example.smoot.ajerwaojra.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smoot.ajerwaojra.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFailFragment extends Fragment {

    Button b1;
    public RegistrationFailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v = inflater.inflate(R.layout.fragment_registration_fail, container, false);
      b1 = v.findViewById(R.id.Button);
      b1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FatwaFragment f = new FatwaFragment();
              FragmentManager fm = getFragmentManager();
              FragmentTransaction ft = fm.beginTransaction();
              ft.replace(R.id.container,f);
              ft.commit();
          }
      });
        return v;
    }

}
