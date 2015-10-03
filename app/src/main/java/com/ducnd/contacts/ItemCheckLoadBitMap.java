package com.ducnd.contacts;

import android.graphics.Bitmap;

/**
 * Created by ducnd on 27/07/2015.
 */
public class ItemCheckLoadBitMap {
    private boolean isLoading = false;
    private boolean isFinish = false;
    private Bitmap bitmap = null;

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
