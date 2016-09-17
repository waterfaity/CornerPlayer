package com.waterfairy.corner.presenter;

import android.app.Activity;

import com.waterfairy.corner.activity.MainActivity;
import com.waterfairy.corner.model.MainModel;
import com.waterfairy.corner.view.MainView;

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
}
