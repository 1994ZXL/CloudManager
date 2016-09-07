package com.example.zxl.cloudmanager.leave.checkManagerLeave;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.leave.leader.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveDetailFragment;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveSearchFragment;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
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
    private MyAdapter myAdapter;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private TextView mBack;
    private TextView mSearch;

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
    }

    @Override
    public void onPause() {
        super.onPause();
        leaves.clear();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_manager_check_list, parent, false);
        saveInstanceState = getArguments();

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
                    fragment = new MyLeaveSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

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
        Log.d(TAG, "调用了一次");



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
            keyObj.put("page_count", 50);
            keyObj.put("curl_page", 1);

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
        mHttpc.post(url , mParams, new JsonHttpResponseHandler() {
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
                            myAdapter = new MyAdapter(mFragment.getActivity(), leaves);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView) v.findViewById(R.id.fragment_my_check);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    if (mFragment.getActivity().getClass() == LeaderLeaveSearchActivity.class) {
                                        mAimFragment = MyLeaveDetailFragment.newInstance(data);
                                    } else if (mFragment.getActivity().getClass() == ManagerLeaveActivity.class) {
                                        mAimFragment = ManagerLeaveDealFragment.newInstance(data);
                                    }
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

                        } else {

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                } else {

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
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cm_leave_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Leave leave = leaves.get(i);

            viewHolder.mName.setText(leave.getMem_name());
            viewHolder.mState.setText(leave.getStatus());
            viewHolder.mType.setText(leave.getLeave_type());
            viewHolder.mLeaveBegin.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(leave.getStart_time()));
            viewHolder.mLeaveEnd.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(leave.getEnd_time()));

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
            public TextView mType;
            public TextView mLeaveBegin;
            public TextView mLeaveEnd;
            public TextView mState;
            public TextView mName;

            public ViewHolder(View v) {
                super(v);
                mName = (TextView) v.findViewById(R.id.cm_leave_card_item_name);
                mState = (TextView)v.findViewById(R.id.cm_leave_card_item_state);
                mLeaveBegin = (TextView)v.findViewById(R.id.cm_leave_card_item_begin_time);
                mLeaveEnd = (TextView)v.findViewById(R.id.cm_leave_card_item_end_time);
                mType = (TextView) v.findViewById(R.id.cm_leave_card_item_type);

            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
