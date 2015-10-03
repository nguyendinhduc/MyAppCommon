package com.ducnd.demogridview;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.GridView;
import android.widget.Toast;

import com.ducnd.myappcommon.R;


public class Demogridview extends Activity{
    private GridView gridView;
    private AdapterGridView adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_gridview);
        Toast.makeText(this, "Demogridview", Toast.LENGTH_SHORT).show();
        initObject();

    }
    private void initObject() {
        gridView = (GridView)findViewById(R.id.gridView);
        adapter = new AdapterGridView(this);
        gridView.setAdapter(adapter);
    }


}
