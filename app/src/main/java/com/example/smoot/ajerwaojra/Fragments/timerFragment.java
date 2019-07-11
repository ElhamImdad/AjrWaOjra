package com.example.smoot.ajerwaojra.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smoot.ajerwaojra.R;

import java.text.DateFormat;
import java.util.Date;

public class timerFragment extends Fragment {
    /** Called when the activity is first created. */
    private TextView textTimer;
    private Button startButton;
    private Button pauseButton;
    private Button stopUmraaButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    TextView secondss;
    TextView minuts;
    TextView hours;

    TextView umraOwner ;
    TextView doaa;

    int id;

    String stringDate;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_timer, container, false);

        Date ddd = new Date();
        stringDate = DateFormat.getDateInstance().format(ddd);
        Log.e("Current Date is ",stringDate);

        umraOwner = v.findViewById(R.id.umraOwner);
        doaa = v.findViewById(R.id.doaa);

        Bundle bundle0 = getArguments();


        umraOwner.setText(bundle0.getString("umraUner"));
        doaa.setText(bundle0.getString("doaa"));
        id = bundle0.getInt("id");



        stopUmraaButton = v.findViewById(R.id.stopUmra);
        stopUmraaButton.setVisibility(View.INVISIBLE);
        secondss = v.findViewById(R.id.textView19);
        minuts = v.findViewById(R.id.textView18);
        hours = v.findViewById(R.id.textView17);


        startButton = v.findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);
                startButton.setVisibility(View.INVISIBLE);
                stopUmraaButton.setVisibility(View.VISIBLE);
            }
        });


        stopUmraaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

                String total = hours.getText()+":"+minuts.getText()+":";
                Log.e("total ",total);

                Bundle bundle = new Bundle();
                bundle.putString("totalTime",total);
                bundle.putString("date",stringDate);
                bundle.putInt("id",id);

                UploadImagesFragment f = new UploadImagesFragment();
                f.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.disallowAddToBackStack();
                ft.replace(R.id.container,f);
                ft.commit();
            }
        });



return  v;
    }

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            int hour =  (milliseconds / 1000) / 36000;
         /*   textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliseconds));*/
            minuts.setText(String.format("%02d", minutes));
            secondss.setText(String.format("%02d", seconds));
            hours.setText(String.format("%02d", hour));



            myHandler.postDelayed(this, 0);
        }

    };

}



