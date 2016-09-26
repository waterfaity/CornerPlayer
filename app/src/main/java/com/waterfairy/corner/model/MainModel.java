package com.waterfairy.corner.model;


import android.os.Environment;

import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.callback.DataCallback;
import com.waterfairy.corner.callback.FileFindIngCallback;
import com.waterfairy.corner.utils.ImageInfoUtils;

import java.io.File;
import java.util.List;

/**
 * Created by shui on 2016/9/17.
 */
public class MainModel {
    String url = Environment.getExternalStorageDirectory() + "/corner";

    public void findImg(FileFindIngCallback<List<ImageBean>> dataCallback) {
        File file = new File(url);
        if (!file.exists()) {
            dataCallback.onFailed("资源文件夹不存在");
            return;
        }
        dataCallback.onFinding();
        dataCallback.onSuccess(ImageInfoUtils.getMediaFromFolder(url));

    }
}