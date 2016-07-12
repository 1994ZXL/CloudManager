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

    private PostLab(Context c) {

        for(int i = 0;i < 3;i++){
            mPost.setName("张三");
            mPost.setPostTime(getTime());
            mPosts.add(mPost);
        }
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
}
