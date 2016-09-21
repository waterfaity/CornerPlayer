package com.waterfairy.corner.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by shui on 2016/9/19.
 */
public class PermissionUtils {
    private static int PERMISSION_REQUEST = 123;

    public static void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasWriteContactsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }


    }
}
