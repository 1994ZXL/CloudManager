package com.example.zxl.cloudmanager.bug.myBug;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.bug.projectManagerBug.PMBugActivity;
import com.example.zxl.cloudmanager.bug.projectManagerBug.PMBugDetailFragment;
import com.example.zxl.cloudmanager.bug.publicSearchBug.PublicBugSearchActivity;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.bug.publicSearchBug.BugSearchFragment;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyBugFragment extends Fragment {
    private static final String TAG = "MyBugFragment";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Bug> bugs = new ArrayList<Bug>();
    private CustomRecyclerAdapter<Bug> mAdapter;

    private Button mAddTextView;
    private TextView mBack;
    private TextView mSearch;
    private TextView mTitle;

    private Fragment mFragment;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    @Override
    public void onPause() {
        super.onPause();
        bugs.clear();
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page,final View view) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.project_name))
                    keyObj.put(Link.project_name, saveInstanceState.getString(Link.project_name));
                if (-1 != saveInstanceState.getInt(Link.submit_time_from))
                    keyObj.put(Link.submit_time_from, saveInstanceState.getInt(Link.submit_time_from));
                if (-1 != saveInstanceState.getInt(Link.submit_time_to))
                    keyObj.put(Link.submit_time_to, saveInstanceState.getInt(Link.submit_time_to));
                if (null != saveInstanceState.getString(Link.mofify_time_from))
                    keyObj.put(Link.mofify_time_from, saveInstanceState.getInt(Link.mofify_time_from));
                if (null != saveInstanceState.getString(Link.modify_time_to))
                    keyObj.put(Link.modify_time_to, saveInstanceState.getInt(Link.modify_time_to));
                if (null != saveInstanceState.getString(Link.submitter))
                    keyObj.put(Link.submitter, saveInstanceState.getString(Link.submitter));
                if (null != saveInstanceState.getString(Link.modifier))
                    keyObj.put(Link.modifier, saveInstanceState.getString(Link.modifier));
                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
                keyObj.put(Link.level, saveInstanceState.getInt(Link.level));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());

            keyObj.put("sort", "modify_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);

            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + url , mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                bugs.add(new Bug(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView)view.findViewById(R.id.bug_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mAdapter = new CustomRecyclerAdapter<Bug>(mFragment.getActivity(), bugs, R.layout.bug_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, Bug data) {
                                    viewHolder.setText(R.id.main_fragment_my_bug_projectName, data.getProject_name())
                                            .setText(R.id.bug_card_item_bug_version, data.getLevel())
                                            .setText(R.id.bug_card_item_bug_state, data.getStatus());

                                    if (data.getSubmit_time() == 0)
                                        viewHolder.setText(R.id.bug_card_item_found_time, "--");
                                    else viewHolder.setText(R.id.bug_card_item_found_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getSubmit_time()));

                                    if (data.getModify_time() == 0)
                                        viewHolder.setText(R.id.bug_card_item_edit_time, "--");
                                    else viewHolder.setText(R.id.bug_card_item_edit_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getModify_time()));
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment;
                                    if (mFragment.getActivity().getClass() == MyBugActivity.class)
                                        fragment = MyBugDetailFragment.newInstance(data);
                                    else fragment = PMBugDetailFragment.newInstance(data);
                                    if (null != saveInstanceState) {
                                        Bundle bundle = new Bundle(saveInstanceState);
                                        fragment.setArguments(bundle);
                                    }
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    if (!fragment.isAdded()) {
                                        transaction.addToBackStack(null);
                                        transaction.hide(mFragment);
                                        transaction.add(R.id.blankActivity, fragment);
                                        transaction.commit();
                                    } else {
                                        transaction.hide(mFragment);
                                        transaction.show(fragment);
                                        transaction.commit();
                                    }
                                }
                            });
                            mRecyclerView.setAdapter(mAdapter);

                        } else {

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                } else {

                }
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_bug, parent, false);
        mAddTextView = (Button) v.findViewById(R.id.my_bug_add);
        mBack = (TextView) v.findViewById(R.id.my_bug_back);
        mSearch = (TextView) v.findViewById(R.id.my_bug_search);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new BugSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        mTitle = (TextView) v.findViewById(R.id.bug_title);

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;
        if (mFragment.getActivity().getClass() == MyBugActivity.class) {
            url = Link.my_bug + Link.get_list;
        } else if (mFragment.getActivity().getClass() == PMBugActivity.class){
            url = Link.pm_bug + Link.get_list;
            mTitle.setText("bug");
        } else if (mFragment.getActivity().getClass() == PublicBugSearchActivity.class) {
            url = Link.ps_bug + Link.get_list;
            mTitle.setText("bug");
            mAddTextView.setVisibility(View.GONE);
        }

        if (mFragment.getActivity().getClass() != PMBugActivity.class) {
            mAddTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new MyBugAddFragment();

                    if (null != saveInstanceState) {
                        Bundle bundle = new Bundle(saveInstanceState);
                        fragment.setArguments(bundle);
                    }

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
        } else {
            mAddTextView.setVisibility(View.GONE);
        }

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_bug_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        bugs.clear();
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        bugs.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });
        return v;
    }
}
