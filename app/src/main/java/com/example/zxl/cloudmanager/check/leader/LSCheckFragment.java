package com.example.zxl.cloudmanager.check.leader;

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
import com.example.zxl.cloudmanager.check.myCheck.MyCheckDetailFragment;
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

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/11.
 */
public class LSCheckFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Check> checks = new ArrayList<Check>();
    private CustomRecyclerAdapter<Check> mAdapter;

    private TextView mBack;
    private TextView mTitle;
    private TextView mSearch;

    private static final String TAG = "MyCheckFragment";

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

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
            keyObj.put(Link.is_pmleader, User.newInstance().getIs_pmleader());
            keyObj.put(Link.mem_job, User.newInstance().getMem_job());
            keyObj.put("sort", "att_date desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        mHttpc.post(Link.localhost + Link.punch_list, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("result")) {
                        JSONArray array = response.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            checks.add(new Check(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "checks: " + checks);
                        mRecyclerView = (RecyclerView) v.findViewById(R.id.check_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        mCardView = (CardView) v.findViewById(R.id.fragment_ls_check);
                        mAdapter = new CustomRecyclerAdapter<Check>(mFragment.getActivity(), checks, R.layout.ls_check_card_item) {
                            @Override
                            protected void display(ViewHolderHelper viewHolder, Check data) {
                                viewHolder.setText(R.id.ls_check_card_item_name, data.getMem_name())
                                        .setText(R.id.ls_check_card_item_location, data.getPuncher_name())
                                        .setText(R.id.ls_check_card_item_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(data.getAtt_date() + 28800));

                                if (data.getS_att_time() == 0)
                                    viewHolder.setText(R.id.ls_check_card_item_dutytime, "--");
                                else viewHolder.setText(R.id.ls_check_card_item_dutytime, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getS_att_time()+28800));

                                if (data.getE_att_time() == 0)
                                    viewHolder.setText(R.id.ls_check_card_item_offdutytime, "--");
                                else viewHolder.setText(R.id.ls_check_card_item_offdutytime, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getE_att_time()+28800));
                            }
                        };
                        mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Object data) {
                                Fragment fragment = MyCheckDetailFragment.newInstance(data);

                                if (null != saveInstanceState) {
                                    fragment.setArguments(new Bundle(saveInstanceState));
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
                        mRecyclerView.setAdapter(mAdapter);

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_check, parent, false);

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;

        loadDate(saveInstanceState, mCurl_page, v);

        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_check_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        checks.clear();
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
                        checks.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        mBack = (TextView) v.findViewById(R.id.main_fragment_my_check_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mTitle = (TextView) v.findViewById(R.id.main_fragment_my_check_title);
        mTitle.setText("考勤");

        mSearch = (TextView) v.findViewById(R.id.main_fragment_my_check_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new LeaderCheckSearchFragment();
                    if (null != saveInstanceState) {
                        fragment.setArguments(new Bundle(saveInstanceState));
                    }
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        return v;
    }
}
