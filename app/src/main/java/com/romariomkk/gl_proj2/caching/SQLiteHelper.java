package com.romariomkk.gl_proj2.caching;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by romariomkk on 28.11.2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "station_db";

    public static final String TOP_500_STATIONS = "Top500Stations";
    public final static String STATION_ID = "stationId";
    public final static String STATION_NAME = "stationName";
    public final static String STATION_GENRE = "stationGenre";
    public static final String STATION_IMAGE = "stationImage";
    public static final String STATION_AUDIENCE = "currentAudience";

    public static final String CACHE_META_TABLE = "cache_meta";
    public static final String CACHE_TABLE_KEY = "cache_key_table";
    public static final String CACHE_TABLE_VALUE_TIME = "cache_value_time";

    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TOP_500_STATIONS + "(" +
            "ID integer," +
            STATION_ID + " text, " +
            STATION_NAME + " text, " +
            STATION_GENRE + " text," +
            STATION_AUDIENCE + " text," +
            "PRIMARY KEY (ID," + STATION_ID + "));";

    private final String CREATE_CACHE_META_TABLE = "CREATE TABLE IF NOT EXISTS " + CACHE_META_TABLE +
            "(" + CACHE_TABLE_KEY + " text, " +
            CACHE_TABLE_VALUE_TIME + " integer, " +
            "PRIMARY KEY (" + CACHE_TABLE_KEY  + ")" +
            ");";


    public SQLiteHelper(Context context) {
        this(context, DB_NAME, null, 1);
    }

    private SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
        db.execSQL(CREATE_CACHE_META_TABLE);
        initialInsert(db);
    }

    private void initialInsert(SQLiteDatabase db){
        ContentValues vals = new ContentValues();
        vals.put(CACHE_TABLE_KEY, TOP_500_STATIONS);
        vals.put(CACHE_TABLE_VALUE_TIME, 0);
        db.insertOrThrow(CACHE_META_TABLE, null, vals);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
