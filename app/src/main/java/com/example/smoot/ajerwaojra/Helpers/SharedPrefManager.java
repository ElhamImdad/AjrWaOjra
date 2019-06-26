package com.example.smoot.ajerwaojra.Helpers;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.Models.Requester;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_USERNAME = "keyUsername";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_Phone = "keyPhone";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(Doer user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, user.getDoerEmail());
        editor.putString(KEY_USERNAME, user.getDoerName());
        editor.putString(KEY_Phone,user.getDoerPhone());

        editor.apply();
    }
    public void userLogin(Requester user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_Phone,user.getPhonNumber());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Doer getDoer() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Doer(
                sharedPreferences.getString(KEY_EMAIL, null) ,
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_Phone, null)

        );
    }
    public Requester getRequester() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Requester(
                sharedPreferences.getString(KEY_EMAIL, null) ,
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_Phone, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }
}
