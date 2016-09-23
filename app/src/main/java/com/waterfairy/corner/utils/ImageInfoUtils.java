package com.waterfairy.corner.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.waterfairy.corner.application.MyApp;
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
    public static ContentResolver resolver = MyApp.getInstance().getContentResolver();

   /* *//*
    * 返回文件夹下的图片，如果是文件夹，则返回该文件夹的一张图片
    * */
    public static List<ImageBean> getFolderImager(String dirPath) {
        List<ImageBean> images = new LinkedList<ImageBean>();
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                ImageBean bean = null;
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
* 返回父文件夹的图片列表信息
* */
    public static List<ImageBean> getParentFolderImager(String dirPath) {
        File dir = new File(dirPath);
        String parent = dir.getParent();
        return getFolderImager(parent);
    }


   /*
    * 获取文件格式
    * */
    public static String getFileFormat(File file) {
        String name = file.getName();
        String format = name.substring(name.lastIndexOf("." + 1));
        if (TextUtils.isEmpty(format)) {
//TODO:没有格式的文件返回什么东东；
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
        ImageBean bean = null;
        File files[] = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isImage(file) || isVideo(file)) {
                    bean = new ImageBean();
                    bean.setName(file.getName());
                    bean.setPath(file.getAbsolutePath());
                }
                bean.setType(TYPE_Folder);
                break;
            }
        }
        return bean;
    }
/*
*
* 获取文件夹下的所有图片缩略图
* ————————————————————————————————————————————————————————————————————————————————————————————————
* @param int type ,分别返回三种分辨率模式的缩略图
 *      MediaStore.Images.Thumbnails.MICRO_KIND;//96*96          |——————————————————————|
        MediaStore.Images.Thumbnails.MINI_KIND;//512*384         | 暂时搞不定还不可用    |
        MediaStore.Images.Thumbnails.FULL_SCREEN_KIND;           |——————————————————————
————————————————————————————————————————————————————————————————————————————————————————————————————
   @param Uri uri,选择文件是在内部还是外部存储上
 MediaStore.Images.Media.EXTERNAL_CONTENT_URI;//外部
 MediaStore.Images.Media.INTERNAL_CONTENT_URI;//内部



* */

    public static void getImageCompressedFromDir(Uri uri, String dirPath) {

        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._COUNT,
                MediaStore.Images.Media.PICASA_ID,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.CONTENT_TYPE,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DESCRIPTION,


        };
        String selection = MediaStore.Images.Thumbnails.DATA;
        String[] selectionArgs = {dirPath};

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                Log.i("DATA:", cursor.getString(0));
                Log.i("TITLE:", cursor.getString(0));
                Log.i("BUCKET_DISPLAY_NAME:", cursor.getString(0));
                Log.i("DISPLAYNAME:", cursor.getString(0));
                Log.i("PICASA_ID:", cursor.getString(0));
                Log.i("MIME_TYPE:", cursor.getString(0));
                Log.i("MINI_THUMB_MAGIC:", cursor.getString(0));
                Log.i("CONTENT_TYPE:", cursor.getString(0));
                Log.i("BUCKET_ID:", cursor.getString(0));
                Log.i("DESCRIPTION:", cursor.getString(0));

            }
        }
    }


}

