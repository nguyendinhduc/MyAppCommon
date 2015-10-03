package com.ducnd.demo_speech_to_text;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ducnd.common.CommonVL;
import com.ducnd.myappcommon.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ducnd on 26/09/2015.
 */
public class MainActivitySpeechToText extends Activity implements View.OnClickListener {
    private static final String TAG = "MainSpeechToText";
    private static final int REQ_CODE_SPEECH_INPUT = 845;
    private ImageButton btnSpeak;
    private TextView txtSpeechInput;
    private BroadcastSpeechToText broadcastSpeechToText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_speech_to_text);
        initComponent();
        registerBroadSpeechToText();
        Intent intent = new Intent();
        intent.setClassName("com.ducnd.myappcommon", "com.ducnd.common.ServiceSpeechToText");
        startService(intent);
    }

    private void initComponent() {
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(this);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
    }

    @Override
    public void onClick(View v) {
//        promptSpeechInput();
        Intent intent = new Intent();
        intent.setAction(CommonVL.REQUEST_RECORD);
        sendBroadcast(intent);
    }

    private void promptSpeechInput() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
//        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speech_prompt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "speech_not_supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult start");
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                Log.i(TAG, "onActivityResult start");
                if ( data == null ) {
                    Log.i(TAG, "onActivityResult data null");
                }
                if ( resultCode != Activity.RESULT_OK ) {
                    Log.i(TAG, "onActivityResult not ok");
                }
                if (resultCode == RESULT_OK && null != data) {
                    Log.i(TAG, "onActivityResult success");
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void registerBroadSpeechToText() {
        if ( broadcastSpeechToText == null ) {
            broadcastSpeechToText = new BroadcastSpeechToText();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonVL.RESULT_SPEECH_TO_TEXT);
            registerReceiver(broadcastSpeechToText, intentFilter);
        }
    }

    private void unregisterBroadTextToSpeech() {
        if ( broadcastSpeechToText != null ) {
            unregisterReceiver(broadcastSpeechToText);
            broadcastSpeechToText = null;
        }
    }

    private class BroadcastSpeechToText extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonVL.RESULT_SPEECH_TO_TEXT:
                    txtSpeechInput.setText(intent.getStringExtra(CommonVL.CONTENT_SPEECH_TO_TEXT));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterBroadTextToSpeech();
        super.onDestroy();
    }
}
