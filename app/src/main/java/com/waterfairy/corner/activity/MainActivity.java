package com.waterfairy.corner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private LinearLayout mLLScrollView;//时间轴内容
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
        //时间轴内容
        mLLScrollView.setPadding(0, 0, 0, getLen(MetricsUtils.bottom_item_time)
        );
        initScrollView();
    }

    private void initScrollView() {
        for (int i = 0; i < 24; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_time_line, null);
            TextView time = (TextView) view.findViewById(R.id.time);

            time.setTextSize(getLen(MetricsUtils.size_time));
            time.setText(i + ":00");
            ImageView arrowImg = (ImageView) view.findViewById(R.id.arrow);
            arrowImg.getLayoutParams().width = getLen(MetricsUtils.height_arrow);
            arrowImg.getLayoutParams().height = getLen(MetricsUtils.height_arrow);
            ((LinearLayout.LayoutParams) arrowImg.getLayoutParams()).bottomMargin = getLen(MetricsUtils.height_arrow);
            LinearLayout timeLin = (LinearLayout) view.findViewById(R.id.time_lin);
            timeLin.getLayoutParams().width = getLen(MetricsUtils.item_time);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            img.getLayoutParams().height = getLen(MetricsUtils.item_time);
            img.getLayoutParams().width = getLen(MetricsUtils.item_time);

            mLLScrollView.addView(view);
        }
    }

    private void findView() {
        mMainView = (RelativeLayout) findViewById(R.id.main_view);
        mLLStroke = (LinearLayout) findViewById(R.id.time_stroke);
        mLLScrollView = (LinearLayout) findViewById(R.id.scroll_view_lin);
    }

    private int getLen(int len) {
        return MetricsUtils.getLen(len);
    }
}
