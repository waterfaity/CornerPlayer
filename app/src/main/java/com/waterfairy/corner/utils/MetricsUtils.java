package com.waterfairy.corner.utils;

import com.waterfairy.corner.application.MyApp;

/**
 * Created by shui on 2016/9/17.
 */
public class MetricsUtils {
    public static int leftAlign = 110;//左边距
    public static int topAlign = 24;//上边距

    public static int height_arrow = 19;//箭头高度
    public static int bottom_arrow = 23;//箭头高度

    public static int size_time = 10;//时间 字体大小

    public static int height_item_time = 156;//时间轴条目 高度

    public static int item_time = 126;//时间轴 条目宽度

    public static int right_item_time = 20;//时间轴 条目右侧间距
    public static int top_item_time = 16;//时间轴 条目右侧间距
    public static int bottom_item_time = 14;//时间轴 条目右侧间距

    public static int item_library = 160;//资源库 条目宽度
    public static int right_item_library = 21;//资源库 条目右侧间距
    public static int top_item_library = 16;
    public static int top_library = 36;

    private static int item_time_padding = 16;//时间轴 边框距离
    //比例转换
    private static int height = 1280;

    public static int getLen(int len) {
        return (int) (len / (float) height * MyApp.getInstance().getHeight());

    }


}
