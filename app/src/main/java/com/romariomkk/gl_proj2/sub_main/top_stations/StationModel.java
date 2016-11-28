package com.romariomkk.gl_proj2.sub_main.top_stations;

import java.io.Serializable;

/**
 * Created by romariomkk on 16.11.2016.
 */
public class StationModel implements Serializable {

    private int internalId;
    private String stationId;
    private String stationName;
    private String stationGenre;
    private int imageID;
    private String currentAudience;

    public StationModel(int internId, String id, String name, String genre, int imageId) {
        this(internId, id, name, genre, imageId, "0");
    }

    public StationModel(int internId, String id, String name, String genre, int imageId, String curUsers) {
        internalId = internId;
        stationId = id;
        stationName = name;
        stationGenre = genre;
        imageID = imageId;
        currentAudience = curUsers;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationGenre() {
        return stationGenre;
    }

    public Integer getImageID() {
        return imageID;
    }

    public int getInternalId() {
        return internalId;
    }

    public String getStationId() {
        return stationId;
    }

    public String getCurrentAudience() {
        return currentAudience;
    }

    @Override
    public String toString() {
        return "Station: " + stationName + " " + stationGenre + " " + imageID;
    }

}
