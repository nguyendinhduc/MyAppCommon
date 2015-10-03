package com.ducnd.incommingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by ducnd on 26/09/2015.
 */
public class InCommingCall extends BroadcastReceiver{
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        TelephonyManager tmgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        //Create Listner
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

        // Register listener for LISTEN_CALL_STATE
        tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {

            // Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);

            // state = 1 means when phone is ringing
            if (state == 1) {

                String msg = " New Phone Call Event. Incomming Number : "+incomingNumber;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(InCommingCall.this.context, msg, duration);
                toast.show();

            }
        }
    }
}
