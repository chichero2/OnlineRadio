package com.romariomkk.gl_proj2.station;

import java.io.Serializable;

/**
 * Created by romariomkk on 16.11.2016.
 */
public class StationModel implements Serializable {

    private String stationName;
    private String stationDescription;
    private int imageID;

    public StationModel(String name, String desc, int imageId) {
        stationName = name;
        stationDescription = desc;
        imageID = imageId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationDescription() {
        return stationDescription;
    }

    public Integer getImageID() {
        return imageID;
    }

    @Override
    public String toString() {
        return "Station: " + stationName + " " + stationDescription + " " + imageID;
    }

}
