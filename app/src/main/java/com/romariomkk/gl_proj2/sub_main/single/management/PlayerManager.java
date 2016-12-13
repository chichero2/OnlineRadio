package com.romariomkk.gl_proj2.sub_main.single.management;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

/**
 * Created by romariomkk on 02.12.2016.
 */
public class PlayerManager extends Service implements MediaPlayer.OnPreparedListener {

    private int notiID = 101;
    public static final String EXTRA = "final_extra";
    private final String TAG = PlayerManager.class.getSimpleName();

    private Uri path;
    private MediaPlayer player = null;
    private boolean isPlaying = false;

    public PlayerManager() {
        super();
    }

    public class PlayerBinder extends Binder {
        public PlayerManager getPlayerManager() {
            return PlayerManager.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isPlaying) {
            if (intent.getAction().equals(Constants.START_FOREGROUND_ACTION)) {
                initAndLaunchPlayer(intent);
            }
        } else {
            if (intent.getAction().equals(Constants.PAUSE_FOREGROUND_ACTION)) {
                pausePlayer();
            } else if (intent.getAction().equals(Constants.STOP_FOREGROUND_ACTION)) {
                stopPlayer(intent);
            }
        }
        return START_NOT_STICKY;
    }

    private void initAndLaunchPlayer(Intent intent){
        StationModel station = (StationModel) intent.getSerializableExtra(EXTRA);
        path = Uri.parse("http://cast.radiogroup.com.ua:8000/106fm");

        //heavy operation - // TODO: 06.12.2016 to be extracted into separated thread
        player = MediaPlayer.create(getApplicationContext(), path);
        //
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        startForeground(notiID, getNotification(station));
        isPlaying = true;
    }

    private void pausePlayer() {
        isPlaying = false;
        player.pause();
        Log.d(TAG, "Player paused");
    }

    private void stopPlayer(Intent intent){
        player.stop();
        stopForeground(true);
        Log.d(TAG, "Foreground stopped");

        isPlaying = false;

        sendBroadcast(intent);
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "Prepared and starting playback");
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        player.stop();
        player = null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(StationModel station) {
        Intent pauseIntent = new Intent(this, PlayerManager.class);
        pauseIntent.setAction(Constants.PAUSE_FOREGROUND_ACTION);
        PendingIntent pPauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);

        Intent stopIntent = new Intent(this, PlayerManager.class);
        stopIntent.setAction(Constants.STOP_FOREGROUND_ACTION);
        PendingIntent pStopIntent = PendingIntent.getService(this, 0, stopIntent, 0);

        Notification noti = new Notification.Builder(this)
                .setContentTitle(station.getStationName())
                .setTicker("Started " + station.getStationName())
                .setContentText(station.getCurrentAudience())
                .addAction(R.drawable.play_button, "", pPauseIntent)
                .addAction(R.drawable.stop_button, "", pStopIntent)
                .setSmallIcon(R.drawable.last_fm)
                .build();

        noti.flags |=
                Notification.FLAG_FOREGROUND_SERVICE |
                Notification.FLAG_NO_CLEAR;
        return noti;
    }

}
