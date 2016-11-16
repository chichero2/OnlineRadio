package com.romariomkk.gl_proj2.station_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.station.StationModel;

import java.util.ArrayList;

/**
 * Created by romariomkk on 09.11.2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClicked(StationModel station);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView smallImage;
        TextView idText;
        TextView mainText;
        TextView subText;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            smallImage = (ImageView) itemView.findViewById(R.id.small_image);
            idText = (TextView) itemView.findViewById(R.id.idNum);
            mainText = (TextView) itemView.findViewById(R.id.mainText);
            subText = (TextView) itemView.findViewById(R.id.subText);
        }
    }

    private final int HEADER_ITEM = 0;
    private final int PLAIN_ITEM = 1;

    Context context;
    ArrayList<StationModel> stations;
    private OnItemClickListener clickListener;

    public RecyclerAdapter(Context c, ArrayList<StationModel> items) {
        context = c;
        stations = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        clickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = null;
        switch (viewType) {
            case HEADER_ITEM:
                convertView = inflater.inflate(R.layout.head_single_item, parent, false);
                break;
            case PLAIN_ITEM:
                convertView = inflater.inflate(R.layout.plain_single_item, parent, false);
                break;
        }
        return new ViewHolder(convertView);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? HEADER_ITEM : PLAIN_ITEM;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StationModel station = stations.get(position);

        boolean isFirst = getItemViewType(position) == HEADER_ITEM;
        if (isFirst) {
            holder.image.setImageResource(station.getImageID());
        }
        holder.smallImage.setImageResource(station.getImageID());

        holder.idText.setText(context.getString(R.string.idText, position + 1));
        holder.mainText.setText(station.getStationName());
        holder.subText.setText(station.getStationDescription());

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null){
                clickListener.onItemClicked(station);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}
