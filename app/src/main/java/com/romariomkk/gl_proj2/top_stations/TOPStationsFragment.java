package com.romariomkk.gl_proj2.top_stations;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.station_recycler_view.RecyclerAdapter;
import com.romariomkk.gl_proj2.top_stations.single.TOPStationDetailActivity;

import java.util.ArrayList;


public class TOPStationsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener,
        AsyncTop500Extraction.OnLoadedListener {

    NotificationManager notifManager;
    View inflaterView;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    AsyncTop500Extraction task;

    public static TOPStationsFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("title", name);
        TOPStationsFragment frag = new TOPStationsFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflaterView = inflater.inflate(R.layout.fragment_shows, container, false);
        initRecyclerView();
        return inflaterView;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) inflaterView.findViewById(R.id.recyclerView);
        Context context = inflaterView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        task = new AsyncTop500Extraction();
        task.setOnLoadedListener(this);
        task.execute();
    }

    @Override
    public void onLoaded(ArrayList<StationModel> models) {
        adapter = new RecyclerAdapter(getContext(), models);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(StationModel station) {
        Intent intent = new Intent(getContext(), TOPStationDetailActivity.class);
        intent.putExtra("station_item", station);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
    }








    private void callNotif() {
        if (notifManager == null) {
            notifManager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Notification.Builder notifDraft = new Notification.Builder(getContext());
        notifDraft.setContentTitle("FM player")
                .setContentText("Current Track: ")
                .setSmallIcon(R.drawable.ic_plus)
                .setTicker("FM radio is online");

        Notification notif = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notif = notifDraft.build();
        }
        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        notifManager.notify(1, notif);
    }
}
