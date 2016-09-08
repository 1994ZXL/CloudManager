package com.example.zxl.cloudmanager.post.myPost;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/26.
 */
public class MyPostDetailFragment extends Fragment {

    private static final String TAG = "MPDetailFragment";
    private TextView mName;
    private EditText mContent;
    private TextView mSubmitTime;
    private TextView mDailyDate;
    private TextView mState;
    private TextView mLevel;
    private EditText mOpinion;

    private LinearLayout mLevelSpinnerLinearLayout;

    private TextView mSave;
    private TextView mBack;

    private static Post sPost = new Post();

    private static final String MYPOST_CONTENT = "日报内容";

    private static int mPosition;

    private Fragment mFragment;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String content;

    public static MyPostDetailFragment newInstance(Object post) {
        sPost = (Post) post;
        MyPostDetailFragment fragment = new MyPostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        //ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
       // mBtn.setVisibility(View.INVISIBLE);
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
        mOpinion = (EditText) view.findViewById(R.id.post_details_opinion);

        mSave = (TextView) view.findViewById(R.id.post_details_save);
        mBack = (TextView) view.findViewById(R.id.post_details_back);

        mLevelSpinnerLinearLayout = (LinearLayout) view.findViewById(R.id.post_level_spinnerLinearLayout);
    }

    private void control() {
        mLevelSpinnerLinearLayout.setVisibility(View.GONE);
        mName.setText(sPost.getMem_name());
        mState.setText(sPost.getState());
        mLevel.setText(sPost.getLevel());
        mOpinion.setText(sPost.getOpinion());
        mOpinion.setFocusable(false);
        mContent.setText(sPost.getContent());
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (sPost.getReport_time() == 0) {
            mSubmitTime.setText("——");
        } else {
            mSubmitTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getReport_time() + 28800));
        }
        mDailyDate.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sPost.getDaily_date() + 28800));
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyObj.put(Link.daily_id, sPost.getDaily_id());
                    keyObj.put(Link.content, content);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "my_daily&act=edit", mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getActivity(),
                                    response.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                        }
                    }

                });
                Fragment fragment = new MyPostFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_message:
                try {
                    keyObj.put(Link.daily_id, sPost.getDaily_id());
                    keyObj.put(Link.content, content);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "my_daily&act=edit", mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getActivity(),
                                    response.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                        }
                    }

                });
                Fragment fragment = new MyPostFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
