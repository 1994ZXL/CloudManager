package com.example.zxl.cloudmanager.leave.myLeave;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.LeaveMyLab;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
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
    private MyAdapter myAdapter;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;
    private Fragment mFragment;
    private static final String TAG = "MyLeaveQueryFragment";

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
    }

    @Override
    public void onPause() {
        super.onPause();
        leaves.clear();
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_leave_query, parent, false);

        getActivity().getActionBar().setTitle("我的请假");
        mPullToRefreshView = (PullToRefreshView) v.findViewById(R.id.my_leave_pull_to_refresh);
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

        saveInstanceState = getArguments();
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
            keyObj.put(Link.curl_page, 1);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG,"key:" + key);
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

                            mRecyclerView = (RecyclerView)v.findViewById(R.id.leave_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), leaves);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView)v.findViewById(R.id.fragment_my_leave);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyLeaveDetailFragment.newInstance(data);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    if (!fragment.isAdded()) {
//                                        transaction.addToBackStack(null);
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
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }

            }

        });

        return v;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<Leave> leaves;
        private Context mContext;

        public MyAdapter (Context context, List<Leave> leaves) {
            this.leaves = leaves;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leave_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Leave leave = leaves.get(i);

            viewHolder.mLeaveName.setText(leave.getMem_name());
            viewHolder.mLeaveType.setText(leave.getLeave_type());
            viewHolder.mLeaveState.setText(leave.getStatus());
            viewHolder.mLeaveBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(leave.getStart_time()));
            viewHolder.mLeaveEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(leave.getEnd_time()));

            viewHolder.itemView.setTag(leaves.get(i));
        }

        @Override
        public int getItemCount() {
            return leaves == null ? 0 : leaves.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView mLeaveName;
            public TextView mLeaveType;
            public TextView mLeaveState;
            public TextView mLeaveBeginTime;
            public TextView mLeaveEndTime;

            public ViewHolder(View v) {
                super(v);
                mLeaveBeginTime = (TextView) v.findViewById(R.id.leave_card_item_begin_time);
                mLeaveEndTime = (TextView) v.findViewById(R.id.leave_card_item_end_time);
                mLeaveName = (TextView) v.findViewById(R.id.leave_card_item_name);
                mLeaveType = (TextView) v.findViewById(R.id.leave_card_item_illness);
                mLeaveState = (TextView) v.findViewById(R.id.leave_card_item_state);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}
