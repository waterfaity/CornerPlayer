package com.waterfairy.corner.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by shui on 2016/9/22.
 */
public class AnnotationUtils {
    private static int lastX, lastY;

    public static void transTo(View view, int x, int y) {
        TranslateAnimation translateAnimation = new TranslateAnimation(lastX, x, lastY, y);
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        lastX = x;
        lastY = y;
        view.startAnimation(translateAnimation);

    }
}
