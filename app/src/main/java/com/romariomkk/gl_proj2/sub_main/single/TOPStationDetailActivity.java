package com.romariomkk.gl_proj2.sub_main.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.sub_main.single.management.Constants;
import com.romariomkk.gl_proj2.sub_main.single.management.PlayerManager;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

public class TOPStationDetailActivity extends AppCompatActivity {

    public final static String STATION_EXTRA = "station_item";

    CollapsingToolbarLayout collapseToolbar;

    TextView stationAud;
    TextView stationGenre;
    ImageView stationImage;

    ImageView startButton;
    ImageView stopButton;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.STOP_FOREGROUND_ACTION)){
                stopService(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Intent intent = getIntent();
        StationModel station = (StationModel) intent.getSerializableExtra(STATION_EXTRA);

        initComponentsWithModel(station);

        collapseToolbar.setTitle(station.getStationName());
        stationGenre.setText(station.getStationGenre());
        stationAud.setText(getString(R.string.currentAud, station.getCurrentAudience()));
        stationImage.setImageResource(station.getImageID());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(Constants.PACKAGE_NAME));
    }

    private void initComponentsWithModel(StationModel station) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapseToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        stationAud = (TextView) findViewById(R.id.stationAud);
        stationGenre = (TextView) findViewById(R.id.stationGenre);
        stationImage = (ImageView) findViewById(R.id.main_image);

        startButton = (ImageView) findViewById(R.id.startPlayback);
        stopButton = (ImageView) findViewById(R.id.stopPlayback);

        startButton.setOnClickListener((view) -> {
            Intent intent = new Intent(this, PlayerManager.class);
            intent.putExtra(PlayerManager.EXTRA, station);
            intent.setAction(Constants.START_FOREGROUND_ACTION);
            startService(intent);
        });

        stopButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PlayerManager.class);
            intent.setAction(Constants.STOP_FOREGROUND_ACTION);
            startService(intent);
        });

//        bindService(new Intent(this, PlayerManager.class), new ServiceConnection() {
//            /*
//            * */
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//                PlayerManager.PlayerBinder binder = (PlayerManager.PlayerBinder)iBinder;
//                PlayerManager manager = binder.getPlayerManager();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//                //manage = null;
//            }
//        }, Context.BIND_AUTO_CREATE);
//
//        unbindService();
//
    }
}