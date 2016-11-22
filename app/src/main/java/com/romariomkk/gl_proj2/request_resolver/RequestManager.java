package com.romariomkk.gl_proj2.request_resolver;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.top_stations.StationModel;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by romariomkk on 18.11.2016.
 */
public class RequestManager {

    XMLManager manager = new XMLManager();

    public ArrayList<StationModel> getTopStations() {
        return getTopStations(500);
    }

    public ArrayList<StationModel> getTopStations(int limit) {
        return getTopStations(0, limit);
    }

    public ArrayList<StationModel> getTopStations(int offset, int limit) {
        ArrayList<StationModel> stations = new ArrayList<>();

        NodeList xmlList = manager.getTopStations(offset, limit);
        for (int i = 0; i < xmlList.getLength(); i++) {
            Node obj = xmlList.item(i);
            StationModel model = createStation(i,obj);
            stations.add(model);
        }

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
}