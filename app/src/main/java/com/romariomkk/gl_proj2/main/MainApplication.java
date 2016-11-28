package com.romariomkk.gl_proj2.main;

import android.app.Application;

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

    public RequestManager getManager() {
        return manager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
