package com.example.smoot.ajerwaojra.Helpers;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.example.smoot.ajerwaojra.Activities.MainActivity;
import com.example.smoot.ajerwaojra.Fragments.FatwaFragment;
import com.example.smoot.ajerwaojra.Models.Doer;
import com.example.smoot.ajerwaojra.Models.Requester;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    public static final String TOKEN = "keytoken";
    private static final String KEY_Phone = "keyPhone";
    private static final String KEY_ROLE = "keyRole";
    private static final String CountryID = "countryId";
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
    public void userLogin(Requester user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, user.getToken());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getPassword());
        editor.putString(KEY_Phone,user.getPhonNumber());
        editor.putString(KEY_ROLE,user.getRole());
        editor.putString(CountryID,user.getCountry());
        editor.apply();
    }
    public void userLogin(Doer user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, user.getDoerToken());
        editor.putString(KEY_EMAIL, user.getDoerEmail());
        editor.putString(KEY_USERNAME, user.getDoerName());
        editor.putString(KEY_Phone,user.getDoerPhone());
        editor.putString(KEY_ROLE,user.getRole());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, null) != null;
    }

    //this method will give the logged in user
    public Doer getDoer() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Doer(
                sharedPreferences.getString(KEY_EMAIL, null) ,
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_Phone, null),
                sharedPreferences.getString(TOKEN, null),
                sharedPreferences.getString(KEY_ROLE, "Doer")
        );
    }
    public Requester getRequester() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Requester(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(TOKEN, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ROLE,"Requester"),
                sharedPreferences.getString(CountryID,"")

                );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
       // ctx.startActivity(new Intent(ctx, MainActivity.class));
    }
}
