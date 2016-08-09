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
    private static ArrayList<Post> mPosts = new ArrayList<Post>();

    private String[] content;
    private String[] content2;
    private String[] content3;

    private Context context;

    private PostLab(Context c) {
        context = c;
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

    public void setPosts(Post post) {
        mPosts.add(post);
    }
}
