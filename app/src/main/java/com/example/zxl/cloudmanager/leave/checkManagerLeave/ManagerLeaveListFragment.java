package com.example.zxl.cloudmanager.leave.checkManagerLeave;

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
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.leave.leader.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveDetailFragment;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveSearchFragment;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/15.
 */
//考勤主管 领导 请假查询列表界面
public class ManagerLeaveListFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Leave> leaves = new ArrayList<Leave>();
    private CustomRecyclerAdapter<Leave> mAdapter;
    public static final int REFRESH_DELAY = 4000;

    private TextView mBack;
    private TextView mSearch;
    private TextView mTitle;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private String key = "";
    private JSONObject keyObj = new JSONObject();

    private static final String TAG = "MLeaveListFragment";

    private String url = new String();

    private Fragment mFragment;
    private Fragment mAimFragment;

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
        if (null == saveInstanceState) {
            Log.d(TAG, "没有选择条件");
        } else {
            try {
                if (null != saveInstanceState.getString(Link.mem_name))
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));

                if (-1 != saveInstanceState.getInt(Link.start_time))
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));

                if (-1 != saveInstanceState.getInt(Link.end_time))
                    keyObj.put(Link.end_time, saveInstanceState.getInt(Link.end_time));

                keyObj.put(Link.leave_type, saveInstanceState.getInt(Link.leave_type));
                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
                keyObj.put(Link.mem_id, User.newInstance().getUser_id());
                keyObj.put(Link.mem_job, User.newInstance().getMem_job());
                keyObj.put(Link.comp_id, User.newInstance().getComp_id());
                keyObj.put(Link.is_pmmaster, User.newInstance().getIs_pmmaster());
                keyObj.put(Link.is_puncher, User.newInstance().getIs_puncher());
                keyObj.put(Link.is_pmleader, User.newInstance().getIs_pmleader());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            keyObj.put("sort", "start_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);

            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        if (mFragment.getActivity().getClass() == LeaderLeaveSearchActivity.class) {
            url = Link.localhost + Link.leave_list + Link.get_list;
        } else if (mFragment.getActivity().getClass() == ManagerLeaveActivity.class) {
            url = Link.localhost + Link.manage_leave + Link.get_list;
        }
        Log.d(TAG, "url: " + url);
        mHttpc.post(url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                leaves.add(new Leave(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView) v.findViewById(R.id.manager_check_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView) v.findViewById(R.id.fragment_my_check);
                            mAdapter = new CustomRecyclerAdapter<Leave>(mFragment.getActivity(), leaves, R.layout.cm_leave_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, Leave data) {
                                    viewHolder.setText(R.id.cm_leave_card_item_name, data.getMem_name())
                                            .setText(R.id.cm_leave_card_item_state, data.getStatus())
                                            .setText(R.id.cm_leave_card_item_type, data.getLeave_type());

                                    if (data.getStart_time() == 0)
                                        viewHolder.setText(R.id.cm_leave_card_item_begin_time, "--");
                                    else viewHolder.setText(R.id.cm_leave_card_item_begin_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(data.getStart_time()));

                                    if (data.getEnd_time() == 0)
                                        viewHolder.setText(R.id.cm_leave_card_item_end_time, "--");
                                    else viewHolder.setText(R.id.cm_leave_card_item_end_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(data.getEnd_time()));

                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    if (mFragment.getActivity().getClass() == LeaderLeaveSearchActivity.class) {
                                        mAimFragment = MyLeaveDetailFragment.newInstance(data);
                                    } else if (mFragment.getActivity().getClass() == ManagerLeaveActivity.class) {
                                        mAimFragment = ManagerLeaveDealFragment.newInstance(data);
                                    }
                                    mAimFragment.setArguments(saveInstanceState);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    if (!mAimFragment.isAdded()) {
                                        transaction.addToBackStack(null);
                                        transaction.hide(mFragment);
                                        transaction.add(R.id.blankActivity, mAimFragment);
                                        transaction.commit();
                                    } else {
                                        transaction.hide(mFragment);
                                        transaction.show(mAimFragment);
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
        final View v = layoutInflater.inflate(R.layout.main_fragment_manager_check_list, parent, false);
        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;
        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.manager_check_refresh);
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

        mBack = (TextView) v.findViewById(R.id.main_fragment_manager_check_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mTitle = (TextView) v.findViewById(R.id.main_fragment_manager_check_title);
        mTitle.setText("请假");

        mSearch = (TextView) v.findViewById(R.id.main_fragment_manager_check_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyLeaveSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        Log.d(TAG, "调用了一次");

        return v;
    }
}
