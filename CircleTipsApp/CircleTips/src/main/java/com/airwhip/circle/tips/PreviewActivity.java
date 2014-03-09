package com.airwhip.circle.tips;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airwhip.circle.tips.anim.Fade;

public class PreviewActivity extends Activity {

    // 0 - health, 1 - communicate, 2 - play
    private ImageButton[] buttons = new ImageButton[3];
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preview);

        buttons[0] = (ImageButton) findViewById(R.id.health);
        buttons[1] = (ImageButton) findViewById(R.id.communicate);
        buttons[2] = (ImageButton) findViewById(R.id.play);
        tip = (TextView) findViewById(R.id.tip);

        for (ImageButton im : buttons) {
            im.setOnLongClickListener(new ButtonHold());
            im.setOnTouchListener(new ButtonRelease());
        }
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

    private class ButtonHold implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            tip.setText(getTip(v));
            tip.startAnimation(new Fade(0f, 1f));

            for (View i : buttons) {
                if (i != v) {
                    i.startAnimation(new Fade(1f, .4f));
                }
            }
            return false;
        }
    }

    private class ButtonRelease implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tip.startAnimation(new Fade(1f, 0f));

                for (View i : buttons) {
                    if (i != v) {
                        i.startAnimation(new Fade(.4f, 1f));
                    }
                }
            }
            return false;
        }
    }

}
