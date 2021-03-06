package com.example.zxl.cloudmanager.schedule.PMSchedule;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Schedule;
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
 * Created by ZXL on 2016/9/5.
 */
public class PMScheduleListFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Schedule> schedules = new ArrayList<Schedule>();
    private CustomRecyclerAdapter<Schedule> mAdapter;

    private TextView mBack;
    private Button mAdd;
    private TextView mSearch;

    private static final String TAG = "MCListFragment";

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private Fragment mFragment;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcDelete = new AsyncHttpClient();
    private RequestParams mParamsDelete = new RequestParams();
    private JSONObject keyObjDelete = new JSONObject();
    private String keyDelete = "";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page,final View view) {
        if (null != saveInstanceState) {

            try {
                if (-1 != saveInstanceState.getInt(Link.report_time_from))
                    keyObj.put(Link.report_time_from, saveInstanceState.getInt(Link.report_time_from));
                if (-1 != saveInstanceState.getInt(Link.report_time_to))
                    keyObj.put(Link.report_time_to, saveInstanceState.getInt(Link.report_time_to));
                keyObj.put(Link.percent_from, saveInstanceState.getString(Link.percent_from));
                keyObj.put(Link.percent_to, saveInstanceState.getString(Link.percent_to));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put("sort", "pmsch_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + Link.pm_schedule + Link.get_list, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                schedules.add(new Schedule(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "schedules: " + schedules);

                            mRecyclerView = (RecyclerView)view.findViewById(R.id.pm_schedule_list);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView)view.findViewById(R.id.pm_schedule_card_item);
                            mAdapter = new CustomRecyclerAdapter<Schedule>(mFragment.getActivity(), schedules, R.layout.pm_schedule_card_item) {
                                @Override
                                protected void display(ViewHolderHelper viewHolder, final Schedule data) {
                                    if (data.getPmsch_time() == 0)
                                        viewHolder.setText(R.id.pm_schedule_card_item_pmsch_time, "--");
                                    else viewHolder.setText(R.id.pm_schedule_card_item_pmsch_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getPmsch_time()));

                                    if (data.getReport_time() == 0)
                                        viewHolder.setText(R.id.pm_schedule_card_item_submit_time, "--");
                                    else viewHolder.setText(R.id.pm_schedule_card_item_submit_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(data.getReport_time()));

                                    viewHolder.setText(R.id.pm_schedule_card_item_title, data.getTitle())
                                            .setText(R.id.pm_schedule_card_item_percent, String.valueOf(data.getPercent()))
                                            .setImageButton(R.id.pm_schedule_card_item_delete, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    final int i = schedules.indexOf(data);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
                                                    builder.setTitle("提示");
                                                    builder.setMessage("是否要删除");
                                                    builder.setNegativeButton("取消", null);
                                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                                        @Override
                                                        public void onClick(DialogInterface dialog,int which){
                                                            try {
                                                                keyObjDelete.put(Link.pmsch_id, schedules.get(i).getPmsch_id());
                                                                keyDelete = DESCryptor.Encryptor(keyObjDelete.toString());
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            mParamsDelete.put("key", keyDelete);
                                                            Log.d(TAG,"key:" + keyDelete);

                                                            mHttpcDelete.post(Link.localhost + "pm_schedule&act=drop", mParamsDelete, new JsonHttpResponseHandler() {
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
                                                            schedules.remove(i);
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
                                    Fragment fragment = PMScheduleEditFragment.newInstance(data);
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
                        Log.e(TAG, "接收数据异常: " + e.getLocalizedMessage());
                    }
                } else {

                }
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.pm_schedule_list, parent, false);

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.pm_schedule_list_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        schedules.clear();
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
                        schedules.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });

        mBack = (TextView) v.findViewById(R.id.pm_schedule_list_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.pm_schedule_list_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMScheduleSearchFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        mAdd = (Button) v.findViewById(R.id.pm_schedule_list_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMScheduleAddFrament();
                fragment.setArguments(saveInstanceState);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        return v;
    }
}
