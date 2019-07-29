package com.example.smoot.ajerwaojra.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.R;

public class PendingRequestFragment extends Fragment {
    private ImageView returnBTN, settingIcon;
    private TextView omraName,  doaaP;
    public PendingRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_request, container, false);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        final NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

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
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
          /*      RequestsFragment f = new RequestsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.details, f);
                ft.commit();*/
            }
        });
        settingIcon = view.findViewById(R.id.setting2);
        settingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });
        return view;
    }

}
