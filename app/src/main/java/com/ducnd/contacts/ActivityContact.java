package com.ducnd.contacts;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


import android.provider.ContactsContract.CommonDataKinds;

import com.ducnd.myappcommon.R;
/**
 * Created by ducnd on 24/07/2015.
 */
public class ActivityContact extends Activity {

    private ListView listContact;
    private ArrayList<ItemContact> arrItemContact;
    private AdapterListContant adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact);
        init();

    }

    public void init() {

        listContact = (ListView) findViewById(R.id.listContact);
        initArrItemContact();
        adapter = new AdapterListContant(this, arrItemContact);
        if (listContact != null)
            listContact.setAdapter(adapter);

    }

    private void initArrItemContact() {
        arrItemContact = new ArrayList<>();

        Cursor c = null;
        c = getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI, new String[]{
                CommonDataKinds.Phone.NUMBER,
                CommonDataKinds.Phone.DISPLAY_NAME,
                CommonDataKinds.Phone.PHOTO_ID
        }, null, null, null);

        if (c != null) {
            int indextNumber = c.getColumnIndex(CommonDataKinds.Phone.NUMBER);
            int indexName = c.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME);
            int indexUriIcon = c.getColumnIndex(CommonDataKinds.Phone.PHOTO_ID);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String uri = null;
                try {
                    uri = c.getString(indexUriIcon);
                    Uri u = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, Long.parseLong(uri));
                    arrItemContact.add(new ItemContact(c.getString(indexName), c.getString(indextNumber),u));
                } catch (Exception e) {
                    Uri u = Uri.parse("android.resource://com.ducnd.democommon/" + R.drawable.contact);
                    arrItemContact.add(new ItemContact(c.getString(indexName), c.getString(indextNumber),u));
                }

                c.moveToNext();
            }
            c.close();
        }


    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}
