package com.airwhip.circle.tips.anim;

import android.view.animation.AlphaAnimation;

/**
 * Created by Whiplash on 09.03.14.
 */
public class Fade extends AlphaAnimation {

    public Fade(float from, float to) {
        super(from, to);
        this.setFillEnabled(true);
        this.setFillAfter(true);
        this.setDuration(300);
    }
}
