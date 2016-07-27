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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyCheckFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Check> checks = new ArrayList<Check>();
    private MyAdapter myAdapter;

    private Button mSearchBtn;

    private static final String TAG = "MyCheckFragment";
    private static final String SEARCH_KEY = "search_key";
    private int searchKey;

    private Fragment mFragment;

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
                    fragment = new SearchCheckFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_check, parent, false);


        getActivity().getActionBar().setTitle("我的考勤");

        saveInstanceState = getArguments();
        if (null == saveInstanceState) {
            searchKey = 0;
        } else {
            searchKey = getArguments().getInt(SEARCH_KEY);
        }

        if (1 == searchKey) {
            checks.add(CheckLab.newInstance(mFragment.getActivity()).get().get(0));
        } else if (2 == searchKey) {
            checks.add(CheckLab.newInstance(mFragment.getActivity()).get().get(1));
        } else if (0 == searchKey){
            checks = CheckLab.newInstance(mFragment.getActivity()).get();
        }

        mRecyclerView = (RecyclerView)v.findViewById(R.id.check_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), checks);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_check);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = MyCheckDetailFragment.newInstance(data);
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
        private ArrayList<Check> checks;
        private Context mContext;

        public MyAdapter (Context context, ArrayList<Check> checks) {
            this.checks = checks;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.check_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Check check = checks.get(i);
            viewHolder.mDate.setText(check.getDate());
            viewHolder.mCheckLocation.setText(check.getCheckLocation());
            viewHolder.mDutyTime.setText(check.getDutyTime());
            viewHolder.mOffDutyTime.setText(check.getOffDutyTime());
            viewHolder.itemView.setTag(checks.get(i));
        }

        @Override
        public int getItemCount() {
            return checks == null ? 0 : checks.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mDate;
            public TextView mCheckLocation;
            public TextView mDutyTime;
            public TextView mOffDutyTime;
            public ViewHolder(View v) {
                super(v);
                mDate = (TextView)v.findViewById(R.id.check_card_item_time);
                mCheckLocation = (TextView)v.findViewById(R.id.check_card_item_location);
                mDutyTime = (TextView)v.findViewById(R.id.check_card_item_dutytime);
                mOffDutyTime = (TextView)v.findViewById(R.id.check_card_item_offdutytime);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
