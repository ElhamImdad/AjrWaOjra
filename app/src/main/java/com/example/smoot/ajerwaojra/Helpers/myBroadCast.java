package com.example.smoot.ajerwaojra.Helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.smoot.ajerwaojra.R;

public class myBroadCast extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
     if (intent.getAction().equalsIgnoreCase("NewOffer")){
         // make notification
         Log.e("MMM","yes");

       makeNotification(context,bundle);

         Toast.makeText(context,bundle.getString("Message"),Toast.LENGTH_LONG).show();
         Toast.makeText(context,bundle.getString("Doer Name"),Toast.LENGTH_LONG).show();
     }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void makeNotification(Context context ,Bundle bundle){
        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Bitmap picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        NotificationChannel notificationChannel = new NotificationChannel("channalID","Name",NotificationManager.IMPORTANCE_HIGH);
        notif.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"channalID")
                .setContentTitle(bundle.getString("Doer Name"))
                .setContentTitle(" سيقوم بالعمرة "+bundle.getString("Doer Name"))
                .setNumber(11)
                .setAutoCancel(true)
                .setLargeIcon(picture);
        builder  .setSmallIcon(R.drawable.person_icon);
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
        builder.setVibrate(new long[]{500,1000,500,1000});
        notif.notify(11,builder.build());
    }
}
