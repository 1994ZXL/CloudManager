package com.example.zxl.cloudmanager.overtime.myOvertime;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.overtime.checkManagerOverTime.ManagerOverTimeSearchFragment;
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
public class MyOverTimeFragment extends Fragment {
    private static final String MY = null;
    private static final String TAG = "MyOverTimeFragment";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<OverTime> overTimes = new ArrayList<OverTime>();
    private CustomRecyclerAdapter<OverTime> mAdapter;

    private Fragment mFragment;

    private TextView mBack;
    private TextView mSearch;

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

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {

            try {
                keyObj.put(Link.work_pm, saveInstanceState.getString(Link.work_pm));

                if (-1 != saveInstanceState.getInt(Link.start_time)) {
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));
                }

                if (-1 != saveInstanceState.getInt(Link.end_time)) {
                    keyObj.put(Link.end_time, saveInstanceState.getInt(Link.end_time));
                }

                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());

            keyObj.put("sort", "start_time desc");
            keyObj.put("page_count", 20);
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
                                overTimes.add(new OverTime(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "overTimes: " + overTimes);
                            mRecyclerView = (RecyclerView) v.findViewById(R.id.overtime_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView) v.findViewById(R.id.fragment_my_overtime);
                            mAdapter = new CustomRecyclerAdapter<OverTime>(mFragment.getActivity(), overTimes, R.layout.overtime_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, OverTime data) {
                                    if (data.getStart_time() == 0)
                                        viewHolder.setText(R.id.overtime_card_item_begin_time, "--");
                                    else viewHolder.setText(R.id.overtime_card_item_begin_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getStart_time()));

                                    if (data.getEnd_time() == 0)
                                        viewHolder.setText(R.id.overtime_card_item_end_time, "--");
                                    else viewHolder.setText(R.id.overtime_card_item_end_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getEnd_time()));

                                    viewHolder.setText(R.id.main_fragment_overtime_project, data.getWork_pm())
                                            .setText(R.id.overtime_card_item_name, data.getMem_name())
                                            .setText(R.id.overtime_card_item_overtime_reason, data.getWork_resaon());
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyOvertimeDetailFragment.newInstance(data);
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_overtime, parent, false);

        if (mFragment.getActivity().getClass() == MyOverTimeActivity.class) {
            url = Link.my_work + Link.get_list;
        }

        mBack = (TextView) v.findViewById(R.id.my_overtime_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.my_overtime_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new ManagerOverTimeSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_overtime_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        overTimes.clear();
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
                        overTimes.clear();
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
