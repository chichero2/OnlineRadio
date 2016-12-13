package com.romariomkk.gl_proj2.sub_main.top_stations;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.main.MainApplication;
import com.romariomkk.gl_proj2.sub_main.single.TOPStationDetailActivity;
import com.romariomkk.gl_proj2.sub_main.top_stations.mvp.StationPresenter;
import com.romariomkk.gl_proj2.sub_main.top_stations.mvp.StationView;
import com.romariomkk.gl_proj2.sub_main.top_stations.station_recycler_view.RecyclerAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


public class TOPStationsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener,
        RecyclerAdapter.OnMenuItemClickedListener,
        StationView {

    AVLoadingIndicatorView loadingBar;
    View inflaterView;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    StationPresenter presenter;

    public static TOPStationsFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("title", name);
        TOPStationsFragment frag = new TOPStationsFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflaterView = inflater.inflate(R.layout.fragment_shows, container, false);

        loadingBar = (AVLoadingIndicatorView) inflaterView.findViewById(R.id.loadingBar);
        loadingBar.show();

        recyclerView = (RecyclerView) inflaterView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new StationPresenter(this, MainApplication.getInstance().getRequestManager());
        presenter.requestTopStations();

        return inflaterView;
    }

    @Override
    public void showTopStations(ArrayList<StationModel> stations) {
        adapter = new RecyclerAdapter(getContext(), stations);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemMenuClickListener(this);
        recyclerView.setAdapter(adapter);
        loadingBar.hide();
    }

    @Override
    public void notifyContactAdded(String notification) {
        Toast.makeText(getContext(), notification, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemClicked(StationModel station) {
        Intent intent = new Intent(getContext(), TOPStationDetailActivity.class);
        intent.putExtra(TOPStationDetailActivity.STATION_EXTRA, station);
        startActivity(intent);
    }

    @Override
    public void onMenuItemClicked(View view, StationModel stationModel) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.item_menu);
        popup.setOnMenuItemClickListener(menuItem -> {
            presenter.addToContactList(stationModel);
            return true;
        });
        popup.show();
    }

}