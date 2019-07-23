package com.example.smoot.ajerwaojra.Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smoot.ajerwaojra.Helpers.SharedPrefManager;
import com.example.smoot.ajerwaojra.Helpers.URLs;
import com.example.smoot.ajerwaojra.Helpers.VolleySingleton;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class UploadImagesFragment extends Fragment {


    public UploadImagesFragment() {
        // Required empty public constructor
    }

    Button b1;

    Button close;

    ImageView image1;

    Bitmap imageBitmap1;

    String date;
    String totalTime;

    int id;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String encodedImage1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // request the permission
        if (ContextCompat.checkSelfPermission(getActivity()
                , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "permission done", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_images, container, false);

        Bundle bundle = getArguments();
        date = bundle.getString("date");
        totalTime = bundle.getString("totalTime");
        id = bundle.getInt("id");
        Log.e("id", " : " + id);

        close = v.findViewById(R.id.button4);

        b1 = v.findViewById(R.id.button5);


        image1 = v.findViewById(R.id.imageView10);
        image1.setVisibility(View.INVISIBLE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setVisibility(View.INVISIBLE);
                takePhoto();

            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap1 == null) {
                    takePhotoMessage();
                } else {
                    uploadUserImage();
                }
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            if (b1.isEnabled()) {
                imageBitmap1 = (Bitmap) extras.get("data");
                image1.setVisibility(View.VISIBLE);
                image1.setImageBitmap(imageBitmap1);
                b1.setEnabled(false);
            }
        }
    }

    private void takePhoto() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPTURE);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String ConvertBitmapToBase64Format(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imageString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imageString;

    }

    public void uploadUserImage() {
        Log.e("Raghad click button", " yes");
        Doer r = SharedPrefManager.getInstance(getContext()).getDoer();
        final String token = SharedPrefManager.getInstance(getContext()).getDoer().getDoerToken();
        Log.e("Token Shared", token);
        StringRequest request = new StringRequest(Request.Method.POST, URLs.UPL_FINISH_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob = new JSONObject(response);
                    String uu = ob.getString("message");
                    Log.e("message response", uu);
                    AlertDialog.Builder  alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("تأكيد....");
                    alert.setMessage("تم تحميل الصور وإنهاء الطلب بنجاح...شكرا لك.");
                    alert.setPositiveButton("حسنا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doerHomeFragment doerHome = new doerHomeFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.container, doerHome);
                            ft.commit();
                        }
                    });
                    alert.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("Myresponse", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error of volley ", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("date", date);
                param.put("time", totalTime);
                param.put("id", String.valueOf(id));
                encodedImage1 = ConvertBitmapToBase64Format(imageBitmap1);
                Log.i("encodedImage1", encodedImage1);
                param.put("image", encodedImage1);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + token);
                headers.put("token", token);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    public void takePhotoMessage() {
        android.support.v7.app.AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("عذرا......");
        alert.setMessage("يجب أن تقوم بارفاق صورة للحج ");
        alert.show();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("صلاحيات الوصول")
                    .setMessage("التطبيق يحتاج الوصول للكامير لالتقاط صور العمرة")
                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

        }
    }
}