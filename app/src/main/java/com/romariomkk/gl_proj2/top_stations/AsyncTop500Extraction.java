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

    public interface OnLoadedListener{
        void onLoaded(ArrayList<StationModel> models);
    }
    ///////////////
    WeakReference<OnLoadedListener> weakLoadListener;
    //////////////
    RequestManager requestManager = new RequestManager();

    @Override
    protected ArrayList<StationModel> doInBackground(Integer... ints) {
        int length = ints.length;
        if (length == 1)
            return requestManager.getTopStations(ints[0]);
        if (length == 2)
            return requestManager.getTopStations(ints[0], ints[1]);

        return requestManager.getTopStations();
    }

    @Override
    protected void onPostExecute(ArrayList<StationModel> stationModels) {
        OnLoadedListener listener = weakLoadListener.get();
        Log.d(TAG, "Listener " + listener);
        /*
         * It has to be checked because in case of high load on the system,
         * GC can destroy the bridge FRAGMENT <--> ASYNC_TASK (this listener is
         * this bridge) because it's defined as a WeakReference. It can be destroyed,
         * for example, in case when we leave activity with the fragment while the
         * execution of Async_Task is not yet finished, or in other situation when
         * the activity(which is thus is a priority issue for destroying by GC) becomes invisible.
         */
        if (listener != null){
            listener.onLoaded(stationModels);
        }
    }

    void setOnLoadedListener(OnLoadedListener listener){
        weakLoadListener = new WeakReference<>(listener);
    }

}
