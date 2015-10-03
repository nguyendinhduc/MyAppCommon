package com.ducnd.common;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by ducnd on 07/08/2015.
 */
public class MyApplication extends Application {
    public static int WITDH_SCREEN;
    public static int HEIGHT_SCREEN;
    public static int DENSITY;

    @Override
    public void onCreate() {
        super.onCreate();
        initSizeScreen();
    }

    private void initSizeScreen() {
        WindowManager window = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics display = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(display);
        WITDH_SCREEN = display.widthPixels;
        HEIGHT_SCREEN = display.heightPixels;
        DENSITY = display.densityDpi;
    }
    public static int getOrientaionBitmap( String sourcepath ) {
        int rotate = 0;
        try {
            File imageFile = new File(sourcepath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap getBitmapOrientation( int orientation , Bitmap bitmap ) {
        if ( orientation == 0 ) return bitmap;
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        bitmap =  Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

}
