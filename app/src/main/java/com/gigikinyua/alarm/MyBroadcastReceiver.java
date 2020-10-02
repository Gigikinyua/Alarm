package com.gigikinyua.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmControl.getInstance(context).playMusic();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Notification nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb);
    }

}
