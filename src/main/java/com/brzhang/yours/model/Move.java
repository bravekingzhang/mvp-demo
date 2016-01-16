package com.brzhang.yours.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by brzhang on 15/12/21.
 * Description :数据基本模型
 */
public class Move extends RealmObject {
    @PrimaryKey
    @Expose
    private String url;
    @Expose
    private String move_pic;
    @Expose
    private String move_name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMove_pic() {
        return move_pic;
    }

    public void setMove_pic(String move_pic) {
        this.move_pic = move_pic;
    }

    public String getMove_name() {
        return move_name;
    }

    public void setMove_name(String move_name) {
        this.move_name = move_name;
    }

}
