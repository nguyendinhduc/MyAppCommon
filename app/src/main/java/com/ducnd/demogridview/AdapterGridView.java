package com.ducnd.demogridview;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ducnd.common.MyApplication;
import com.ducnd.myappcommon.R;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class AdapterGridView extends BaseAdapter {
    private String TAG = "AdapterGridView";
    private ArrayList<Uri> uriImages;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ItemImage> itemImages;


    public AdapterGridView(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        initUriImages();
    }

    private void initUriImages() {
        uriImages = new ArrayList<Uri>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA}, null, null,
                MediaStore.Images.Media.DATE_ADDED + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            uriImages.add(Uri.fromFile(new File(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)))));
            cursor.moveToNext();
        }
        cursor.close();
        itemImages = new ArrayList<ItemImage>();
        for (int i = 0; i < uriImages.size(); i++) {
            itemImages.add(new ItemImage());
        }
        return;

    }

    @Override
    public int getCount() {
        return uriImages.size();
    }

    @Override
    public Uri getItem(int position) {
        return uriImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderImage holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_demo_gridview, parent, false);
            holder = new ViewHolderImage();
            holder.imageView = (SquareImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderImage) convertView.getTag();
        }

        holder.imageView.setImageURI(uriImages.get(position));
        return convertView;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap decodeSampledBitmapFromMemory(String data, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(data, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(data, options);
    }



    private class ViewHolderImage {
        private SquareImageView imageView;

    }
    private class ItemImage {
        private boolean isLoading = false;
        private boolean isFinish = false;
        private Bitmap bm;
    }

    public void loadBitmap(int position, String data, SquareImageView imageView) {
        if (cancelPotentialWork(data, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(position, imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(this.context.getResources(), itemImages.get(position).bm != null ? itemImages.get(position).bm :
                            BitmapFactory.decodeResource(context.getResources(), R.drawable.download), task);
            imageView.setImageDrawable(asyncDrawable);
            if ( itemImages.get(position).bm == null ) {
                task.execute(data);
            }
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            if (!bitmapData.equals(data)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data = "";
        private int position = -1;

        public BitmapWorkerTask(int posistion, ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            this.position = posistion;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            if ( itemImages.get(this.position).bm != null ) return null;
            else {
                float sizeImage = (MyApplication.WITDH_SCREEN - 16 * ( MyApplication.DENSITY / 160 ) ) /3;
                int orientation = MyApplication.getOrientaionBitmap(data);
                return MyApplication.getBitmapOrientation(orientation,
                        decodeSampledBitmapFromMemory(data, (int) sizeImage, (int) sizeImage));
            }
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                    itemImages.get(this.position).bm = bitmap;
                    itemImages.get(this.position).isFinish = true;
                }
            }
        }
    }
}
