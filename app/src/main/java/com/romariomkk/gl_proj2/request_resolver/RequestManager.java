package com.romariomkk.gl_proj2.request_resolver;

import android.os.AsyncTask;
import android.util.Log;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by romariomkk on 18.11.2016.
 */
public class RequestManager {

    public class AsyncTop500Extraction extends AsyncTask<Integer, Void, ArrayList<StationModel>> {
        private final String TAG = AsyncTop500Extraction.class.getSimpleName();

        @Override
        protected ArrayList<StationModel> doInBackground(Integer... ints) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Waiting..", e);
            }

            NodeList xmlList = xmlManager.getTopStations(ints[0], ints[1]);

            ArrayList<StationModel> stations = new ArrayList<>();
            for (int i = 0; i < xmlList.getLength(); i++) {
                Node obj = xmlList.item(i);
                StationModel model = createStation(i, obj);
                stations.add(model);
            }

            //cacheManager.writeStations(stations);

            return stations;
        }

        private StationModel createStation(int index, Node obj) {
            NamedNodeMap attrs = obj.getAttributes();
            return new StationModel(index,
                    attrs.getNamedItem("id").getNodeValue(),
                    attrs.getNamedItem("name").getNodeValue(),
                    attrs.getNamedItem("genre").getNodeValue(),
                    index == 0 ? R.drawable.img1 : R.drawable.img2,
                    attrs.getNamedItem("lc").getNodeValue());
        }

        /**
         * It has to be checked because in case of high load on the system,
         * GC can destroy the bridge FRAGMENT <--> ASYNC_TASK (this listener is
         * this bridge) because it's defined as a WeakReference. It can be destroyed,
         * for example, in case when we leave activity with the fragment while the
         * execution of Async_Task is not yet finished, or in other situation when
         * the activity(which is thus is a priority issue for destroying by GC) becomes invisible.
         */
        @Override
        protected void onPostExecute(ArrayList<StationModel> stationModels) {
            OnLoadListener l = listener.get();
            if (l != null) {
                l.onLoaded(stationModels);
            }
        }
    }

    public interface OnLoadListener {
        void onLoaded(ArrayList<StationModel> stations);
    }


    XMLManager xmlManager = new XMLManager();
    WeakReference<OnLoadListener> listener;
    AsyncTop500Extraction task = new AsyncTop500Extraction();

    public void requestTopStations(OnLoadListener listener) {
        this.listener = new WeakReference<>(listener);
        requestTopStations(500);
    }

    private void requestTopStations(int limit) {
        requestTopStations(0, limit);
    }

    private void requestTopStations(int offset, int limit) {
        ArrayList<StationModel> stations = null;// = cacheManager.getStations();
        if (stations == null) { //for cache managing utils
            if (task.getStatus() != AsyncTask.Status.RUNNING) {
                task = new AsyncTop500Extraction();
                task.execute(offset, limit);
            }
        } else {
            OnLoadListener l = listener.get();
            if (l != null) {
                l.onLoaded(stations);
            }
        }
    }
}