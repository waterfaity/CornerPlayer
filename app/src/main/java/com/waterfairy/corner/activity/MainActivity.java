package com.waterfairy.corner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waterfairy.corner.R;
import com.waterfairy.corner.adapter.PicAdapter;
import com.waterfairy.corner.application.MyApp;
import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.presenter.MainPresenter;
import com.waterfairy.corner.utils.MetricsUtils;
import com.waterfairy.corner.utils.PermissionUtils;
import com.waterfairy.corner.view.MainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    //presenter
    private MainPresenter mPresenter;
    //data
    private int mWidth;
    private float mDensity;
    private int mHeight;

    //view
    private HorizontalScrollView mScrollView;//水平滚动view
    private RelativeLayout mMainView;//主view
    private LinearLayout mLLScrollView;//时间轴内容
    private LinearLayout mLLStroke;//边框
    private GridView mGridView;//资源列表
    private HorizontalScrollView mScrollViewLib;//资源列表滚动
    //adapter
    private PicAdapter mAdapter;
    private List<ImageBean> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置view
        setContentView(R.layout.activity_main);
        initPermission();
        //初始化边距大小
        initMetrics();
        //找到view
        findView();
        //初始化view
        initView();
        //初始化数据
        initData();
    }

    private void initPermission() {
        PermissionUtils.requestPermission(this);
    }

    private void initMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        MyApp.getInstance().initMetrics(displayMetrics);

    }

    private void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.findImg();
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
        //
        ((RelativeLayout.LayoutParams) mScrollViewLib.getLayoutParams()).topMargin = getLen(MetricsUtils.top_library);
        //初始化时间轴view
        initScrollView();

    }

    private void initGridView(int size) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (size / 2 + 1) * (getLen(MetricsUtils.item_library + MetricsUtils.right_item_library
                )),
                LinearLayout.LayoutParams.FILL_PARENT);
        mGridView.setNumColumns(size / 2 + 1); // 设置列数量=列表集合数

        mGridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        mGridView.setColumnWidth(getLen(MetricsUtils.item_library)); // 设置列表项宽
        mGridView.setHorizontalSpacing(getLen(MetricsUtils.right_item_library)); // 设置列表项水平间距
        mGridView.setVerticalSpacing(getLen(MetricsUtils.top_item_library)); // 设置列表项水平间距
        mGridView.setStretchMode(GridView.NO_STRETCH);


    }

    //24个小时内容
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
        mGridView = (GridView) findViewById(R.id.grid);
        mScrollViewLib = (HorizontalScrollView) findViewById(R.id.scrollView_lib);
    }

    private int getLen(int len) {
        return MetricsUtils.getLen(len);
    }

    @Override
    public void displayImg(List<ImageBean> list) {
        if (mAdapter == null) {
            mList = new ArrayList<>();
            mList.addAll(list);
            initGridView(mList.size());
            mAdapter = new PicAdapter(this, mList);
            mGridView.setAdapter(mAdapter);

        }
    }
}
