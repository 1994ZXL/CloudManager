package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.model.OverTimeLab;
import com.example.zxl.cloudmanager.myOvertime.MyOvertimeDetailFragment;
import com.example.zxl.cloudmanager.myOvertime.OverTimeFragment;
import com.example.zxl.cloudmanager.myPost.MyPostSearchFragment;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyOverTimeFragment extends Fragment {
    private static final String MY = null;

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<OverTime> overTimes = new ArrayList<OverTime>();
    private MyAdapter myAdapter;

    private Fragment mFragment;
    private Button searchBtn;
    private Fragment fragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new OverTimeFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_overtime, parent, false);

        getActivity().getActionBar().setTitle("我的加班");

        overTimes = OverTimeLab.newInstance(mFragment.getActivity()).get();

        mRecyclerView = (RecyclerView)v.findViewById(R.id.overtime_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), overTimes);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_overtime);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                fragment = MyOvertimeDetailFragment.newInstance(data);
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
            OverTime overTime = overTimes.get(i);

            viewHolder.mOvertimeName.setText(overTime.getMem_id());
            viewHolder.mOvertimeProject.setText(overTime.getPm_id());
            viewHolder.mBeginTime.setText(overTime.getStart_time());
            viewHolder.mEndTime.setText(overTime.getEnd_time());

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
            public TextView mBeginTime;
            public TextView mEndTime;
            public TextView mOvertimeProject;

            public ViewHolder(View v) {
                super(v);
                mOvertimeName = (TextView)v.findViewById(R.id.main_fragment_overtime_name);
                mBeginTime = (TextView) v.findViewById(R.id.overtime_card_item_begin_time);
                mEndTime = (TextView)v.findViewById(R.id.overtime_card_item_end_time);
                mOvertimeProject = (TextView) v.findViewById(R.id.overtime_card_item_overtime_project);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
