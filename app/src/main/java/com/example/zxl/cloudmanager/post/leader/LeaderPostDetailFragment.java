package com.example.zxl.cloudmanager.post.leader;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Post;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/26.
 */
public class LeaderPostDetailFragment extends Fragment {

    private TextView mName;
    private EditText mContent;
    private TextView mSubmitTime;
    private TextView mDailyDate;
    private TextView mState;
    private TextView mLevel;
    private TextView mOpinion;

    private static Post sPost = new Post();

    private Fragment mFragment;

    public static LeaderPostDetailFragment newInstance(Post post) {
        sPost = post;
        LeaderPostDetailFragment fragment = new LeaderPostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
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
        mDailyDate = (TextView) view.findViewById(R.id.post_details_date);
        mState = (TextView) view.findViewById(R.id.post_details_state);
        mLevel = (TextView) view.findViewById(R.id.post_details_level);
        mOpinion = (TextView) view.findViewById(R.id.post_details_opinion);
    }

    private void control() {
        mName.setText(sPost.getMem_name());
        mState.setText(sPost.getState());
        mLevel.setText(sPost.getLevel());
        mOpinion.setText(sPost.getOpinion());
        mContent.setText(sPost.getContent());
        if (sPost.getReport_time() == 0) {
            mSubmitTime.setText("——");
        } else {
            mSubmitTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getReport_time()));
        }
        mDailyDate.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sPost.getDaily_date()));

    }
}
