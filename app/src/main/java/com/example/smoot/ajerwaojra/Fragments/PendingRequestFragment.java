package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

public class PendingRequestFragment extends Fragment {
    private ImageView returnBTN;
    private TextView omraName,  doaaP;
    public PendingRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_request, container, false);
        omraName = view.findViewById(R.id.OmraNamePending);
        doaaP = view.findViewById(R.id.doaaPending);

        if (getArguments() != null) {
            omraName.setText(getArguments().getString("omraName"));
            doaaP.setText(getArguments().getString("doaa"));
        }

        returnBTN = view.findViewById(R.id.returnBtnPending);
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
