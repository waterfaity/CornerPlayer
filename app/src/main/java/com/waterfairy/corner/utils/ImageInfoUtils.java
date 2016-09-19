package com.waterfairy.corner.utils;

import android.text.TextUtils;

import com.waterfairy.corner.bean.ImageBean;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by m on 2016/9/18.
 */
public class ImageInfoUtils {
    public static String ImageCollection = "jpg" + "jpeg" + "png";
    public static String VideoCollection = "mp4" + "3gp" + "avi" + "mkv";
    public static int TYPE_IMAGE = 0;
    public static int TYPE_Video = 1;
    public static int TYPE_Folder = 2;


    /*
    * 返回文件夹下的图片，如果是文件夹，则返回该文件夹的一张图片
    * */
    public static List<ImageBean> getFolderImager(String dirPath) {
        List<ImageBean> images = new LinkedList<ImageBean>();
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                ImageBean bean = new ImageBean();
                if (file.isDirectory()) {
                    bean = getFirstImageBean(file);
                } else {
                    if (isImage(file)) {
                        bean.setType(TYPE_IMAGE);
                    } else if (isVideo(file)) {
                        bean.setType(TYPE_Video);
                    }
                    bean.setPath(file.getAbsolutePath());
                    bean.setName(file.getName());
                }

                images.add(bean);
            }

        }
        return images;
    }

    /*
    * 获取文件格式
    * */
    public static String getFileFormat(File file) {
        String name = file.getName();
        String format = name.substring(name.lastIndexOf("."));
        if (TextUtils.isEmpty(format)) {

        }
        return format;
    }

    /*
    * 判断文件是否为图片
    * */
    public static boolean isImage(File file) {
        boolean isImage = false;
        String format = getFileFormat(file);
        if (ImageCollection.contains(format.toLowerCase())) {
            isImage = true;
        }
        return isImage;
    }

    /*
    * 判断文件是否为视频
    * */
    public static boolean isVideo(File file) {
        boolean isVideo = false;
        String format = getFileFormat(file);
        if (VideoCollection.contains(format.toLowerCase())) {
            isVideo = true;
        }
        return isVideo;
    }

    /*
    * 获取文件夹中的一张图片的信息
    * */
    public static ImageBean getFirstImageBean(File dir) {
        ImageBean bean = new ImageBean();
        File files[] = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isImage(file) || isVideo(file)) {
                    bean.setName(file.getName());
                    bean.setPath(file.getAbsolutePath());
                }
                bean.setType(TYPE_Folder);
                break;
            }
        }
        return bean;
    }
}

