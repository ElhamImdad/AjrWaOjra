package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.smoot.ajerwaojra.R;
import com.squareup.picasso.Picasso;

public class ZoomImageOmraFragment extends Fragment {
    private ImageView omraImage, arrowUpBTN;
    public ZoomImageOmraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoom_image_omra, container, false);
        omraImage = view.findViewById(R.id.largeImage);
        if (getArguments() != null) {
            String url = getArguments().getString("image");
           // omraImage.setBackgroundColor(getResources().getColor(R.color.gray));
            Picasso.with(getContext())
                    .load(url)
                    .into(omraImage);
        }
        arrowUpBTN = view.findViewById(R.id.arrowUpBtn);
        arrowUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("omraName", getArguments().getString("omraName"));
                    bundle.putString("doerName", getArguments().getString("doerName"));
                    bundle.putString("date", getArguments().getString("date"));
                    bundle.putString("time", getArguments().getString("time"));
                    bundle.putString("doaa", getArguments().getString("doaa"));
                    bundle.putStringArrayList("photos", getArguments().getStringArrayList("photos"));

                    RequestDetailsFragment f = new RequestDetailsFragment();
                    f.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.details, f);
                    ft.commit();
                }
            }
        });


        return view;
    }
}
