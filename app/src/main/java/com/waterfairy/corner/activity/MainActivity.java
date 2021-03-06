package com.waterfairy.corner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.waterfairy.corner.R;
import com.waterfairy.corner.adapter.PicAdapter;
import com.waterfairy.corner.application.MyApp;
import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.presenter.MainPresenter;
import com.waterfairy.corner.utils.AnnotationUtils;
import com.waterfairy.corner.utils.MetricsUtils;
import com.waterfairy.corner.utils.PermissionUtils;
import com.waterfairy.corner.view.MainView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener, PicAdapter.OnSrcClickListener, View.OnLongClickListener {
    private static final String TAG = "main";
    //presenter
    private MainPresenter mPresenter;
    //data
    private int mWidth;
    private float mDensity;
    private int mHeight;

    //view
    private HorizontalScrollView mTimeScrollView;//水平滚动view
    private RelativeLayout mMainView;//主view
    private LinearLayout mLLScrollView;//时间轴内容
    //    private LinearLayout mLLStroke;//边框
    private GridView mGridView;//资源列表
    private HorizontalScrollView mScrollViewLib;//资源列表滚动
    //arrow
    private ImageView mArrow;

    //adapter
    private PicAdapter mAdapter;
    private List<ImageBean> mList;
    //时间轴
    private List<LinearLayout> mTimeContent;
    private TextView mPoint;
    //图片/视频预览
    private ImageView mImgView;
    private VideoView mVideoView;

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
                , getLen(MetricsUtils.arrow_bottom)
                , getLen(MetricsUtils.leftAlign)
                , 0);
        //时间轴滚动
        mTimeScrollView.setPadding(0, getLen(MetricsUtils.time_sv_top), 0, getLen(MetricsUtils.time_sv_bottom));
        //时间轴边框
//        mLLStroke.getLayoutParams().height = getLen(MetricsUtils.height_item_time);
        //时间轴内容
