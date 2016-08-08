package com.example.zxl.cloudmanager.projectManager.PMAdressBook;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/12.
 */
public class ProjectManagerListFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Project> project = new ArrayList<Project>();
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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_manager_list, parent, false);

        getActivity().getActionBar().setTitle("项目管理");

//        saveInstanceState = getArguments();
//        if (null == saveInstanceState) {
//            searchKey = -1;
//        } else {
//            searchKey = getArguments().getInt(SEARCH_KEY);
//        }
//
//        if (-1 == searchKey) {
//            project = ProjectLab.newInstance(mFragment.getActivity()).getmProjects();
//        } else {
//            project.add(ProjectLab.newInstance(mFragment.getActivity()).getmProjects().get(searchKey));
//        }
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pm_manager_pull_to_refresh);
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

        mRecyclerView = (RecyclerView)view.findViewById(R.id.pm_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), project);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)view.findViewById(R.id.fragment_my_check);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = new ProjectManagerDetailFragment();
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

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<Project> projects;
        private Context mContext;

        public MyAdapter (Context context, List<Project> projects) {
            this.projects = projects;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pm_manager_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Project project = projects.get(i);

            viewHolder.mProjectName.setText(project.getProject_name());
            viewHolder.mContactName.setText(project.getContact_name());
            viewHolder.mPartA.setText(project.getPart_a());
            viewHolder.mState.setText(project.getStatus());
            viewHolder.mHeader.setText(project.getHeader());

            viewHolder.itemView.setTag(projects.get(i));
        }

        @Override
        public int getItemCount() {
            return projects == null ? 0 : projects.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mProjectName;
            public TextView mHeader;
            public TextView mContactName;
            public TextView mPartA;
            public TextView mState;

            public ViewHolder(View v) {
                super(v);
                mProjectName = (TextView) v.findViewById(R.id.pm_item_name);
                mState = (TextView)v.findViewById(R.id.pm_item_state);
                mContactName = (TextView)v.findViewById(R.id.pm_item_contactor);
                mHeader= (TextView)v.findViewById(R.id.pm_item_manager);
                mPartA = (TextView)v.findViewById(R.id.pm_item_company);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }


}
