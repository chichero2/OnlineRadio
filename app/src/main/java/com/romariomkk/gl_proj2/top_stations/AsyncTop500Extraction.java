package com.romariomkk.gl_proj2.top_stations;

import android.os.AsyncTask;
import android.util.Log;

import com.romariomkk.gl_proj2.request_resolver.RequestManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by romariomkk on 22.11.2016.
 */
public class AsyncTop500Extraction extends AsyncTask<Integer, Void, ArrayList<StationModel>> {
    private static final String TAG = AsyncTop500Extraction.class.getSimpleName();

    RequestManager requestManager = new RequestManager();
    ///////////////
    WeakReference<OnLoadedListener> weakLoadListener;
    //////////////
    @Override
    protected ArrayList<StationModel> doInBackground(Integer... ints) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return requestManager.getTopStations();
    }

    @Override
    protected void onPostExecute(ArrayList<StationModel> stationModels) {
        OnLoadedListener listener = weakLoadListener.get();
        Log.d(TAG, "Listener " + listener);
        if (listener != null){//for checking the
            listener.onLoaded(stationModels);
        }
    }

    public interface OnLoadedListener{
        void onLoaded(ArrayList<StationModel> models);
    }

    void setOnLoadedListener(OnLoadedListener listener){
        weakLoadListener = new WeakReference<>(listener);
    }

}
