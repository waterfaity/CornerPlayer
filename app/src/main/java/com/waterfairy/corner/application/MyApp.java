package com.waterfairy.corner.application;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.waterfairy.corner.utils.PermissionUtils;

/**
 * Created by shui on 2016/9/17.
 */
public class MyApp extends Application {

    private static MyApp myApp;
    private static int width;
    private static int height;
    private static float density;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }



    private void initApp() {
        myApp = this;
    }

    public static MyApp getInstance() {
        return myApp;
    }


    public void initMetrics(DisplayMetrics displayMetrics) {
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        density = displayMetrics.density;
    }

    //
    public int getHeight() {
        if (height > width) {
            return height;
        } else {
            return width;
        }
    }

    //方便在任意位置获取Context
    public static Context getContext(){
        return  getContext();
    }
}
