package com.example.zxl.cloudmanager.check.checkManager;

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
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
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
public class ManagerCheckListFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Check> checks = new ArrayList<Check>();
    private CustomRecyclerAdapter<Check> mAdapter;

    private TextView mBack;
    private TextView mSearch;

    public static final int REFRESH_DELAY = 4000;
    private static final String TAG = "MCListFragment";

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private Fragment mFragment;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 0;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {

            try {
                if (null != saveInstanceState.getString(Link.mem_name))
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));
                if (-1 != saveInstanceState.getInt(Link.att_date_from))
                    keyObj.put(Link.att_date_from, saveInstanceState.getInt(Link.att_date_from));
                if (-1 != saveInstanceState.getInt(Link.att_date_to))
                    keyObj.put(Link.att_date_to, saveInstanceState.getInt(Link.att_date_to));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            keyObj.put("sort", "att_date desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + Link.manage_punch, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                checks.add(new Check(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "checks: " + checks);

                            mRecyclerView = (RecyclerView) v.findViewById(R.id.manager_check_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView) v.findViewById(R.id.fragment_manager_check);
                            mAdapter = new CustomRecyclerAdapter<Check>(mFragment.getActivity(), checks, R.layout.manager_check_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, Check data) {
                                    viewHolder.setText(R.id.manager_check_card_item_name, data.getMem_name())
                                            .setText(R.id.manager_check_card_item_state, DateForGeLingWeiZhi.fromGeLinWeiZhi(data.getAtt_date() + 28800))
                                            .setText(R.id.manager_check_card_item_check_location, data.getPuncher_name())
                                            .setText(R.id.manager_check_card_item_check_manager, data.getMaster_name());
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = ManagerCheckDetailFragment.newInstance(data);

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
                        Log.e(TAG, "接收数据异常: " + e.getLocalizedMessage());
                    }
                } else {

                }
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_manager_check_list, parent, false);
        Log.d(TAG, "调用了一次");

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
                        checks.clear();
                        mCurl_page = 0;
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
                        mCurl_page = mCurl_page + 10;
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

        mSearch = (TextView) v.findViewById(R.id.main_fragment_manager_check_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new ManagerCheckQueryFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        return v;
    }
}
