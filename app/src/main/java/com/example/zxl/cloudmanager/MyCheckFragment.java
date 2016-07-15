package com.example.zxl.cloudmanager;

import android.app.Fragment;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.model.Check;

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
    private List<Check> checks = new ArrayList<Check>();
    private MyAdapter myAdapter;

    private Button mBtn;

    private static final String TAG = "MyCheckFragment";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    private String getTime1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    private String getTime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_check, parent, false);

        Log.d(TAG, "调用了一次");

        getActivity().getActionBar().setTitle("我的考勤");

        mBtn =(Button)v.findViewById(R.id.search_button) ;
        mBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment = new SearchCheckFragment();
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

        checks.add(new Check(getTime1(), "公司", getTime2(), getTime2()));

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

        return v;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<Check> checks;
        private Context mContext;

        public MyAdapter (Context context, List<Check> checks) {
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
