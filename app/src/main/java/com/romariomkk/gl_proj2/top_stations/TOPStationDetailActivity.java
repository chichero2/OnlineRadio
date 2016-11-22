package com.romariomkk.gl_proj2.top_stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.romariomkk.gl_proj2.R;

import java.util.ArrayList;

public class TOPStationDetailActivity extends AppCompatActivity implements AsyncTop500Extraction.OnLoadedListener {

    CollapsingToolbarLayout collapseToolbar;

    TextView stationName;
    TextView stationDescription;
    ImageView stationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        initInstances();

        Intent intent = getIntent();
        StationModel station = (StationModel) intent.getSerializableExtra("station_item");

        stationName.setText(station.getStationName());
        stationDescription.setText(getString(R.string.currentAud, station.getStationGenre()));
        stationImage.setImageResource(station.getImageID());

        AsyncTop500Extraction task = new AsyncTop500Extraction();
        task.setOnLoadedListener(this);
        task.execute();
    }

    private void initInstances() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapseToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        stationName = (TextView) findViewById(R.id.stationName);
        stationDescription = (TextView) findViewById(R.id.stationDescr);
        stationImage = (ImageView) findViewById(R.id.main_image);
    }

    @Override
    public void onLoaded(ArrayList<StationModel> models) {

    }
}