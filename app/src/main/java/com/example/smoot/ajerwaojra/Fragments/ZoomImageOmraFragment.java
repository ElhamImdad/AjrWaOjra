package com.example.smoot.ajerwaojra.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smoot.ajerwaojra.Helpers.SaveImage;
import com.example.smoot.ajerwaojra.R;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class ZoomImageOmraFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private ImageView omraImage, arrowUpBTN, downloadImage;
    private String url;
    public ZoomImageOmraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE :
            {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getContext(), "تم اعطاء صلاحيةالوصول الى الصور والوسائط", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "تم رفض طلب الإذن", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoom_image_omra, container, false);
        omraImage = view.findViewById(R.id.largeImage);
        if (getArguments() != null) {
             url = getArguments().getString("image");
           // omraImage.setBackgroundColor(getResources().getColor(R.color.gray));
            Picasso.with(getContext())
                    .load(url)
                    .into(omraImage);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
        downloadImage = view.findViewById(R.id.cloudBtn);
        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "يجب الموافقة على منح التطبيق إذن الوصول إلى الصور والوسائط والملفات على جهازك", Toast.LENGTH_LONG).show();
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                }else {
                    AlertDialog dialog = new SpotsDialog(getContext());
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getContext())
                            .load(url)
                            .into(new SaveImage(getContext(),
                                    dialog ,
                                    getActivity().getContentResolver(),
                                    fileName,
                                    "Image Discribtion"));
                }

            }
        });

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
