package com.romariomkk.gl_proj2.caching;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.main.MainApplication;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

import java.util.ArrayList;

/**
 * Created by romariomkk on 28.11.2016.
 */
public class CacheManager {
    private String TAG = getClass().getSimpleName();
    private String TIME_TAG = "current_time";
    //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance().getApplicationContext());


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void updateLastEditTimeForTable(String tableName) {
        SQLiteHelper dbHelper = MainApplication.getInstance().getDbHelper();
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + SQLiteHelper.CACHE_META_TABLE + " SET "
                    + SQLiteHelper.CACHE_TABLE_VALUE_TIME + "=" + System.currentTimeMillis() +
                    " WHERE " + SQLiteHelper.CACHE_TABLE_KEY + " = \"" + tableName + "\"");
        }
        Log.d(TAG, "Edit time updated");
        //pref.edit().putLong(TIME_TAG, System.currentTimeMillis()).commit();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public long getLastUpdateTimeOfTable(String tableName) {
        long valueToReturn = 0L;
        SQLiteHelper dbHelper = MainApplication.getInstance().getDbHelper();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery("SELECT " + SQLiteHelper.CACHE_TABLE_VALUE_TIME +
                    " FROM " + SQLiteHelper.CACHE_META_TABLE +
                    " WHERE " + SQLiteHelper.CACHE_TABLE_KEY + "=" + "\"" + tableName + "\"", null)) {
                if (cursor.moveToFirst()) {
                    valueToReturn = cursor.getLong(0);
                }
            }
        }
        Log.d(TAG, "Edit time returned");
        return valueToReturn;
        //return pref.getLong(TIME_TAG, System.currentTimeMillis());
    }

    public boolean isCacheOlderThan(int days, String tableName) {
        long dayDuration = days * 24 * 60 * 60 * 1000;
        return (System.currentTimeMillis() - getLastUpdateTimeOfTable(tableName) > dayDuration);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void writeStations(ArrayList<StationModel> stationList) {
        SQLiteHelper dbHelper = MainApplication.getInstance().getDbHelper();
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {

            for (StationModel station : stationList) {
                ContentValues vals = new ContentValues();
                vals.put(SQLiteHelper.STATION_ID, station.getStationId());
                vals.put(SQLiteHelper.STATION_NAME, station.getStationName());
                vals.put(SQLiteHelper.STATION_GENRE, station.getStationGenre());
                vals.put(SQLiteHelper.STATION_AUDIENCE, station.getCurrentAudience());

                try {
                    db.insertOrThrow(SQLiteHelper.TOP_500_STATIONS, null, vals);
                } catch (SQLException exc) {
                    Log.e(TAG, "Unable to insert single or multiple values.", exc);
                }
            }
        }
        Log.d(TAG, "Station TOP list updated");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public ArrayList<StationModel> getStations() {
        SQLiteHelper dbHelper = MainApplication.getInstance().getDbHelper();
        ArrayList<StationModel> stationList = null;
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            if (db != null) {
                String selectQuery = "SELECT * FROM " + SQLiteHelper.TOP_500_STATIONS;
                try (Cursor cursor = db.rawQuery(selectQuery, null)) {
                    if (cursor.moveToFirst()) {
                        stationList = new ArrayList<>();
                        do {
                            StationModel station = extractStation(cursor);
                            stationList.add(station);
                            Log.d(TAG, "Station " + cursor.getPosition() + " extracted");
                        } while (cursor.moveToNext());
                    }
                } catch (SQLException exc) {
                    Log.e(TAG, "Unable to extract single or multiple values.", exc);
                }
            }
        }
        Log.i(TAG, "Database extraction executed");
        return stationList;
    }

    private StationModel extractStation(Cursor cursor) {
        return new StationModel(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getPosition() == 0 ? R.drawable.img1 : R.drawable.img2,
                cursor.getString(4));
    }

}
