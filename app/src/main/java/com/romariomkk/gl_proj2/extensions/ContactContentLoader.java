package com.romariomkk.gl_proj2.extensions;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.CursorAdapter;

/**
 * Created by romariomkk on 01.12.2016.
 */
public class ContactContentLoader implements LoaderManager.LoaderCallbacks<Cursor>{
    private final String TAG = ContactContentLoader.class.getSimpleName();
    Context context;
    CursorAdapter adapter;

    public ContactContentLoader(Context c, CursorAdapter ad){
        context = c;
        adapter = ad;
        Log.d(TAG, "Loader initialized");
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projections = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};

        Log.d(TAG, "CursorLoader returned");
        return new CursorLoader(context, ContactsContract.Contacts.CONTENT_URI,
                projections, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "" + data.getCount());
        adapter.changeCursor(data);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

}