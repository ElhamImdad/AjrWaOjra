package com.example.smoot.ajerwaojra.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.smoot.ajerwaojra.Fragments.InprogressRequestsFragment;
import com.example.smoot.ajerwaojra.Fragments.PendingRequestFragment;
import com.example.smoot.ajerwaojra.Fragments.RequestDetailsFragment;
import com.example.smoot.ajerwaojra.R;

public class OmrahDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omrah_details);

        Intent intent = getIntent();
        Bundle bundle;
        bundle = intent.getExtras();
        String omraStatus = bundle.getString("omraStatus");

        if (bundle != null ){
            switch (omraStatus){
                case "3":
                    Bundle bundle2 = new Bundle();
                    Log.e("omra name,,,",bundle.getString("omraName") );
                    bundle2.putString("omraName",bundle.getString("omraName"));
                    bundle2.putString("doerName", bundle.getString("doerName"));
                    bundle2.putString("date", bundle.getString("date"));
                    bundle2.putString("time", bundle.getString("time"));
                    bundle2.putString("doaa", bundle.getString("doaa"));
                    bundle2.putStringArrayList("photos", bundle.getStringArrayList("photos"));

                    RequestDetailsFragment doneObj = new RequestDetailsFragment();
                    doneObj.setArguments(bundle2);
                    setFragment(doneObj);
                    break;
                case "2":
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("omraName",bundle.getString("omraName"));
                    bundle3.putString("doerName", bundle.getString("doerName"));
                    bundle3.putString("date", bundle.getString("date"));
                    bundle3.putString("time", bundle.getString("time"));
                    bundle3.putString("doaa", bundle.getString("doaa"));

                    InprogressRequestsFragment inProgressObj = new InprogressRequestsFragment();
                    inProgressObj.setArguments(bundle3);
                    setFragment(inProgressObj);
                    break;
                case "1":
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("omraName",bundle.getString("omraName"));
                    bundle4.putString("doaa", bundle.getString("doaa"));

                    PendingRequestFragment pendingObj = new PendingRequestFragment();
                    pendingObj.setArguments(bundle4);
                    setFragment(pendingObj);
                    break;
            }
        }
    }
    public void setFragment(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.details,f);
        ft.commit();
    }

}
