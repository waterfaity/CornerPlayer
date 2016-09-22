package com.waterfairy.corner.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;

/**
 * Created by m on 2016/9/18.
 */
public class ImageBean extends SugarRecord {
    private String path;
    private Long size;
    private String name;
    private int height;
    private int width;
    private String compressPath;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
