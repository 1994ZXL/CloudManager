package com.example.zxl.cloudmanager.model;

import android.content.Context;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class PostLab {
    private Post mPost = new Post();
    private static PostLab sPost;
    private ArrayList<Post> mPosts = new ArrayList<Post>();

    private String[] content;
    private String[] content2;

    private Context context;

    private PostLab(Context c) {
        context = c;

        content = new String[]{
                "张三",
                "1.编写了商品分类列表和轮换图（完成）2.学习了document.getElementById方法和setInterval方法的使用（完成）3.遇到了setInterval在window.onload里无法多次运行的问题并解决。",
                "2016.7.26"};
        content2 = new String[]{
                "李四",
                "1.编写了商品分类列表和轮换图（完成）2.学习了document.getElementById方法和setInterval方法的使用（完成）3.遇到了setInterval在window.onload里无法多次运行的问题并解决。",
                "2016.7.28"};
        set(content);
        set(content2);
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    public static PostLab newInstance(Context c) {
        if(sPost == null){
            sPost = new PostLab(c.getApplicationContext());
        }
        return sPost;
    }

    public ArrayList<Post> getPosts(){
        return mPosts;
    }

    public void set(String[] content) {
        Post post = new Post();
        post.set(content);
        mPosts.add(post);
    }
}
