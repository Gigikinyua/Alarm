package com.gigikinyua.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmControl {
    private static AlarmControl sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public AlarmControl(Context context) {
        mContext = context;
    }

    public static AlarmControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AlarmControl(context);
        }
        return sInstance;
    }

    public void playMusic() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mMediaPlayer = MediaPlayer.create(mContext, alarmSound);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void stopMusic() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
        }
    }
}
