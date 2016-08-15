package com.example.zxl.cloudmanager.post.leader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Post;

/**
 * Created by ZXL on 2016/7/26.
 */
public class LeaderPostDetailFragment extends Fragment {

    private TextView mName;
    private EditText mContent;
    private TextView mSubmitTime;
    private TextView mCreateTime;

    private static Post sPost = new Post();

    public static LeaderPostDetailFragment newInstance(Post post) {
        sPost = post;
        LeaderPostDetailFragment fragment = new LeaderPostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mContent = (EditText) view.findViewById(R.id.post_details_content);
        mSubmitTime = (TextView) view.findViewById(R.id.post_details_submit_time);
        mCreateTime = (TextView) view.findViewById(R.id.post_details_create_time);
    }

    private void control() {
        mName.setText(sPost.getMem_name());
        mContent.setText(sPost.getContent());
        mContent.setFocusable(false);
        if (sPost.getReport_time() == 0) {
            mSubmitTime.setText("——");
        } else {
            mSubmitTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getReport_time()));
        }

        mCreateTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getCreate_time()));
    }

}
