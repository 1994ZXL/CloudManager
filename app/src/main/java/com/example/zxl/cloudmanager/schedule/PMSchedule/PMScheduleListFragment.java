package com.example.zxl.cloudmanager.schedule.PMSchedule;

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
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Schedule;
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
 * Created by ZXL on 2016/9/5.
 */
public class PMScheduleListFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Schedule> schedules = new ArrayList<Schedule>();
    private MyAdapter myAdapter;

    public static final int REFRESH_DELAY = 4000;
    private static final String TAG = "MCListFragment";

    private Fragment mFragment;

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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.pm_schedule_list, parent, false);

        saveInstanceState = getArguments();
        if (null != saveInstanceState) {

            try {
                if (-1 != saveInstanceState.getInt(Link.report_time_from))
                    keyObj.put(Link.report_time_from, saveInstanceState.getInt(Link.report_time_from));
                if (-1 != saveInstanceState.getInt(Link.report_time_to))
                    keyObj.put(Link.report_time_to, saveInstanceState.getInt(Link.report_time_to));
                keyObj.put(Link.percent_from, saveInstanceState.getInt(Link.percent_from));
                keyObj.put(Link.percent_to, saveInstanceState.getInt(Link.percent_to));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put("sort", "pmsch_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", 1);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + Link.pm_schedule + Link.get_list, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                schedules.add(new Schedule(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "schedules: " + schedules);

                            mRecyclerView = (RecyclerView)v.findViewById(R.id.pm_schedule_list);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), schedules);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView)v.findViewById(R.id.pm_schedule_card_item);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = PMScheduleEditFragment.newInstance(data);
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
                        Log.e(TAG, "接收数据异常: " + e.getLocalizedMessage());
                    }
                } else {

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
        private List<Schedule> schedules;
        private Context mContext;

        public MyAdapter (Context context, List<Schedule> schedules) {
            this.schedules = schedules;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pm_schedule_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Schedule schedule = schedules.get(i);

            viewHolder.mTitle.setText(schedule.getTitle());
            viewHolder.mPmschTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(schedule.getPmsch_time()));
            viewHolder.mSubmitTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(schedule.getReport_time()));
            viewHolder.mPercent.setText(String.valueOf(schedule.getPercent()));

            viewHolder.itemView.setTag(schedules.get(i));
        }

        @Override
        public int getItemCount() {
            return schedules == null ? 0 : schedules.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTitle;
            public TextView mPmschTime;
            public TextView mSubmitTime;
            public TextView mPercent;

            public ViewHolder(View v) {
                super(v);
                mTitle = (TextView) v.findViewById(R.id.pm_schedule_card_item_title);
                mPmschTime = (TextView) v.findViewById(R.id.pm_schedule_card_item_pmsch_time);
                mSubmitTime = (TextView) v.findViewById(R.id.pm_schedule_card_item_submit_time);
                mPercent = (TextView) v.findViewById(R.id.pm_schedule_card_item_percent);

            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
