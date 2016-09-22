package com.waterfairy.corner.database;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

/**
 * Created by shui on 2016/9/22.
 */
public class PictureDB extends SugarRecord {
    public static int TYPE_IMAGE = 0;
    public static int TYPE_VIDEO = 1;
    public static int TYPE_FOLDER = 2;
    @Column(unique = true, name = "path")
    private String path;
    @Column(unique = true, name = "id")
    private int type;
    @Column(unique = true, name = "name")
    private String name;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
