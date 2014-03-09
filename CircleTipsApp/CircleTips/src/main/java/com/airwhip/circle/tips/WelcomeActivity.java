package com.airwhip.circle.tips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.airwhip.circle.tips.getters.AccountInformation;
import com.airwhip.circle.tips.getters.ApplicationInformation;
import com.airwhip.circle.tips.getters.BrowserInformation;
import com.airwhip.circle.tips.getters.MusicInformation;

public class WelcomeActivity extends Activity {

    private static final String XML_DOCUMENT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    private static final String USER_TAG_BEGIN_1 = "<user id=\"";
    private static final String USER_TAG_BEGIN_2 = "\">\n";
    private static final String USER_TAG_END = "</user>";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO progress bar test
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder information = new StringBuilder(XML_DOCUMENT);
                information.append(USER_TAG_BEGIN_1 + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + USER_TAG_BEGIN_2);
                information.append(ApplicationInformation.get(getApplicationContext()));
                progressBar.setProgress(20);
                information.append(AccountInformation.get(getApplicationContext()));
                progressBar.setProgress(40);
                information.append(BrowserInformation.getHistory(getApplicationContext()));
                progressBar.setProgress(60);
                information.append(BrowserInformation.getBookmarks(getApplicationContext()));
                progressBar.setProgress(80);
                information.append(MusicInformation.get(getApplicationContext()));
                progressBar.setProgress(100);
                information.append(USER_TAG_END);

                //TODO run service and send information
                Intent intent = new Intent(WelcomeActivity.this, PreviewActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        Log.d("TEST_APP", "DESTROY");
        super.onDestroy();
    }
}
