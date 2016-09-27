package com.example.zxl.cloudmanager.post.myPost;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.post.leader.LeaderPostDetailFragment;
import com.example.zxl.cloudmanager.post.projectManagerPost.PMPostActivtiy;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostFragment extends Fragment {

    private ArrayList<Post> mPosts = new ArrayList<Post>();

    private RecyclerView mRecyclerView;

//    private MyAdapter myAdapter;
    private CustomRecyclerAdapter<Post> mAdapter;

    private TextView mSearch;
    private TextView mBack;
    private TextView mTitle;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private Fragment mFragment;

    private static final String TAG = "MyPostFragment";

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {

        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.mem_name)) {
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));
                }
                if (-1 != saveInstanceState.getInt(Link.daily_time_from)) {
                    keyObj.put(Link.daily_time_from, saveInstanceState.getInt(Link.daily_time_from));
                }
                if (-1 != saveInstanceState.getInt(Link.daily_time_to)) {
                    keyObj.put(Link.daily_time_to, saveInstanceState.getInt(Link.daily_time_to));
                }
                if (null != saveInstanceState.getString(Link.content)) {
                    keyObj.put(Link.content, saveInstanceState.getString(Link.content));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
            keyObj.put(Link.is_pmleader, User.newInstance().getIs_pmleader());
            keyObj.put("sort", "daily_date desc");
            keyObj.put("page_count", 7);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                mPosts.add(new Post(array.getJSONObject(i)));

                                mRecyclerView = (RecyclerView)v.findViewById(R.id.post_recyclerView);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setHasFixedSize(true);
                                mAdapter = new CustomRecyclerAdapter<Post>(mFragment.getActivity(), mPosts, R.layout.leader_post_list) {
                                    @Override
                                    protected void display(ViewHolderHelper viewHolder, Post data) {
                                        viewHolder.setText(R.id.leader_post_list_name, data.getMem_name())
                                                .setText(R.id.leader_post_list_data, DateForGeLingWeiZhi.fromGeLinWeiZhi(data.getDaily_date() + 28800))
                                                .setText(R.id.leader_post_list_opinion, data.getOpinion())
                                                .setText(R.id.leader_post_list_status, data.getState())
                                                .setText(R.id.leader_post_list_content, data.getContent());
                                    }
                                };
                                mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, Object data) {
                                        Fragment fragment;
                                        if (mFragment.getActivity().getClass() != MyPostActivity.class) {
                                            fragment = LeaderPostDetailFragment.newInstance(data);
                                        } else {
                                            fragment = MyPostDetailFragment.newInstance(data);
                                        }
                                        fragment.setArguments(saveInstanceState);
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
                            }
                            Log.d(TAG, "mPosts: " + mPosts);

                        } else {

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                return;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.leader_post, container, false);

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        if (mFragment.getActivity().getClass() != MyPostActivity.class) {
            mTitle = (TextView) view.findViewById(R.id.leader_post_title);
            mTitle.setText("日报");
            if (mFragment.getActivity().getClass() == PMPostActivtiy.class)
                url = Link.manage_daily + Link.get_list;
            else url = Link.find_daily + Link.get_list;
        } else {
            url = Link.my_daily + Link.get_list;
        }

        loadDate(saveInstanceState, mCurl_page, view);

        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.my_post_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mPosts.clear();
                        mCurl_page = 1;
                        loadDate(saveInstanceState, mCurl_page, view);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, view);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        mBack = (TextView) view.findViewById(R.id.leader_post_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) view.findViewById(R.id.leader_post_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyPostSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        return view;
    }
}
