package com.romariomkk.gl_proj2.sub_main.top_stations.mvp;

import com.romariomkk.gl_proj2.request_resolver.RequestManager;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

import java.util.ArrayList;

/**
 * Created by romariomkk on 24.11.2016.
 */
public class StationPresenter {

    StationView stationView;
    RequestManager requestManager;

    public StationPresenter(StationView view, RequestManager manager){
        stationView = view;
        requestManager = manager;
    }

    public void requestTopStations(){
        requestManager.requestTopStations(new RequestManager.OnLoadListener() {
            @Override
            public void onLoaded(ArrayList<StationModel> stations) {
                stationView.showTopStations(stations);
            }
        });

    }

}
