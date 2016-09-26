package com.waterfairy.corner.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;

/**
 * Created by m on 2016/9/18.
 */
public class ImageBean extends SugarRecord {
    public  static  int  TYPE_IMAGE=0;
    public  static  int  TYPE_Video =1;
    public  static  int  TYPE_Folder=2;
    private String path;
    private String name;
    private String compressPath;
    private int type;
    private  String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String getCompressPath() {
        return compressPath;
    }
    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


}
