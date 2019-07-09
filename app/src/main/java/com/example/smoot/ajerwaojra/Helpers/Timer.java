package com.example.smoot.ajerwaojra.Helpers;

import android.content.Context;

import com.example.smoot.ajerwaojra.Activities.MainActivity;

public class Timer implements Runnable {
    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;
    Context mContext;
    boolean mIsRunning;
    long mStartTime;
    public Timer(Context context) {
        mContext = context;
    }
    public void start() {
        //  if(mStartTime == 0) { //if the start time was not set before! e.g. by second constructor
        mStartTime = System.currentTimeMillis();
        //}
        mIsRunning = true;
    }
    public long getStartTime() {
        return mStartTime;
    }
    @Override
    public void run() {
        while(mIsRunning) {
            //We do not call ConvertTimeToString here because it will add some overhead
            //therefore we do the calculation without any function calls!

            //Here we calculate the difference of starting time and current time
            long since = System.currentTimeMillis() - mStartTime;

            //convert the resulted time difference into hours, minutes, seconds and milliseconds
            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
            int hours = (int) ((since / (MILLS_TO_HOURS))); //this does not reset to 0!
            // int millis = (int) since % 1000; //the last 3 digits of millisecs

         //   ((MainActivity) mContext).updateTimerText(String.format("%02d:%02d:%02d:%03d"
           //         , hours, minutes, seconds));


            //Sleep the thread for a short amount, to prevent high CPU usage!
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
