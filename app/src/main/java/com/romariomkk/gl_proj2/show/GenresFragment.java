package com.romariomkk.gl_proj2.show;


import android.content.Context;
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

public class GenresFragment extends Fragment {

    View inflaterView;
    RecyclerView recyclerView;

    public static GenresFragment newInstance(String name){
        Bundle bundle = new Bundle();
        bundle.putString("title", name);
        GenresFragment frag = new GenresFragment();
        frag.setArguments(bundle);
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
        recyclerView.setAdapter(new RecyclerAdapter(context, new ArrayList<>()));
    }
}
