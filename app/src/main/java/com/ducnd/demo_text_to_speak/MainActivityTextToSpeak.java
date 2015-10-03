package com.ducnd.demo_text_to_speak;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.ducnd.demoslidingtab.MainActivity;
import com.ducnd.myappcommon.R;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by ducnd on 23/09/2015.
 */
public class MainActivityTextToSpeak extends AppCompatActivity implements View.OnClickListener,
        TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    private static final String TAG = "MainActivityTextToSpeak";
    private static final int CHECK_TO_SPEAK = 43;
    private TextToSpeech mTts;
    private EditText editTextOne, editTextTwo;
    private FloatingActionButton floating;
    private HashMap<String, String> myHashAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_text_to_speak);
        initComponent();
    }

    private void initComponent() {
        findViewById(R.id.btnTextToSpeak).setOnClickListener(this);
        editTextOne = (EditText) findViewById(R.id.editTextOne);
        editTextTwo = (EditText) findViewById(R.id.editTextTwo);
        myHashAlarm = new HashMap<>();
        floating = (FloatingActionButton) findViewById(R.id.floating);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTextToSpeak:
//                Log.i(TAG, "onClick_btnTextToSpeak");
//                Intent checkIntent = new Intent();
//                checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//                startActivityForResult(checkIntent, CHECK_TO_SPEAK);
                floating.setImageResource(R.drawable.ic_checkmark);
                Snackbar snackbar = Snackbar.make(findViewById(R.id.floating), "have not text to speak", Snackbar.LENGTH_LONG);
                snackbar.setAction("ACTION", null);

                snackbar.setActionTextColor(ColorStateList.valueOf(Color.parseColor("#4caf50")));
                snackbar.getView().setBackgroundColor(Color.parseColor("#4caf50"));
                snackbar.show();
                mTts = new TextToSpeech(this, this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHECK_TO_SPEAK) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.floating), "have not text to speak", Snackbar.LENGTH_LONG);
            snackbar.setAction("ACTION", null);

            snackbar.setActionTextColor(ColorStateList.valueOf(Color.parseColor("#4caf50")));
            snackbar.getView().setBackgroundColor(Color.parseColor("#4caf50"));
            snackbar.show();
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.i(TAG, "onActivityResult");
                floating.setImageResource(R.drawable.ic_checkmark);
                // success, create the TTS instance
//                myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
//                        String.valueOf(AudioManager.STREAM_ALARM));

                mTts = new TextToSpeech(this, this);
//                mTts.isLanguageAvailable(Locale.ENGLISH);
//                mTts.setOnUtteranceCompletedListener(this);
//
//                mTts.speak("Did you sleep well?", TextToSpeech.QUEUE_FLUSH, myHashAlarm);
//
//                myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
//                        "end of wakeup message ID");
//                Log.i(TAG, "onActivityResult_ max: " + mTts.getMaxSpeechInputLength());
//
//                mTts.speak("I hope so, because it's time to wake up", TextToSpeech.QUEUE_ADD, myHashAlarm);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {
        Log.i(TAG, "onInit");
        mTts.setLanguage(Locale.ENGLISH);
        mTts.speak(editTextOne.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        mTts.speak(editTextTwo.getText().toString(), TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {

    }
}
