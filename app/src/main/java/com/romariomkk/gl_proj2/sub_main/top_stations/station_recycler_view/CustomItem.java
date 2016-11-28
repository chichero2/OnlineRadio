package com.romariomkk.gl_proj2.sub_main.top_stations.station_recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

/**
 * Created by romariomkk on 17.11.2016.
 */
public class CustomItem extends RelativeLayout {


    private TextView idText;
    private TextView mainText;
    private TextView subText;
    private ImageView smallImage;

    public CustomItem(Context context) {
        this(context, null);
    }

    public CustomItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        initInstances(attrs);
    }

    void setStation(StationModel st, int pos){
        StationModel station = st;
        int position = pos;

        idText.setText(Integer.toString(position + 1));
        smallImage.setImageResource(station.getImageID());
        mainText.setText(station.getStationName());
        subText.setText(station.getStationGenre());
    }

    void initInflater(){
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_station_item_view, this, true);
    }


    private void initInstances(AttributeSet attrs) {
        initInflater();
        initFields();

        if (attrs != null) {
            Context context = getContext();
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CustomItem);

            int mainTextColor = arr.getColor(R.styleable.CustomItem_mainTextColor, getResources().getColor(R.color.black));
            int subTextColor = arr.getColor(R.styleable.CustomItem_subTextColor, getResources().getColor(R.color.light_black));
            arr.recycle();

            idText.setTextColor(mainTextColor);
            mainText.setTextColor(mainTextColor);
            subText.setTextColor(subTextColor);
        }
    }

    private void initFields() {
        smallImage = (ImageView) findViewById(R.id.small_image);
        idText = (TextView) findViewById(R.id.idNum);
        mainText = (TextView) findViewById(R.id.mainText);
        subText = (TextView) findViewById(R.id.subText);
    }
}