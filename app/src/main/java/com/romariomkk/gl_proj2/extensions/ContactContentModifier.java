package com.romariomkk.gl_proj2.extensions;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import com.romariomkk.gl_proj2.main.MainApplication;
import com.romariomkk.gl_proj2.sub_main.top_stations.StationModel;

import java.util.ArrayList;

/**
 * Created by romariomkk on 30.11.2016.
 */
public class ContactContentModifier {

    public interface OnContactAdding {
        void onContactAdded();
    }

    private final String TAG = ContactContentModifier.class.getSimpleName();

    private OnContactAdding contactAddingListener;
    ArrayList<ContentProviderOperation> opsList = new ArrayList<>();
    ContentProviderOperation op;

    public void addToContactList(StationModel st, OnContactAdding listener) {
        contactAddingListener = listener;

        op = ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        opsList.add(op);

        opsList.add(formContactName(st));
        opsList.add(formPhoneNumber(st));
        opsList.add(formEmail(st));

        try {
            MainApplication.getInstance().getContentResolver().applyBatch(ContactsContract.AUTHORITY, opsList);
        } catch (RemoteException e) {
            Log.e(TAG, "Remote Content Provider didn't respond", e);
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Application operation didn't respond correctly", e);
        }

        if (contactAddingListener != null)
            contactAddingListener.onContactAdded();
    }

    ContentProviderOperation formContactName(StationModel st) {
        return ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, Integer.parseInt(st.getStationId()))
                .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        st.getStationName())
                .build();
    }

    ContentProviderOperation formPhoneNumber(StationModel st) {
        return ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, Integer.parseInt(st.getStationId()))
                .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, st.getCurrentAudience())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build();
    }

    ContentProviderOperation formEmail(StationModel st) {
        return ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, Integer.parseInt(st.getStationId()))
                .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, st.getStationGenre())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                .build();
    }

}
