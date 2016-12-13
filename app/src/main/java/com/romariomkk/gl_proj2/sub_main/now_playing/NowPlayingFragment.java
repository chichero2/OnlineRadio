package com.romariomkk.gl_proj2.sub_main.now_playing;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.romariomkk.gl_proj2.R;
import com.romariomkk.gl_proj2.extensions.ContactContentLoader;
import com.romariomkk.gl_proj2.sub_main.top_stations.station_recycler_view.RecyclerAdapter;

import java.util.ArrayList;

//// TODO: 15.11.2016 LOOK AT FragmentArgs lib
public class NowPlayingFragment extends Fragment {

    final String TAG = NowPlayingFragment.class.getSimpleName();

    RecyclerAdapter adapter;
    View inflaterView;
    RecyclerView recyclerView;

    SimpleCursorAdapter cursorAdapter;

    private LoaderManager.LoaderCallbacks<Cursor> loader;

    public static NowPlayingFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("title", name);
        NowPlayingFragment frag = new NowPlayingFragment();
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
        inflaterView = inflater.inflate(R.layout.fragment_countries, container, false);

        setupCursorAdapter();

        ListView listView = (ListView) inflaterView.findViewById(R.id.recyclerViewCountries);
        listView.setAdapter(cursorAdapter);

        loader = new ContactContentLoader(getContext(), cursorAdapter);
        getLoaderManager().initLoader(0, new Bundle(), loader);
        //initRecyclerView();

        Log.d(TAG, "List view initialized");
        return inflaterView;
    }

    private void setupCursorAdapter() {
        String[] bindFrom = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_URI};
        int[] bindTo = {R.id.contactName, R.id.contactImage};

        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.contact_item, null, bindFrom, bindTo, 0);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) inflaterView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(getContext(), new ArrayList<>());
        adapter.setOnItemClickListener(station -> Log.d("OK", "" + station));
        recyclerView.setAdapter(adapter);
    }

}
