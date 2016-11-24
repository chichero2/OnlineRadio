package com.romariomkk.gl_proj2;

import android.app.Application;

/**
 * Created by romariomkk on 22.11.2016.
 */
public class MainApplication extends Application {

    private static MainApplication INSTANCE;

    public static MainApplication getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
