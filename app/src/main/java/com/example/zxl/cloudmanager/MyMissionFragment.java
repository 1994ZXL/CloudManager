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

import com.example.zxl.cloudmanager.model.Mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyMissionFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Mission> missions = new ArrayList<Mission>();
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
        View view = layoutInflater.inflate(R.layout.main_fragment_my_mission, parent, false);

        getActivity().getActionBar().setTitle("我的任务");

        missions.add(new Mission(getTime(), getTime()));

        mRecyclerView = (RecyclerView)view.findViewById(R.id.mission_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), missions);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)view.findViewById(R.id.fragment_my_check);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = new MyCheckDetailFragment();
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

    private Date getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String date = formatter.format(curDate);
        return curDate;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<Mission> missions;
        private Context mContext;

        public MyAdapter (Context context, List<Mission> missions) {
            this.missions = missions;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mission_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Mission mission = missions.get(i);
            viewHolder.mBeginTime.setText(mission.getMissionBeginTime().toString());
            viewHolder.mEndTime.setText(mission.getMissionEndTime().toString());
            viewHolder.itemView.setTag(missions.get(i));
        }

        @Override
        public int getItemCount() {
            return missions == null ? 0 : missions.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mBeginTime;
            public TextView mEndTime;

            public ViewHolder(View v) {
                super(v);
                mBeginTime = (TextView)v.findViewById(R.id.missoin_card_item_mission_begin_time);
                mEndTime = (TextView)v.findViewById(R.id.mission_card_item_mission_end_time);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}