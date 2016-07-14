package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.zxl.cloudmanager.model.OverTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyOverTimeFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<OverTime> overTimes = new ArrayList<OverTime>();
    private MyAdapter myAdapter;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_overtime, parent, false);


        getActivity().getActionBar().setTitle("我的加班");

        overTimes.add(new OverTime());

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
                Fragment fragment = new MyUseCaseDetailFragment();
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
            OverTime OverTime = overTimes.get(i);

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
            public TextView mOvertimeDate;
            public TextView mOvertimeProject;

            public ViewHolder(View v) {
                super(v);
                mOvertimeName = (TextView)v.findViewById(R.id.main_fragment_overtime_name);
                mOvertimeDate = (TextView)v.findViewById(R.id.overtime_card_item_overtime_time);
                mOvertimeProject = (TextView)v.findViewById(R.id.overtime_card_item_overtime_project);

            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
