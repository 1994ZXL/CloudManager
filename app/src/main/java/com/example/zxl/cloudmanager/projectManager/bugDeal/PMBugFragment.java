package com.example.zxl.cloudmanager.projectManager.bugDeal;

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

import com.example.zxl.cloudmanager.MyBugDetailFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.BugLab;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/13.
 */
public class PMBugFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Bug> bugs = new ArrayList<Bug>();
    private MyAdapter myAdapter;

    private Fragment mFragment;
    private Button mSearchBtn;

    private static final String SEARCH_KEY = "search_key";
    private static final String TAG = "MyBugFragment";
    private int searchKey;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

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
                    fragment = new BugSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_bug, parent, false);


        getActivity().getActionBar().setTitle("我的bug");

        saveInstanceState = getArguments();
        if (null == saveInstanceState) {
            searchKey = -1;
        } else {
            searchKey = getArguments().getInt(SEARCH_KEY);
        }

        if (-1 == searchKey) {
            bugs = BugLab.newInstance(mFragment.getActivity()).get();
        } else {
            bugs.add(BugLab.newInstance(mFragment.getActivity()).get().get(searchKey));
        }

        mPullToRefreshView = (PullToRefreshView) v.findViewById(R.id.my_bug_pull_to_refresh);
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
        mRecyclerView = (RecyclerView)v.findViewById(R.id.bug_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), bugs);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_bug);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = MyBugDetailFragment.newInstance(data);
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
        private List<Bug> bugs;
        private Context mContext;

        public MyAdapter (Context context, List<Bug> bugs) {
            this.bugs = bugs;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bug_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Bug bug = bugs.get(i);

            viewHolder.mFunctionModuel.setText(bug.getFunctionModel());
            viewHolder.mBugVersion.setText(bug.getBugVersion());
            viewHolder.mBugState.setText(bug.getStatus());
            viewHolder.mUseCaseNumber.setText(bug.getUseCaseNumber());
            viewHolder.mFoundTime.setText(bug.getSubmit_time());

            viewHolder.itemView.setTag(bugs.get(i));
        }

        @Override
        public int getItemCount() {
            return bugs == null ? 0 : bugs.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mFunctionModuel;
            public TextView mBugVersion;
            public TextView mBugState;
            public TextView mUseCaseNumber;
            public TextView mFoundTime;

            public ViewHolder(View v) {
                super(v);
                mFunctionModuel = (TextView)v.findViewById(R.id.main_fragment_my_bug_functionModuel);
                mBugVersion = (TextView)v.findViewById(R.id.bug_card_item_bug_version);
                mBugState = (TextView)v.findViewById(R.id.bug_card_item_bug_state);
                mUseCaseNumber = (TextView)v.findViewById(R.id.bug_card_item_usecase_number);
                mFoundTime = (TextView)v.findViewById(R.id.bug_card_item_found_time);
                
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}
