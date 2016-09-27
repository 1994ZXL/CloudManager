package com.example.zxl.cloudmanager.leave.myLeave;

import android.app.Fragment;
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
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Leave;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveQueryFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Leave> leaves = new ArrayList<Leave>();
    private CustomRecyclerAdapter<Leave> mAdapter;

    public static final int REFRESH_DELAY = 4000;
    private Fragment mFragment;
    private static final String TAG = "MyLeaveQueryFragment";

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Button mSearchBtn;

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
        leaves.clear();
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (0 != saveInstanceState.getInt(Link.leave_type))
                    keyObj.put(Link.leave_type, saveInstanceState.getInt(Link.leave_type));

                if (0 != saveInstanceState.getInt(Link.status))
                    keyObj.put(Link.status, saveInstanceState.getInt(Link.status));

                if (-1 != saveInstanceState.getInt(Link.start_time))
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));

                if (-1 != saveInstanceState.getInt(Link.end_time))
                    keyObj.put(Link.end_time, saveInstanceState.getInt(Link.end_time));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put(Link.page_count, 20);
            keyObj.put(Link.curl_page, curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key:" + key);
        mHttpc.post(Link.localhost + "my_leave&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            for (int i = 0; i < array.length(); i++) {
                                leaves.add(new Leave(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView) v.findViewById(R.id.leave_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView) v.findViewById(R.id.fragment_my_leave);
                            mAdapter = new CustomRecyclerAdapter<Leave>(mFragment.getActivity(), leaves, R.layout.leave_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, Leave data) {
                                    viewHolder.setText(R.id.leave_card_item_name, data.getMem_name())
                                            .setText(R.id.leave_card_item_illness, data.getLeave_type())
                                            .setText(R.id.leave_card_item_state, data.getStatus());

                                    if (data.getStart_time() == 0)
                                        viewHolder.setText(R.id.leave_card_item_begin_time, "--");
                                    else viewHolder.setText(R.id.leave_card_item_begin_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getStart_time()));

                                    if (data.getEnd_time() == 0)
                                        viewHolder.setText(R.id.leave_card_item_end_time, "--");
                                    else viewHolder.setText(R.id.leave_card_item_end_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getEnd_time()));
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyLeaveDetailFragment.newInstance(data);
                                    fragment.setArguments(saveInstanceState);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    if (!fragment.isAdded()) {
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
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }

            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_leave_query, parent, false);

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;
        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_leave_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        leaves.clear();
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        leaves.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        return v;
    }
}
