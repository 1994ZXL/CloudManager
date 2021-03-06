package com.example.zxl.cloudmanager.mission.projectManagerMission;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.mission.publicSearchMission.PublicSearchMissionActivity;
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
public class MissionManagerListFragment extends Fragment {

    private static final String TAG = "MissionManagerList";
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Mission> missions = new ArrayList<Mission>();
    private CustomRecyclerAdapter<Mission> mAdapter;

    private TextView mAddTextView;
    private TextView mTitle;
    private TextView mSearch;
    private TextView mBack;

    private Fragment mFragment;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcDelete = new AsyncHttpClient();
    private RequestParams mParamsDelete = new RequestParams();
    private JSONObject keyObjDelete = new JSONObject();
    private String keyDelete = "";
    private String url;

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
                    keyObj.put(Link.title, saveInstanceState.getString(Link.title));
                if (null != saveInstanceState.getString(Link.mem_name))
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));
                if (-1 != saveInstanceState.getInt(Link.start_time_from))
                    keyObj.put(Link.start_time_from, saveInstanceState.getInt(Link.start_time_from));
                if (-1 != saveInstanceState.getInt(Link.start_time_to))
                    keyObj.put(Link.start_time_to, saveInstanceState.getInt(Link.start_time_to));
                if (-1 != saveInstanceState.getInt(Link.end_time_from))
                    keyObj.put(Link.end_time_from, saveInstanceState.getInt(Link.end_time_from));
                if (-1 != saveInstanceState.getInt(Link.end_time_to))
                    keyObj.put(Link.end_time_to, saveInstanceState.getInt(Link.end_time_to));
                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
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
                                protected void display(ViewHolderHelper viewHolder, final Mission data) {
                                    if (data.getStart_time() == 0)
                                        viewHolder.setText(R.id.missoin_card_item_mission_begin_time, "--");
                                    else viewHolder.setText(R.id.missoin_card_item_mission_begin_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getStart_time()));

                                    if (data.getEnd_time() == 0)
                                        viewHolder.setText(R.id.mission_card_item_mission_end_time, "--");
                                    else viewHolder.setText(R.id.mission_card_item_mission_end_time, DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(data.getEnd_time()));

                                    viewHolder.setText(R.id.mission_card_item_title, data.getTitle())
                                            .setText(R.id.mission_card_item_state, data.getStatus())
                                            .setImageButton(R.id.mission_card_item_delete, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    final int i = missions.indexOf(data);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
                                                    builder.setTitle("提示");
                                                    builder.setMessage("是否要删除");
                                                    builder.setNegativeButton("取消", null);
                                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            try {
                                                                keyObjDelete.put(Link.pmtask_id, missions.get(i).getPmtask_id());
                                                                keyDelete = DESCryptor.Encryptor(keyObjDelete.toString());
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            mParamsDelete.put("key", keyDelete);
                                                            Log.d(TAG, "key:" + keyDelete);

                                                            mHttpcDelete.post(Link.localhost + "pm_task&act=drop", mParamsDelete, new JsonHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                                    try {
                                                                        Toast.makeText(getActivity(),
                                                                                response.getString("msg"),
                                                                                Toast.LENGTH_SHORT).show();
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                                    Toast.makeText(getActivity(),
                                                                            R.string.edit_error,
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            missions.remove(i);
                                                            mRecyclerView.scrollToPosition(i - 1);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                    });
                                                    builder.show();
                                                }
                                            });
                                }
                            };
                            mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MissionManagerEditFragment.newInstance(data);
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
                        try {
                            Toast.makeText(mFragment.getActivity(),
                                    rjo.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View view = layoutInflater.inflate(R.layout.main_fragment_my_mission, parent, false);

        mAddTextView = (TextView) view.findViewById(R.id.manager_mission_add);

        mBack = (TextView) view.findViewById(R.id.my_mission_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mTitle = (TextView) view.findViewById(R.id.my_mission_title);
        mTitle.setText("任务管理");

        mSearch = (TextView) view.findViewById(R.id.manager_mission_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MissionSearchFragment();
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

        if (mFragment.getActivity().getClass() == PublicSearchMissionActivity.class) {
            url = Link.pm_task_list + Link.get_list;
            mAddTextView.setVisibility(View.GONE);
            mTitle.setText("任务列表");
        } else if (mFragment.getActivity().getClass() == ProjectMissionManagerActivity.class) {
            url = Link.pm_task + Link.get_list;
        }

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

        mAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MissionManagerAddFragment();
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
        return view;
    }
}