//        mLLScrollView.setPadding(0, 0, 0, getLen(MetricsUtils.bottom_item_time)
//        );
        //
        ((RelativeLayout.LayoutParams) mScrollViewLib.getLayoutParams()).topMargin = getLen(MetricsUtils.top_library);
        //初始化时间轴view
        initScrollView();
        //预览
        mImgView.setOnClickListener(this);
        mVideoView.setOnClickListener(this);
        //arrow
        mArrow.getLayoutParams().height = getLen(MetricsUtils.height_arrow);
        mArrow.getLayoutParams().width = getLen(MetricsUtils.height_arrow);


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
        mTimeContent = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_time_line, null);
            //时间 0:00
            TextView time = (TextView) view.findViewById(R.id.time);
            time.setTextSize(getLen(MetricsUtils.size_time));
            time.setTextColor(getResources().getColor(R.color.normal_text));
            time.setText(i + ":00");
            //箭头
            ImageView arrowImg = (ImageView) view.findViewById(R.id.arrow);
            arrowImg.getLayoutParams().width = getLen(MetricsUtils.height_arrow);
            arrowImg.getLayoutParams().height = getLen(MetricsUtils.height_arrow);
            ((LinearLayout.LayoutParams) arrowImg.getLayoutParams()).bottomMargin = getLen(MetricsUtils.height_arrow);
            //时间和监听LinearLayout
            LinearLayout timeLin = (LinearLayout) view.findViewById(R.id.time_lin);
            timeLin.getLayoutParams().width = getLen(MetricsUtils.item_time);
            timeLin.setTag(time);
            time.setTag(i);
            timeLin.setOnClickListener(this);
            //存放多个图片
            LinearLayout imgContent = (LinearLayout) view.findViewById(R.id.time_pic_content);
            //默认放入的一个图片
            ImageView img = (ImageView) view.findViewById(R.id.time_img);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
            layoutParams.height = getLen(MetricsUtils.item_time);
            layoutParams.width = getLen(MetricsUtils.item_time);
            layoutParams.leftMargin = getLen(MetricsUtils.left_item_time);
            if (i == 23) {
                layoutParams.rightMargin = getLen(MetricsUtils.left_item_time);
            }
            img.setTag(i + "-" + 0);
            img.setOnLongClickListener(this);
            mTimeContent.add(imgContent);
            mLLScrollView.addView(view);
        }
    }

    private void findView() {
        mMainView = (RelativeLayout) findViewById(R.id.main_view);
        mTimeScrollView = (HorizontalScrollView) findViewById(R.id.scroll_view_time);
//        mLLStroke = (LinearLayout) findViewById(R.id.time_stroke);
        mLLScrollView = (LinearLayout) findViewById(R.id.scroll_view_lin);
        mGridView = (GridView) findViewById(R.id.grid);
        mScrollViewLib = (HorizontalScrollView) findViewById(R.id.scrollView_lib);
        mPoint = (TextView) findViewById(R.id.point);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mImgView = (ImageView) findViewById(R.id.img_view);
        mArrow = (ImageView) findViewById(R.id.arrow);
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
            mAdapter.setOnSrcClickListener(this);

        }
    }

    /**
     * 如果不为空 说明已经选中时间轴
     * 为空 没哟选中时间轴
     */
    private TextView mLastTime;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_view:
                mImgView.setVisibility(View.GONE);
                break;
            case R.id.video_view:
                mVideoView.setVisibility(View.GONE);
                break;
            case R.id.time_lin:
                TextView time = (TextView) v.getTag();
                int position = (int) time.getTag();
                if (time == mLastTime) {
                    mLastTime = null;
                    time.setTextColor(getResources().getColor(R.color.normal_text));
                    mAdapter.setCheckboxVisibility(false);
                    return;
                }
                if (mLastTime != null) {
                    mLastTime.setTextColor(getResources().getColor(R.color.normal_text));
                }
                time.setTextColor(getResources().getColor(R.color.red));
                mLastTime = time;
                mAdapter.setCheckboxVisibility(true);
                break;
        }
    }

    //资源库长点击

    @Override
    public void onOnLongLick(View view, int position) {
        if (mLastTime == null) {
            RelativeLayout parent = (RelativeLayout) view.getParent();
            HorizontalScrollView scrollView = (HorizontalScrollView) parent.getParent().getParent().getParent();
            int itemWidth = view.getBottom() - view.getTop();
            int x = parent.getLeft() - scrollView.getScrollX() + scrollView.getLeft();
            int y = parent.getTop() + scrollView.getTop();
            Log.i(TAG, "onOnLongLick: X:" + x + "-- Y:" + y);
            AnnotationUtils.transTo(mPoint, x - 10, y - 10);
        }
    }

    @Override
    public void onSrcClick(int pos, int type, String path) {
        if (type == ImageBean.TYPE_IMAGE) {
            mImgView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(new File(path)).resize(0, MyApp.getInstance().getHeight()).into(mImgView);
        } else if (type == ImageBean.TYPE_Video) {
            mVideoView.setVisibility(View.VISIBLE);

        } else {

        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.time_img) {
            String tag = (String) v.getTag();
            String[] split = tag.split("-");
            int pos1 = Integer.parseInt(split[0]);
            int pos2 = Integer.parseInt(split[1]);


            LinearLayout parent = (LinearLayout) v.getParent();
            RelativeLayout parent1 = (RelativeLayout) parent.getParent();
            HorizontalScrollView scrollView = (HorizontalScrollView) parent1.getParent().getParent();
            int x = parent.getLeft() + parent1.getLeft() - scrollView.getScrollX() +
                    scrollView.getLeft() + getLen(MetricsUtils.left_item_time);
            int y = parent.getTop() + parent1.getTop() + +scrollView.getTop();
            Log.i(TAG, "onLongClick: X:" + x + "-- Y:" + y);

            AnnotationUtils.transTo(mPoint, x - 10, y - 10);
        }
        return false;
    }
}
