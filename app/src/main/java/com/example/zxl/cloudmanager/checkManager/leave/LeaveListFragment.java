package com.example.zxl.cloudmanager.checkManager.leave;

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
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.Leave;
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
public class LeaveListFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Leave> leaves = new ArrayList<Leave>();
    private MyAdapter myAdapter;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams;

    private static final String TAG = "LeaveListFragment";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public void onResume(){
        super.onResume();
//        new LoadLeaveByServerTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_manager_check_list, parent, false);

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

        getActivity().getActionBar().setTitle("请假处理");

//        leaves = LeaveQueryLab.newInstance(mFragment.getActivity()).getLeaveQuery();

        mHttpc.post("http://192.168.0.109/yunmgr_v1.0/api/uc.php?app=manage_leave&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            leaves.add(new Leave(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "leaves: " + leaves);

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
                                Fragment fragment = LeaveDeallFragment.newInstance(data);
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

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {

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

            viewHolder.mState.setText(leave.getStatus());
            viewHolder.mType.setText(leave.getLeave_type());
            viewHolder.mLeaveBegin.setText(leave.getStart_time());
            viewHolder.mLeaveEnd.setText(leave.getEnd_time());

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

            public ViewHolder(View v) {
                super(v);
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

//    private class LoadLeaveByServerTask extends AsyncTask<Void, Void, ArrayList<Leave>> {
//
//        @Override
//        protected ArrayList<Leave> doInBackground(Void... params) {
//            return new HttpClient().getJSONArray();
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Leave> leave){
//            LeaveQueryLab.newInstance(getActivity()).setLeaves(leave);
//            leaves = leave;
//            Log.d(TAG, "leave: " + leave);
//        }
//    }
}
