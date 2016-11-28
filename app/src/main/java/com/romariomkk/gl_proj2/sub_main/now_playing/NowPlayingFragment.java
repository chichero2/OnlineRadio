package com.romariomkk.gl_proj2.sub_main.now_playing;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.sub_main.top_stations.station_recycler_view.RecyclerAdapter;

import java.util.ArrayList;

//// TODO: 15.11.2016 LOOK AT FragmentArgs lib
public class NowPlayingFragment extends Fragment {

    public static NowPlayingFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("title", name);
        NowPlayingFragment frag = new NowPlayingFragment();
        frag.setArguments(args);
        return frag;
    }

    View inflaterView;
    RecyclerView recyclerView;

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
        RecyclerAdapter adapter = new RecyclerAdapter(context, new ArrayList<>());
        adapter.setOnItemClickListener(station -> Log.d("OK", "" + station));
        recyclerView.setAdapter(adapter);
    }

}
