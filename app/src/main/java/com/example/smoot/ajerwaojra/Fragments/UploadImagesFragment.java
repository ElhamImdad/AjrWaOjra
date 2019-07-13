package com.example.smoot.ajerwaojra.Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    String date;
    String totalTime;

    int id;
    Uri filePath1;
    Uri filePath2;
    Uri filePath3;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    File photoFile1 = null;
    File photoFile2 = null;
    File photoFile3 = null;
    String mCurrentPhotoPath;

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


   /* private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

// Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            if (b1.isEnabled()) {
                filePath1 = data.getData();
                try {
                    imageBitmap1 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPTURE);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public File getStringImage(Bitmap bitmap) {
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + filename + ".PNG";
        // name of file is done
        // create image file
        File f = new File(getContext().getCacheDir(), imageFileName);

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // test
        boolean t = f.exists();
        if (t) {
            Log.e("file is created ", "yes ");
        } else Log.e("file is created ", "no ");
        Log.i("BitMap", "" + bitmap);
        //

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, baos);
        byte[] b = baos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos.write(b);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;

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

                File f1 = getStringImage(imageBitmap1);


                param.put("image", f1.toString());

                if (imageBitmap2 != null) {
                    //
                    Log.i("Mynewsam", "" + image2);
                    //param.put("image", image2);
                    if (imageBitmap3 != null) {
                        //
                        Log.i("Mynewsam", "" + image3);
                        //param.put("image", image3);
                    }
                }

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

   /* private String getPath(Uri uri){
        Cursor cursor = getContext().getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String ducoment_id = cursor.getString(0);
        ducoment_id=ducoment_id.substring(ducoment_id.lastIndexOf(":"+1));
        cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media._ID+" = ?",
                new String[] {ducoment_id},null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        return path;
    }*/
/*
    private void upload()
    {
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + filename + ".PNG";
        String path = getPath(filePath1);
        try{
            String uploadID= UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(),uploadID,URLs.UPL_FINISH_REQUEST)
        }catch (Exception e){e.printStackTrace();}

    }

}
*/
}