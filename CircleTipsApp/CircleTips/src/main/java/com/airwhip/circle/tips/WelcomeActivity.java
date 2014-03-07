package com.airwhip.circle.tips;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.airwhip.circle.tips.getters.AccountInformation;

public class WelcomeActivity extends Activity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //TODO test getters
        Log.d("TEST_APP", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        Log.d("TEST_APP", AccountInformation.get(this).toString());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO progress bar test
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    progressBar.setProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
