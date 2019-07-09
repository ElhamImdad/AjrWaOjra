package com.example.smoot.ajerwaojra.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.Helpers.Timer;
import com.example.smoot.ajerwaojra.R;


public class timerFragment extends Fragment {

Button start ;
TextView hour;
TextView seconds;
TextView minuts;
Timer timer;
    private Thread mThreadChrono;
    //Reference to the MainActivity (this class!)
    private Context mContext;
    public timerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        start = v.findViewById(R.id.start);
        hour = v.findViewById(R.id.hour);
        minuts = v.findViewById(R.id.minuts);
        seconds = v.findViewById(R.id.seconds);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer == null) {
                   // Log.e("start time = ",mTvTimer.getText().toString()+"---");
                    //instantiate the chronometer
                    timer = new Timer(mContext);
                    //run the chronometer on a separate thread
                    mThreadChrono = new Thread(timer);
                    mThreadChrono.start();

                    //start the chronometer!
                    timer.start();

                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

 /*   public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seconds.setText(timeAsText);
            }
        });
    }*/
}
