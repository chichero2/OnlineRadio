package com.romariomkk.gl_proj2.country;


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
import com.romariomkk.gl_proj2.station_recycler_view.RecyclerAdapter;

import java.util.ArrayList;

//// TODO: 15.11.2016 LOOK AT FragmentArgs lib
public class CountriesFragment extends Fragment {

    public static CountriesFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("args_name", name);
        CountriesFragment frag = new CountriesFragment();
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
