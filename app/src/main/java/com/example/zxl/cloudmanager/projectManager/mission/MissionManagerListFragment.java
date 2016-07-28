package com.example.zxl.cloudmanager.projectManager.mission;

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

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.Mission;
import com.example.zxl.cloudmanager.model.MissionLab;
import com.example.zxl.cloudmanager.myMission.MyMissionSearchFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MissionManagerListFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Mission> missions = new ArrayList<Mission>();
    private MyAdapter myAdapter;

    private Fragment mFragment;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private static final String SEARCH_KEY = "search_key";
    private int searchKey;

    private Button mSearchBtn;

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
                    fragment = new MyMissionSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_my_mission, parent, false);

        getActivity().getActionBar().setTitle("我的任务");

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
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
        if (null == saveInstanceState) {
            searchKey = -1;
        } else {
            searchKey = getArguments().getInt(SEARCH_KEY);
        }

        if (-1 == searchKey) {
            missions = MissionLab.newInstance(mFragment.getActivity()).get();
        } else {
            missions.add(MissionLab.newInstance(mFragment.getActivity()).get().get(searchKey));
        }

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
                Fragment fragment = MissionManagerEditFragment.newInstance(data);
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
            viewHolder.mName.setText(mission.getName());
            viewHolder.mState.setText(mission.getState());
            viewHolder.mlevel.setText(mission.getLevel());

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
            public TextView mName;
            public TextView mState;
            public TextView mlevel;
            public TextView mBeginTime;
            public TextView mEndTime;

            public ViewHolder(View v) {
                super(v);
                mName = (TextView) v.findViewById(R.id.mission_card_item_name);
                mState = (TextView)v.findViewById(R.id.mission_card_item_state);
                mlevel = (TextView)v.findViewById(R.id.mission_card_item_level);
                mBeginTime = (TextView)v.findViewById(R.id.missoin_card_item_mission_begin_time);
                mEndTime = (TextView)v.findViewById(R.id.mission_card_item_mission_end_time);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}
