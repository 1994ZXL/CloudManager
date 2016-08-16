package com.example.zxl.cloudmanager.overtime.myOvertime;

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
import android.widget.Toast;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.model.User;
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
 * Created by ZXL on 2016/7/13.
 */
public class MyOverTimeFragment extends Fragment {
    private static final String MY = null;
    private static final String TAG = "MyOverTimeFragment";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<OverTime> overTimes = new ArrayList<OverTime>();
    private MyAdapter myAdapter;

    private Fragment mFragment;
    private Button searchBtn;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

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
                    fragment = new MyOverTimeSearch();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_overtime, parent, false);

        getActivity().getActionBar().setTitle("我的加班");

        saveInstanceState = getArguments();
        if (null != saveInstanceState) {

            try {
                keyObj.put(Link.work_pm, saveInstanceState.getString(Link.work_pm));

                if (-1 != saveInstanceState.getInt(Link.start_time)) {
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));
                }

                if (-1 != saveInstanceState.getInt(Link.end_time)) {
                    keyObj.put(Link.end_time, saveInstanceState.getInt(Link.end_time));
                }

                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
//            if (null == saveInstanceState) {
//                keyObj.put(Link.mem_id, User.newInstance().getUser_id());
//                url = Link.my_punch;
//            } else {
//                url = Link.punch_list;
//            }
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "my_work&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getInt("code") != 200){
                            Toast.makeText(getActivity(),
                                    response.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                    return;
                }
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                overTimes.add(new OverTime(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "overTimes: " + overTimes);
                            mRecyclerView = (RecyclerView)v.findViewById(R.id.overtime_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), overTimes);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView)v.findViewById(R.id.fragment_my_overtime);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyOvertimeDetailFragment.newInstance(data);
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                return;
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
            viewHolder.mOvertimeProject.setText(overTime.getWork_name());
            viewHolder.mBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(overTime.getStart_time()));
            viewHolder.mEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(overTime.getEnd_time()));

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
