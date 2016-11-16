package com.romariomkk.gl_proj2.station;


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

import java.util.ArrayList;


public class StationsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener {

    NotificationManager notifManager;
    View inflaterView;
    RecyclerView recyclerView;

    public static StationsFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("args_name", name);
        StationsFragment frag = new StationsFragment();
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

        // FIXME: 16.11.2016 receive ArrayList<StationModel> from e.g. network
        ArrayList<StationModel> objs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            objs.add(new StationModel("StationName", "StationDescr", i == 0 ? R.drawable.img1 : R.drawable.img2));
        }

        RecyclerAdapter adapter = new RecyclerAdapter(context, objs);

        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(StationModel station) {
        Intent intent = new Intent(getContext(), StationDetailActivity.class);
        intent.putExtra("station_item", station);
        startActivity(intent);
    }

    private void callNotif() {
        if (notifManager == null) {
            notifManager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Notification.Builder notifDraft = new Notification.Builder(getContext());
        notifDraft.setContentTitle("LastFM player")
                .setContentText("Current Track: ")
                .setSmallIcon(R.drawable.ic_plus)
                .setTicker("LastFM radio is online");

        Notification notif = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notif = notifDraft.build();
        }
        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        notifManager.notify(1, notif);
    }
}
