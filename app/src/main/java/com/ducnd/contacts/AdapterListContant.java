package com.ducnd.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ducnd.myappcommon.R;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ducnd on 24/07/2015.
 */
public class AdapterListContant extends BaseAdapter {
    private static final String TAG = "AdapterListContant" ;
    private ArrayList<ItemContact> arrItemContact;
    private ArrayList<ItemCheckLoadBitMap> arrItemCheckLoadBitmap;
    private Context mContext;
    private LayoutInflater mInflater;


    public AdapterListContant(Context context, ArrayList<ItemContact> arrItemContact) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.arrItemContact = arrItemContact;
        this.arrItemCheckLoadBitmap = new ArrayList<ItemCheckLoadBitMap>();
        for ( int i = 0; i < arrItemContact.size(); i++ ) {
            Log.i(TAG, "URI: " + arrItemContact.get(i).getUriIcon());
            arrItemCheckLoadBitmap.add(new ItemCheckLoadBitMap());
        }
    }
    @Override
    public int getCount() {
        return arrItemContact.size();
    }

    @Override
    public ItemContact getItem(int position) {
        return arrItemContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holder = null;
        if ( convertView == null ) {
            convertView = mInflater.inflate(R.layout.item_listcontact, parent, false);
            holder = new HolderView();
            holder.textName = (TextView)convertView.findViewById(R.id.textName);
            holder.textPhone = (TextView)convertView.findViewById(R.id.textPhone);
            holder.icon = (CircleImageView)convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        }
        else holder = (HolderView)convertView.getTag();

        ItemContact tem = arrItemContact.get(position);
        holder.textName.setText(tem.getName());
        holder.textPhone.setText(tem.getPhone());
//        if ( tem.getUriIcon() != null ) {
//            try {
//                holder.icon.setImageBitmap(BitmapFactory.decodeStream(this.mContext.getContentResolver()
//                        .openInputStream(tem.getUriIcon())));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            if ( arrItemCheckLoadBitmap.get(position).isFinish() ) holder.icon.setImageBitmap(arrItemCheckLoadBitmap.get(position).getBitmap());
//            else {
//                if ( !arrItemCheckLoadBitmap.get(position).isLoading() ) {
//                    new MyAsyngTask(position, holder.icon, tem.getUriIcon()).execute("sdf");
//                    arrItemCheckLoadBitmap.get(position).setIsLoading(true);
//                }
//            }
//
//
//        }
         if ( tem.getUriIcon() != null) {
             holder.icon.setImageURI((tem.getUriIcon()));
         }
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
//                .writeDebugLogs()
//                .defaultDisplayImageOptions(defaultOptions)
//                .diskCacheExtraOptions(480, 320, null)
//                .build();
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
//        imageLoader.displayImage(tem.getUriIcon(), holder.icon, options, animateFirstListener);

        return convertView;
    }

    private class HolderView {
        private TextView textName, textPhone;
        private CircleImageView icon;
    }



    private class MyAsyngTask extends AsyncTask<String, String, String > {

        private CircleImageView icon;
        private int position;
        private int UPDATE_ICON = 979;
        private Uri uri;
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               if ( msg.what == UPDATE_ICON ) {
                   try {
                       Bitmap bm = BitmapFactory.decodeStream(AdapterListContant.this.mContext.getContentResolver()
                               .openInputStream(uri));

                       arrItemCheckLoadBitmap.get(position).setBitmap(bm);
                       arrItemCheckLoadBitmap.get(position).setIsFinish(true);
                       icon.setImageBitmap(bm);

                   } catch (FileNotFoundException e) {
                       e.printStackTrace();
                   }
               }

            }
        };

        public MyAsyngTask ( int position, CircleImageView icon, Uri uri ) {
            this.position = position;
            this.icon = icon;
            this.uri = uri;
        }


        @Override
        protected String doInBackground(String... params) {
            Message msg = new Message();
            msg.what = UPDATE_ICON;
            msg.setTarget(mHandler);
            msg.sendToTarget();
            return null;
        }
    }

}
