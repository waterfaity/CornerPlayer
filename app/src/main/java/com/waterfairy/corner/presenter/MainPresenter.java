package com.waterfairy.corner.presenter;

import android.app.Activity;

import com.waterfairy.corner.activity.MainActivity;
import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.callback.DataCallback;
import com.waterfairy.corner.callback.FileFindIngCallback;
import com.waterfairy.corner.model.MainModel;
import com.waterfairy.corner.view.MainView;

import java.util.List;

/**
 * Created by shui on 2016/9/17.
 */
public class MainPresenter {

    private MainModel mModel;
    private MainView mView;
    private Activity mActivity;

    public MainPresenter(MainActivity activity) {

        mModel = new MainModel();
        mView = activity;
        mActivity = activity;
    }

    public void findImg() {
        mModel.findImg(new FileFindIngCallback<List<ImageBean>>() {

            @Override
            public void onSuccess(List<ImageBean> list) {
                mView.displayImg(list);
            }

            @Override
            public void onFailed(String string) {

            }

            @Override
            public void onFinding() {

            }
        });
    }
}
