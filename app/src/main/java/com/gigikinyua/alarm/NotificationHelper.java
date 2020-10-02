package com.gigikinyua.alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import java.util.jar.Manifest;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    public static final String ACTION_DISMISS = "ACTION_DISMISS";
    private NotificationManager mManager;
    public MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    Intent fullScreenIntent = new Intent(this, MainActivity.class);

    PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
            fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);



    public NotificationHelper(Context base) {
        super(base);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
       /* Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), alarmSound);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);*/

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;

    }

    public Notification getChannelNotification() {

        Intent snoozeIntent = new Intent(this, DismissBroadcast.class);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification Notif = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Alarm!")
                .setContentText("Your AlarmManager is working.")
                .setSmallIcon(R.drawable.ic_stand_up).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(true)

                // Use a full-screen intent only for the highest-priority alerts where you
                // have an associated activity that you would like to launch after the user
                // interacts with the notification. Also, if your app targets Android 10
                // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
                // order for the platform to invoke this notification.
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .addAction(R.drawable.ic_snooze, "DISMISS", snoozePendingIntent)
                .build();
//        mediaPlayer.start();

        /*if (ACTION_DISMISS.equals(snoozeIntent.getAction())) {
            mediaPlayer.stop();
        }*/


        return Notif;
    }


}
