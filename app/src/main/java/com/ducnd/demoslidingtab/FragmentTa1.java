package com.ducnd.demoslidingtab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ducnd.myappcommon.R;


public class FragmentTa1 extends Fragment {
    private static final String TAG = "FragmentTa1";
    private View view;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1, container, false);
        Log.i(TAG,"Fragment one" );
        return view;
    }
}
