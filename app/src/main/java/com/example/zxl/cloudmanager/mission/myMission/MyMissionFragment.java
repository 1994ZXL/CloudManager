package com.example.zxl.cloudmanager.mission.myMission;

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
import com.example.zxl.cloudmanager.model.Mission;
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
 * Created by ZXL on 2016/7/12.
 */
public class MyMissionFragment extends Fragment {
    private static final String TAG = "MyMissionFragment";
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Mission> missions = new ArrayList<Mission>();
    private CustomRecyclerAdapter<Mission> mAdapter;

    private TextView mSearch;
    private TextView mBack;
    private TextView mAdd;

    private Fragment mFragment;

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

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View view) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.title))
                    keyObj.put(Link.title, saveInstanceState.get(Link.title));

                if (-1 != saveInstanceState.getInt(Link.start_time_from))
                    keyObj.put(Link.start_time_from, saveInstanceState.getInt(Link.start_time_from));

                if (-1 != saveInstanceState.getInt(Link.start_time_to))
                    keyObj.put(Link.start_time_to, saveInstanceState.getInt(Link.start_time_to));

                if (-1 != saveInstanceState.getInt(Link.end_time_from))
                    keyObj.put(Link.end_time_from, saveInstanceState.getInt(Link.end_time_from));

                if (-1 != saveInstanceState.getInt(Link.end_time_to))
                    keyObj.put(Link.end_time_to, saveInstanceState.getInt(Link.end_time_to));

                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));

                key = DESCryptor.Encryptor(keyObj.toString());
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

        mHttpc.post(Link.localhost + "my_task&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                missions.add(new Mission(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "missions: " + missions);
                            mRecyclerView = (RecyclerView) view.findViewById(R.id.mission_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView) view.findViewById(R.id.fragment_my_check);
                            mAdapter = new CustomRecyclerAdapter<Mission>(mFragment.getActivity(), missions, R.layout.mission_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, Mission data) {
                                    viewHolder.setText(R.id.mission_card_item_title, data.getTitle())
                                            .setText(R.id.mission_card_item_state, data.getStatus())
                                            .setImageButtonGone(R.id.mission_card_item_delete);

                                    if (data.getStart_time() == 0)
                                        viewHolder.setText(R.id.missoin_card_item_mission_begin_time, "--");
                                    else viewHolder.setText(R.id.missoin_card_item_mission_begin_time, DateForGeLingWeiZhi.fromGeLinWeiZhi(data.getStart_time()));

                                    if (data.getEnd_time() == 0)
                                        viewHolder.setText(R.id.mission_card_item_mission_end_time, "--");
                                    else viewHolder.setText(R.id.mission_card_item_mission_end_time, DateForGeLingWeiZhi.fromGeLinWeiZhi(data.getEnd_time()));
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyMissionDetailFragment.newInstance(data);
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View view = layoutInflater.inflate(R.layout.main_fragment_my_mission, parent, false);

        mSearch = (TextView) view.findViewById(R.id.manager_mission_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyMissionSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        mBack = (TextView) view.findViewById(R.id.my_mission_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mAdd = (TextView) view.findViewById(R.id.manager_mission_add);
        mAdd.setVisibility(View.GONE);

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;

        loadDate(saveInstanceState, mCurl_page, view);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.my_mission_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        missions.clear();
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
                        missions.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, view);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        return view;
    }
}
