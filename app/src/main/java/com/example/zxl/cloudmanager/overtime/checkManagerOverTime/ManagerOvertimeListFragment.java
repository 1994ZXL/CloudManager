package com.example.zxl.cloudmanager.overtime.checkManagerOverTime;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.overtime.leader.LeaderOvertimeSearchActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

//考勤主管查询列表

public class ManagerOvertimeListFragment extends Fragment {

    private static final String TAG = "ManagerOvertimeList";
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<OverTime> overTimes = new ArrayList<OverTime>();
    private MyAdapter myAdapter;

    private Fragment mFragment;
    private Fragment fragment;
    private Button mSearchBtn;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private String key = "";
    private JSONObject keyObj = new JSONObject();

    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
       final View v = layoutInflater.inflate(R.layout.main_fragment_overtime, parent, false);



        saveInstanceState = getArguments();

        if (mFragment.getActivity().getClass() == LeaderOvertimeSearchActivity.class)
            url = Link.work_list + Link.get_list;
        else url = Link.manage_work + Link.get_list;

        mPullToRefreshView = (PullToRefreshView) v.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

        if (null == saveInstanceState) {
            Log.d(TAG, "没有选择条件");
        } else {
            try {
                if (-1 != saveInstanceState.getInt(Link.start_time)) {
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));
                }

                if (-1 != saveInstanceState.getInt(Link.end_time)) {
                    keyObj.put(Link.end_time, saveInstanceState.getInt(Link.end_time));
                }

                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));

                keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));

                keyObj.put(Link.work_pm, saveInstanceState.getString(Link.work_pm));

                keyObj.put("sort", "start_time desc");
                keyObj.put("page_count", 50);
                keyObj.put("curl_page", 1);
                keyObj.put(Link.mem_id, User.newInstance().getUser_id());
                keyObj.put(Link.is_pmmaster, User.newInstance().getIs_pmmaster());
                keyObj.put(Link.is_puncher, User.newInstance().getIs_puncher());

                key = DESCryptor.Encryptor(keyObj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mParams.put("key", key);
            Log.d(TAG, "key: " + key);
        }

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                overTimes.add(new OverTime(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "overtimes: " + overTimes);
                            mRecyclerView = (RecyclerView)v.findViewById(R.id.overtime_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), overTimes);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView) v.findViewById(R.id.fragment_my_overtime);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = ManagerOvertimeDetailFragment.newInstance(data);
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

                        } else {

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                        try {
                            Toast.makeText(getActivity(),
                                    rjo.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

        });


        return v;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<OverTime> overTimes;
        private Context mContext;

        public MyAdapter (Context context, List<OverTime> overTimes) {
            this.overTimes = overTimes;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.overtime_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            OverTime mOverTime = overTimes.get(i);

            viewHolder.mOvertimeDateBegin.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mOverTime.getStart_time()));
            viewHolder.mOvertimeDateEnd.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mOverTime.getEnd_time()));
            viewHolder.mOvertimeName.setText(mOverTime.getMem_name());
            viewHolder.mOvertimeProject.setText(mOverTime.getWork_pm());
            viewHolder.mOvertimeReason.setText(mOverTime.getWork_resaon());

            viewHolder.itemView.setTag(overTimes.get(i));
        }

        @Override
        public int getItemCount() {
            return overTimes == null ? 0 : overTimes.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mOvertimeName;
            public TextView mOvertimeDateBegin;
            public TextView mOvertimeDateEnd;
            public TextView mOvertimeProject;
            public TextView mOvertimeReason;

            public ViewHolder(View v) {
                super(v);
                mOvertimeProject = (TextView) v.findViewById(R.id.main_fragment_overtime_project);
                mOvertimeName = (TextView)v.findViewById(R.id.overtime_card_item_name);
                mOvertimeDateBegin = (TextView) v.findViewById(R.id.overtime_card_item_begin_time);
                mOvertimeDateEnd = (TextView) v.findViewById(R.id.overtime_card_item_end_time);
                mOvertimeReason = (TextView) v.findViewById(R.id.overtime_card_item_overtime_reason);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
