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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by m on 2016/9/18.
 */
public class ImageInfoUtils {
    /*   public static String ImageCollection = "jpg" + "jpeg" + "png";
       public static String VideoCollection = "mp4" + "3gp" + "avi" + "mkv";*/
    public static int TYPE_IMAGE = 0;
    public static int TYPE_Video = 1;
    public static int TYPE_Folder = 2;
    public static ContentResolver resolver = MyApp.getInstance().getContentResolver();



    /*
    * 获取文件夹下的所有媒体信息
    *
    * */

    public static List<ImageBean> getMediaFromFolder(String dirPath) {
     List<ImageBean> medias = new ArrayList<ImageBean>();
        medias.addAll(getFolderFromFolder(dirPath));
       medias.addAll( getVideoFromFolder(dirPath));
        medias.addAll(getImageFromDir(dirPath));
        return medias;
    }


    public static List<ImageBean> getImageFromDir(String dirPath) {
        List<ImageBean> images = new ArrayList<ImageBean>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uri1 = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,//图片ID 与
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,


        };
        String selection = MediaStore.Images.Media.DATA + " like ?";
        String[] selectionArgs = {"%" + dirPath + "%"};
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        Cursor cursor1 = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageBean imageBean = new ImageBean();
                imageBean.setType(TYPE_IMAGE);
                imageBean.setId(cursor.getLong(0));
                imageBean.setPath(cursor.getString(1));
                imageBean.setName(cursor.getString(2));

                //根据MediaStore.Images.Media._ID 关联MediaStore.Images.Thumbnails.IMAGE_ID查找图片缓存缩略图位置。
                String[] projection1 = {MediaStore.Images.Thumbnails.DATA};
                String selection1 = MediaStore.Images.Thumbnails.IMAGE_ID + "=?";
                String[] selectionArgs1 = {"%" + cursor.getString(0) + "%"};
                cursor1 = resolver.query(uri1, projection1, selection1, selectionArgs1, null);
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        imageBean.setCompressPath(cursor1.getString(0));
                    }
                }

                images.add(imageBean);
            }
            if (cursor1 != null) {
                cursor1.close();
            }

            cursor.close();

        }
        return images;
    }

    /*
    * 获取文件夹下所有视频信息
    * */

    public static List<ImageBean> getVideoFromFolder(String dirPath) {
        List<ImageBean> videos = new ArrayList<ImageBean>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Uri uri1 = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,//路径
                MediaStore.Video.Media.TITLE,//名字
                MediaStore.Video.Media.DURATION};//时长
        String selection = MediaStore.Video.Media.DATA + " like ?";//从路径中选取包含
        String[] selectionArgs = {"%" + dirPath + "%"};//dirPath的
        String sortOrder = MediaStore.Video.Media.DATE_MODIFIED;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        Cursor cursor1 = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageBean imageBean = new ImageBean();
                imageBean.setType(TYPE_Video);
                imageBean.setId(cursor.getLong(0));
                imageBean.setPath(cursor.getString(1));
                imageBean.setName(cursor.getString(2));
                imageBean.setDuration(cursor.getString(3));

                //根据MediaStore.Video.Media._ID 关联MediaStore.Video.Thumbnails.IMAGE_ID查找图片缓存缩略图位置。
                String[] projection1 = {MediaStore.Video.Thumbnails.DATA};
                String selection1 = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";
                String[] selectionArgs1 = {"%" + cursor.getString(0) + "%"};
                cursor1 = resolver.query(uri1, projection1, selection1, selectionArgs1, null);
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        imageBean.setCompressPath(cursor1.getString(0));
                    }
                }

                videos.add(imageBean);
            }
            if (cursor1 != null) {
                cursor1.close();
            }

            cursor.close();

        }
        return videos;
    }


    /*
    * 获取文件夹下含有图片与视频的文件夹的信息。
    * */
    public static List<ImageBean> getFolderFromFolder(String dirPath) {

        List<ImageBean> folders = new ArrayList<ImageBean>();

        File folder = new File(dirPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, null
                );
                if (cursor != null) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setType(TYPE_Folder);
                    imageBean.setPath(file.getPath());
                    imageBean.setName(file.getName());
                    cursor.close();
                    folders.add(imageBean);
                } else {
                    Cursor cursor1 = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null,
                            null, null, null
                    );
                    if (cursor1 != null) {
                        ImageBean imageBean = new ImageBean();
                        imageBean.setType(TYPE_Folder);
                        imageBean.setPath(file.getPath());
                        imageBean.setName(file.getName());
                        cursor1.close();
                        folders.add(imageBean);
                    }
                }

            }
        }
        return folders;
    }





    /* *//*
    * 返回文件夹下的图片，如果是文件夹，则返回该文件夹的一张图片
    * */
 /*   public static List<ImageBean> getFolderImager(String dirPath) {
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
    }*/
/*
    *//*
* 返回父文件夹的图片列表信息
* *//*
    public static List<ImageBean> getParentFolderImager(String dirPath) {
        File dir = new File(dirPath);
        String parent = dir.getParent();
        return getFolderImager(parent);
    }


    *//*
     * 获取文件格式
     * *//*
    public static String getFileFormat(File file) {
        String name = file.getName();
        String format = name.substring(name.lastIndexOf("." + 1));
        if (TextUtils.isEmpty(format)) {
//TODO:没有格式的文件返回什么东东；
        }
        return format;
    }

    *//*
     * 判断文件是否为图片
     * *//*
    public static boolean isImage(File file) {
        boolean isImage = false;
        String format = getFileFormat(file);
        if (ImageCollection.contains(format.toLowerCase())) {
            isImage = true;
        }
        return isImage;
    }

    *//*
    * 判断文件是否为视频
    * *//*
    public static boolean isVideo(File file) {
        boolean isVideo = false;
        String format = getFileFormat(file);
        if (VideoCollection.contains(format.toLowerCase())) {
            isVideo = true;
        }
        return isVideo;
    }

    *//*
    * 获取文件夹中的一张图片的信息
    * *//*
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
    }*/
/*
*
* 获取文件夹下的所有图片信息

* */


}

