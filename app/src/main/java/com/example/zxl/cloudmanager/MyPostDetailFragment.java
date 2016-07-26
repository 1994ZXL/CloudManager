package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.Post;

/**
 * Created by ZXL on 2016/7/26.
 */
public class MyPostDetailFragment extends Fragment {

    private TextView mName;
    private TextView mContent;
    private TextView mSubmitTime;

    private static Post sPost = new Post();

    public static MyPostDetailFragment newInstance(Post post) {
        sPost = post;
        MyPostDetailFragment fragment = new MyPostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
        mBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_details, container, false);

        init(view);
        control();

        return view;
    }

    private void init(View view) {
        mName = (TextView) view.findViewById(R.id.post_details_name);
        mContent = (TextView) view.findViewById(R.id.post_details_content);
        mSubmitTime = (TextView) view.findViewById(R.id.post_details_submit_time);
    }

    private void control() {
        mName.setText(sPost.getName());
        mContent.setText(sPost.getContent());
        mSubmitTime.setText(sPost.getPostTime());
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
        mBtn.setVisibility(View.VISIBLE);
    }
}
