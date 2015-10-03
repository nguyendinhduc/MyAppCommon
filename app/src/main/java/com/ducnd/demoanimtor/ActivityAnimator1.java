package com.ducnd.demoanimtor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ducnd.myappcommon.R;

/**
 * Created by ducnd on 27/08/2015.
 */
public class ActivityAnimator1 extends Activity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_animtion1);
    }
    public void start2 ( View view ) {
        Intent intent = new Intent();
        intent.setClass(this, ActivityAnimation2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.table_horizontal_right_in, R.anim.table_horizontal_left_out);

        Toast.makeText(this, "start2", Toast.LENGTH_SHORT).show();

    }
}
