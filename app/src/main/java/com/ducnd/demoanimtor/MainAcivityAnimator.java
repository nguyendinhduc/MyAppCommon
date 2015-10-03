package com.ducnd.demoanimtor;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;

import com.ducnd.common.MyApplication;
import com.ducnd.myappcommon.R;

public class MainAcivityAnimator extends AppCompatActivity {
    private static final String TAG = "MainAcivityAnimator";
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        initComponent();
        showNotAnimationFagmentOne();
    }

    private void initComponent() {
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        imageView = (ImageView)findViewById(R.id.image);

    }


    public void showNotAnimationFagmentOne() {
        getFragmentManager().beginTransaction().add(R.id.content, fragmentOne, "fragment one").commit();
    }

    public void showFagmentOne() {
        ((ImageView)findViewById(R.id.image)).setImageBitmap(getBitmap());
        getFragmentManager().beginTransaction().remove(fragmentThree).commit();
//        getFragmentManager().beginTransaction().commitAllowingStateLoss();
        getFragmentManager().beginTransaction()
                .add(R.id.content, fragmentOne, "fragment one").commit();

    }

    public void showFragmentTwo() {
       imageView.setImageBitmap(getBitmap());
        getFragmentManager().beginTransaction().remove(fragmentOne).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.content, fragmentTwo, "fragment two").commit();
    }

    public void showFragmentThree() {
        ((ImageView)findViewById(R.id.image)).setImageBitmap(getBitmap());
        getFragmentManager().beginTransaction().remove(fragmentTwo).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.content, fragmentThree, "fragment three").commit();

    }

    public ImageView getImage() {
        return imageView;
    }

    private Bitmap getBitmap(){
        View v = getWindow().getDecorView().findViewById(R.id.contentMain);
        Bitmap b = Bitmap.createBitmap( MyApplication.WITDH_SCREEN, MyApplication.HEIGHT_SCREEN - getHeightStatusbar(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private int getHeightStatusbar() {
        Rect rectangle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight= rectangle.top;
        int contentViewTop=
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return statusBarHeight-contentViewTop ;
    }

    private int getSizeActionBar() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
