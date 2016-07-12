package com.example.zxl.cloudmanager.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Post {
    private String name;
    private String content;
    private String postTime;

    public Post() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
