package com.airwhip.circle.tips;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

    private ProgressBar progressBar;
    private TextView processText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        processText = (TextView) findViewById(R.id.processText);

        //TODO move to res
        processText.setText("Loading achievements...");
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
