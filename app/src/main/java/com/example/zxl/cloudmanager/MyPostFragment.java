package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostFragment extends ListFragment {

    private ArrayList<Post> mPosts;
    private Button mBtn;
    private Fragment mFragment;


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        mPosts = PostLab.newInstance(getActivity()).getPosts();
        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
    }

    private class PostAdapter extends ArrayAdapter<Post> {

        public PostAdapter(ArrayList<Post> posts){
            super(getActivity(), 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_my_post, null);
            }

            Post p = getItem(position);

            TextView postName = (TextView) convertView.findViewById(R.id.main_fragment_my_post_name);
            postName.setText(p.getName());

            TextView postTime = (TextView) convertView.findViewById(R.id.main_fragment_my_post_time);
            postTime.setText(p.getPostTime());

            return convertView;
        }
    }
}
