package com.example.smoot.ajerwaojra.Helpers;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class SaveImage implements Target {
    private Context context;
    private WeakReference<AlertDialog> alartAlertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;
    private String desk;

    public SaveImage(Context context, AlertDialog alartDialog, ContentResolver contentResolver, String name, String desk) {
        this.context = context ;
        this.alartAlertDialogWeakReference = new WeakReference<AlertDialog>(alartDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desk = desk;
    }
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r = contentResolverWeakReference.get();
        AlertDialog Dialog = alartAlertDialogWeakReference.get();
        if ( r!=null)
            MediaStore.Images.Media.insertImage(r, bitmap, name, desk);
        Dialog.dismiss();

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivity(Intent.createChooser(intent, "VIEW PICTURE"));

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
