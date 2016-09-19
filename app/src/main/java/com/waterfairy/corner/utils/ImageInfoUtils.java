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
    public  static  String ImageCollection="jpg"+"jpeg"+"png";
    public  static  String VideoCollection ="mp4"+"3gp"+"avi"+"mkv";
    public static List<ImageBean> getFolderImager(String dirPath){
        List<ImageBean> images = new LinkedList<ImageBean>();
        File dir = new File(dirPath);
        File files[] = dir.listFiles();
        if (files!=null){
            for (File file:files){
                if(file.isDirectory()){

                }else {

                }
            }

        }
        else {
            images = null;
        }



        return images;
    }

    public static String getFileFormat(File file){
     String name = file.getName();
        String format = name.substring(name.lastIndexOf("."+1));
        if (TextUtils.isEmpty(format)){

        }
        return   format;
    }

    public  boolean isImage(File file){
        boolean isImage = false;
        String format = getFileFormat(file);
        if (ImageCollection.contains(format.toLowerCase())){
            isImage = true;
        }
        return isImage;
    }

    public  boolean isVideo(File file){
        boolean isVideo = false;
        String format = getFileFormat(file);
        if (VideoCollection.contains(format.toLowerCase())){
            isVideo = true;
        }
        return isVideo;
    }
}

