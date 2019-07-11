package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;
public class InprogressRequestsFragment extends Fragment {
    private ImageView returnBTN;
    private TextView omraName, doerName, omraDate, omraDuration, doaaP;

    public InprogressRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inprogress_requests, container, false);
        omraName = view.findViewById(R.id.textViewOmraNamePro);
        doerName = view.findViewById(R.id.textDoerNamePro);
        omraDate = view.findViewById(R.id.textViewDatePro);
        omraDuration = view.findViewById(R.id.textViewTimePro);
        doaaP = view.findViewById(R.id.doaaPro);

        if (getArguments() != null) {
            Log.e("data in bindle ","in progress-->"+getArguments().getString("doerName")+"-");
            omraName.setText(getArguments().getString("omraName"));
            doerName.setText(getArguments().getString("doerName"));
            omraDate.setText(getArguments().getString("date"));
            omraDuration.setText(getArguments().getString("time"));
            doaaP.setText(getArguments().getString("doaa"));
        }

        returnBTN = view.findViewById(R.id.returnBtnPro);
        returnBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsFragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.details, f);
                ft.commit();
            }
        });
        return view;
    }
}
