package com.example.zxl.cloudmanager.post.leader;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.post.projectManagerPost.PMPostActivtiy;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/26.
 */
public class LeaderPostDetailFragment extends Fragment {
    private static final String TAG = "LPostDetailFragment";

    private TextView mName;
    private EditText mContent;
    private TextView mSubmitTime;
    private TextView mDailyDate;
    private TextView mState;
    private Spinner mLevel;
    private TextView mLevelTextView;
    private EditText mOpinion;

    private LinearLayout mLevelSpinnerLinearLayout;
    private LinearLayout mLevelLinearLayout;

    private TextView mEdit;
    private TextView mBack;

    private static Post sPost = new Post();

    private static final String[] levelList = new String[]{"全部", "很满意", "满意", "一般"};
    private ArrayAdapter<String> levelAdapter;
    private int level;

    private String opinion;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

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
        mOpinion = (EditText) view.findViewById(R.id.post_details_opinion);
        mEdit = (TextView) view.findViewById(R.id.post_details_save);
        mLevelTextView = (TextView) view.findViewById(R.id.post_details_level);
        mLevel = (Spinner) view.findViewById(R.id.post_details_level_spinner);

        mLevelSpinnerLinearLayout = (LinearLayout) view.findViewById(R.id.post_level_spinnerLinearLayout);
        mLevelLinearLayout = (LinearLayout) view.findViewById(R.id.post_level_linearLayout);

        mBack = (TextView) view.findViewById(R.id.post_details_back);
    }

    private void control() {
        mName.setText(sPost.getMem_name());
        mState.setText(sPost.getState());

        mOpinion.setText(sPost.getOpinion());
        mContent.setText(sPost.getContent());
        if (sPost.getReport_time() == 0) {
            mSubmitTime.setText("——");
        } else {
            mSubmitTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getReport_time()));
        }
        mDailyDate.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sPost.getDaily_date()));

        if (mFragment.getActivity().getClass() == PMPostActivtiy.class){
            mLevelLinearLayout.setVisibility(View.GONE);
            levelAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, levelList);
            levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mLevel.setAdapter(levelAdapter);
            mLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    level = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            mOpinion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    opinion = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (0 != level || null != opinion) {
                        try {
                            if (0 != level)
                                keyObj.put(Link.level, level);

                            if (null != opinion)
                                keyObj.put(Link.opinion, opinion);

                            keyObj.put(Link.user_id, User.newInstance().getUser_id());
                            keyObj.put(Link.daily_id, sPost.getDaily_id());
                            key = DESCryptor.Encryptor(keyObj.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mParams.put("key", key);

                        mHttpc.post(Link.localhost + Link.manage_daily + Link.edit, mParams, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                                if (statusCode == 200) {
                                    try {
                                        Toast.makeText(getActivity(),
                                                rjo.getString("msg"),
                                                Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                                    }
                                }
                            }
                        });
                        Fragment fragment = new LeaderPostListFragment();
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
                    } else {
                        Toast.makeText(getActivity(),
                                "并没有任何修改内容哦",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (mFragment.getActivity().getClass() == LeaderPostSearchActivity.class) {
            mEdit.setVisibility(View.INVISIBLE);
            mLevelSpinnerLinearLayout.setVisibility(View.GONE);
            mEdit.setVisibility(View.INVISIBLE);
            mLevelTextView.setText(sPost.getLevel());
            mOpinion.setFocusable(false);
        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }

}
