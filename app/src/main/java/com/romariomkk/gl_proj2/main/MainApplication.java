package com.romariomkk.gl_proj2.main;

import android.app.Application;

import com.romariomkk.gl_proj2.caching.SQLiteHelper;
import com.romariomkk.gl_proj2.request_resolver.RequestManager;

/**
 * Created by romariomkk on 22.11.2016.
 */
public class MainApplication extends Application {

    private static MainApplication INSTANCE;

    public static MainApplication getInstance(){
        return INSTANCE;
    }

    RequestManager manager = new RequestManager();
    SQLiteHelper dbHelper;

    public RequestManager getRequestManager() {
        return manager;
    }

    public SQLiteHelper getDbHelper() {
        if (dbHelper == null)
            dbHelper = new SQLiteHelper(this);
        return dbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
