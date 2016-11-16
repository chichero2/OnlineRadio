package com.romariomkk.gl_proj2.station_recycler_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by romariomkk on 17.11.2016.
 */
public class CustomItem extends RelativeLayout {
    public CustomItem(Context context) {
        super(context);
    }

    public CustomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInstances();
    }

    public CustomItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInstances();
    }

    private void initInstances() {

    }

}
