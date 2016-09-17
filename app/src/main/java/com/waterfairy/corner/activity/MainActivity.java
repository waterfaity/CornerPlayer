package com.waterfairy.corner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.waterfairy.corner.R;
import com.waterfairy.corner.application.MyApp;
import com.waterfairy.corner.presenter.MainPresenter;
import com.waterfairy.corner.utils.MetricsUtils;
import com.waterfairy.corner.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {
    private MainPresenter mPresenter;
    private int mWidth;
    private float mDensity;
    private int mHeight;

    //view
    private HorizontalScrollView mScrollView;//水平滚动view
    private RelativeLayout mMainView;//主view
    private LinearLayout mLLScrollView;
    private LinearLayout mLLStroke;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initMetrics();
        findView();
        initView();
        initData();
    }

    private void initMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        MyApp.getInstance().initMetrics(displayMetrics);

    }

    private void initData() {
        mPresenter = new MainPresenter(this);
    }

    private void initView() {
        //主view
        mMainView.setPadding(getLen(MetricsUtils.leftAlign)
                , getLen(MetricsUtils.topAlign)
                , getLen(MetricsUtils.leftAlign)
                , 0
        );
        //时间轴边框
        mLLStroke.getLayoutParams().height = getLen(MetricsUtils.height_item_time);
        //

    }

    private void findView() {
        mMainView = (RelativeLayout) findViewById(R.id.main_view);
        mLLStroke = (LinearLayout) findViewById(R.id.time_stroke);

    }

    private int getLen(int len) {
        return MetricsUtils.getLen(len);

    }
}
