package com.ducnd.common;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceSpeechToText extends Service {
    private static final String TAG = "ServiceSpeechToText";
    private PackageManager packageManager;
    private BroadcastSpeechToText broadcastSpeechToText = null;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIsListening = false;
    private boolean checkListening = true;

    @Override
    public void onCreate() {
        super.onCreate();
        packageManager = getPackageManager();
//        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
//                this.getPackageName());
//
//        SpeechRecognitionListener listener = new SpeechRecognitionListener();
//        mSpeechRecognizer.setRecognitionListener(listener);
        initComponent();
    }

    private void initComponent() {
        if ( mSpeechRecognizer != null ) {
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
        }
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerBroadcastSpeechToText();
        return START_NOT_STICKY;
    }

    private void registerBroadcastSpeechToText() {
        if (broadcastSpeechToText == null) {
            broadcastSpeechToText = new BroadcastSpeechToText();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonVL.REQUEST_RECORD);
            registerReceiver(broadcastSpeechToText, intentFilter);
        }
    }

    private void unregisterBroadcastSpeech() {
        if (broadcastSpeechToText != null) {
            unregisterReceiver(broadcastSpeechToText);
            broadcastSpeechToText = null;
        }
    }

    private class BroadcastSpeechToText extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonVL.REQUEST_RECORD:
//                    if ( checkListening ) {
                        checkListening = false;
//                        if ( mSpeechRecognizer != null ) mSpeechRecognizer.destroy();
                        if (!mIsListening) {
                            mIsListening = true;
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        }
//                    }
//                    else {
//                        Toast.makeText(ServiceSpeechToText.this, "Listening...", Toast.LENGTH_SHORT).show();
//                    }
                    break;
            }
        }
    }

    private class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived ");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
            checkListening = true;
        }

        @Override
        public void onError(int error) {

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    Log.i(TAG, "ERROR_AUDIO");
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    Log.i(TAG, "ERROR_CLIENT");
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    Log.i(TAG, "ERROR_NETWORK");
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    Log.i(TAG, "ERROR_NETWORK_TIMEOUT");
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    Log.i(TAG, "ERROR_INSUFFICIENT_PERMISSIONS");
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    Log.i(TAG, "ERROR_NO_MATCH");
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    Log.i(TAG, "ERROR_RECOGNIZER_BUSY");
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    Log.i(TAG, "ERROR_SERVER");
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    Log.i(TAG, "ERROR_SPEECH_TIMEOUT");
                    break;
            }
            Log.d(TAG, "error = " + error);
            checkListening = true;
//            mIsListening = false;
//            mSpeechRecognizer.stopListening();
//            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            initComponent();
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.i(TAG, "onEvent: " + eventType);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.i(TAG, "onPartialResults");
            mIsListening = false;
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public synchronized void onResults(Bundle results) {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.i(TAG, "result:");
            if (matches.size() > 0) {
                final Intent intent = new Intent(CommonVL.RESULT_SPEECH_TO_TEXT);
                intent.putExtra(CommonVL.CONTENT_SPEECH_TO_TEXT, matches.get(0));

                ServiceSpeechToText.this.sendBroadcast(intent);
                for (int i = 0; i < matches.size(); i++) {
                    Log.i(TAG, "result: " + matches.get(i));
                }
            }
            mIsListening = false;
            checkListening = true;
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        @Override
        public void onRmsChanged(float rmsdB) {
//            Log.i(TAG, "onRmsChanged: " + rmsdB);
        }
    }

    @Override
    public void onDestroy() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
        }
        unregisterBroadcastSpeech();
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }
}
