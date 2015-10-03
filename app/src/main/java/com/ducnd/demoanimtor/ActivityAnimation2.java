package com.ducnd.demoanimtor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ducnd.myappcommon.R;

/**
 * Created by ducnd on 27/08/2015.
 */
public class ActivityAnimation2 extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.table_horizontal_right_in, R.anim.table_horizontal_left_out);
        setContentView(R.layout.activity_demo_animtion2);
    }

    public void start1( View view ) {
        super.onBackPressed();
    }
}
