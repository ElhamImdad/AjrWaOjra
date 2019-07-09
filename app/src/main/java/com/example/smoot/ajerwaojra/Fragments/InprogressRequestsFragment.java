package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smoot.ajerwaojra.R;
public class InprogressRequestsFragment extends Fragment {
    private  int id2;

    public InprogressRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        id2 = bundle.getInt("id2");
        return inflater.inflate(R.layout.fragment_inprogress_requests, container, false);
    }
}
