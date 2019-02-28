package com.github.imanx;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by ImanX.
 * sample | Copyrights 2019 ZarinPal Crop.
 */
public class Animation {
    public void run(View v, long duration) {
//        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .3f);
//        fadeOut.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", .4f, 1f);
        fadeIn.setDuration(duration);
        fadeIn.start();

    }
}
