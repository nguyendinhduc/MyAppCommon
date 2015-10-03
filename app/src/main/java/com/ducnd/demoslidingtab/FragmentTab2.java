package com.ducnd.demoslidingtab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ducnd.myappcommon.R;

/**
 * Created by ducnd on 10/08/2015.
 */
public class FragmentTab2 extends Fragment {
    private static final String TAG = "FragmentTa2";
    private View view;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2, container, false);
        Log.i(TAG, "FragmentTab2");
        return view;
    }
}
