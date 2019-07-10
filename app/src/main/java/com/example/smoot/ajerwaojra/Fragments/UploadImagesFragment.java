package com.example.smoot.ajerwaojra.Fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
    Button b2;
    Button b3;

    Button close;

    ImageView image1;
    ImageView image2;
    ImageView image3;

    Bitmap imageBitmap1;
    Bitmap imageBitmap2;
    Bitmap imageBitmap3;

    String date ;
    String totalTime;

    int id;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_images, container, false);

        Bundle bundle = getArguments();
        date = bundle.getString("date");
        totalTime = bundle.getString("totalTime");
        id = bundle.getInt("id");
        Log.e("id"," : "+id);

        close = v.findViewById(R.id.button4);

        b1 = v.findViewById(R.id.button5);
        b2 = v.findViewById(R.id.button6);
        b2.setVisibility(View.INVISIBLE);
        b3 = v.findViewById(R.id.button7);
        b3.setVisibility(View.INVISIBLE);

        image1 = v.findViewById(R.id.imageView10);
        image1.setVisibility(View.INVISIBLE);
        image2 = v.findViewById(R.id.imageView11);
        image2.setVisibility(View.INVISIBLE);
        image3 = v.findViewById(R.id.imageView12);
        image3.setVisibility(View.INVISIBLE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setVisibility(View.INVISIBLE);
                takePhoto();
                b2.setVisibility(View.VISIBLE);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setVisibility(View.INVISIBLE);
                takePhoto();
                b3.setVisibility(View.VISIBLE);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setVisibility(View.INVISIBLE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.e("bundle ", extras.toString());
            if (b1.isEnabled()) {
                imageBitmap1 = (Bitmap) extras.get("data");
                Log.e("bit",imageBitmap1.toString());
                b2.setEnabled(false);
                b3.setEnabled(false);
                image1.setVisibility(View.VISIBLE);
                image1.setImageBitmap(imageBitmap1);
                b2.setEnabled(true);
                b1.setEnabled(false);
            } else if (b2.isEnabled()) {
                imageBitmap2 = (Bitmap) extras.get("data");
                image2.setVisibility(View.VISIBLE);
                image2.setImageBitmap(imageBitmap2);
                b3.setEnabled(true);
                b2.setEnabled(false);
            } else if (b3.isEnabled()) {
                imageBitmap3 = (Bitmap) extras.get("data");
                image3.setVisibility(View.VISIBLE);
                image3.setImageBitmap(imageBitmap3);
            }
        }
    }

    private void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String getStringImage(Bitmap bitmap) {
        Log.i("BitMap", "" + bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
                    Log.e("message response",uu);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("Myresponse", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.e("error of volley ",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
          /*      param.put("date" , date);
                param.put("time",totalTime);
                param.put("id",String.valueOf(id));
                Log.e("erdf","jhjhjhjhjh");
                String image1 = getStringImage(imageBitmap1);
                Log.i("Mynewsam", "" + image1);
                param.put("image", image1);
                if (imageBitmap2 != null) {
                    String image2 = getStringImage(imageBitmap2);
                    Log.i("Mynewsam", "" + image2);
                    param.put("image", image2);
                    if (imageBitmap3 != null) {
                        String image3 = getStringImage(imageBitmap3);
                        Log.i("Mynewsam", "" + image3);
                        param.put("image", image3);
                    }
                }
*/
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
        alert.setMessage("يجب أن تقوم بالتقاط صورة واحدة على الاقل ");
        alert.show();
    }
}
