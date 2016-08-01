package com.example.zxl.cloudmanager;

import android.app.Activity;
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


import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.leave.LeaveSearchActivity;
import com.example.zxl.cloudmanager.leave.LeaveSearchFragment;
import com.example.zxl.cloudmanager.leave.MyLeaveDetailFragment;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.LeaveMyLab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveQueryFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Leave> leaves = new ArrayList<Leave>();
    private MyAdapter myAdapter;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;
    private Fragment mFragment;
    private static final String TAG = "MyLeaveQueryFragment";

    private int type;
    private int index;
    private ArrayList<Integer> key = new ArrayList<Integer>();

    private Button mSearchBtn;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;

        Intent intent = this.getActivity().getIntent();
        if (null == intent.getIntegerArrayListExtra("TYPE")){
            leaves = LeaveMyLab.newInstance(mFragment.getActivity()).get();
        } else {
            key =  intent.getIntegerArrayListExtra("TYPE");
            for (int i = 0; i < key.size(); i++) {
                leaves.add(LeaveMyLab.newInstance(mFragment.getActivity()).get().get(key.get(i)));
            }
        }
    }



    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_leave_query, parent, false);

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
        mRecyclerView = (RecyclerView)v.findViewById(R.id.leave_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), leaves);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_leave);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = MyLeaveDetailFragment.newInstance(data);
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

            viewHolder.mLeaveName.setText(leave.getMen_name());
            viewHolder.mLeaveType.setText(leave.getLeave_type());
            viewHolder.mLeaveState.setText(leave.getStatus());
            viewHolder.mLeaveBeginTime.setText(leave.getStart_time());
            viewHolder.mLeaveEndTime.setText(leave.getEnd_time());

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
