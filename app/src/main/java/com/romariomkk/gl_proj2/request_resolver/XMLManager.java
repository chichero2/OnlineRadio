package com.romariomkk.gl_proj2.request_resolver;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by romariomkk on 18.11.2016.
 */
public class XMLManager {

    private final String BASE_API = "http://api.shoutcast.com";
    private final String TOP_500_SEARCH = "/legacy/Top500";
    private final String SEARCH_STATION = "/legacy/stationsearch";
    private final String START_PARAMS = "?";
    private final String AND = "&";
    private final String GENRE_TYPE = "genre=";
    private final String FORMAT_TYPE = "f=";
    private final String FORMAT_JSON_TYPE = "json";
    private final String FORMAT_XML_TYPE = "xml";
    private final String FORMAT_RSS_TYPE = "rss";
    private final String LIMIT_PARAM = "limit=";
    private final String COMMA = ",";

    private final String DEV_ID_PARAM = "k=";
    private final String DEV_ID = "ia9p4XYXmOPEtXzL";


    public NodeList getTopStations(int offset, int limit) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API)
                .append(TOP_500_SEARCH)
                .append(START_PARAMS)
                .append(DEV_ID_PARAM)
                .append(DEV_ID);

        if (limit > 0 && limit < 500)
            builder.append(AND)
                    .append(LIMIT_PARAM)
                    .append(offset)
                    .append(COMMA)
                    .append(limit);

        return readXML(builder.toString());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private NodeList readXML(String urlAddress) {
        NodeList xmlText = null;
        InputStream inStream = null;
        try {
            URL url = new URL(urlAddress);
            inStream = url.openStream();
        } catch (FileNotFoundException e) {
            Log.e("infoErr", "Internet access not gained", e);
        } catch (IOException e) {
            Log.e("infoErr", "Stream was not processed", e);
        }

        if (inStream != null) {
            try (BufferedInputStream reader = new BufferedInputStream(inStream)) {
                xmlText = readAll(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return xmlText;
    }

    private NodeList readAll(BufferedInputStream input) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null;
        NodeList elems = null;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(input);

            assert doc != null;
            if (doc.hasChildNodes()) {
                elems = doc.getElementsByTagName("station");
            }
        } catch (ParserConfigurationException | SAXException e) {
            Log.e("infoErr", "XML parsing error", e);
        }

        return elems;
    }
}