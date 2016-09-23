package com.example.zxl.cloudmanager.check.myCheck;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyCheckFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Check> checks = new ArrayList<Check>();
    private MyAdapter myAdapter;

    private Button mSearchBtn;
    private TextView mBack;
    private TextView mTitle;
    private TextView mSearch;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static final String TAG = "MyCheckFragment";

    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String url;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 0;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (-1 != saveInstanceState.getInt(Link.att_date_from))
                    keyObj.put(Link.att_date_from, saveInstanceState.getInt(Link.att_date_from));
                if (-1 != saveInstanceState.getInt(Link.att_date_to))
                    keyObj.put(Link.att_date_to, saveInstanceState.getInt(Link.att_date_to));
                if (-1 != saveInstanceState.getInt(Link.att_date_start))
                    keyObj.put(Link.att_date_start, saveInstanceState.getInt(Link.att_date_start));
                if (-1 != saveInstanceState.getInt(Link.att_date_end))
                    keyObj.put(Link.att_date_end, saveInstanceState.getInt(Link.att_date_end));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put("sort", "att_date desc");
            keyObj.put("page_count", 7);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + Link.my_punch, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("result")) {
                        JSONArray array = response.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            checks.add(new Check(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "checks: " + checks);
                        mRecyclerView = (RecyclerView) v.findViewById(R.id.check_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        myAdapter = new MyAdapter(mFragment.getActivity(), checks);
                        mRecyclerView.setAdapter(myAdapter);
                        mCardView = (CardView) v.findViewById(R.id.fragment_my_check);
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

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceStates) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_check, parent, false);

        mBack = (TextView) v.findViewById(R.id.main_fragment_my_check_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.main_fragment_my_check_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new SearchCheckFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        saveInstanceStates = getArguments();
        final Bundle saveInstanceState = saveInstanceStates;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_check_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        checks.clear();
                        mCurl_page = 0;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mCurl_page = mCurl_page + 7;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        return v;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
        private ArrayList<Check> checks;
        private Context mContext;

        public MyAdapter(Context context, ArrayList<Check> checks) {
            this.checks = checks;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.check_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Check check = checks.get(i);
            viewHolder.mCheckLocation.setText(check.getPuncher_name());
            viewHolder.mDate.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(check.getAtt_date() + 28800));
            if (check.getS_att_time() != 0) {
                viewHolder.mDutyTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(check.getS_att_time()+28800));
            } else {
                viewHolder.mDutyTime.setText("--");
            }
            if (check.getE_att_time() != 0) {
                viewHolder.mOffDutyTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(check.getE_att_time()+28800));
            } else {
                viewHolder.mOffDutyTime.setText("--");
            }
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

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mDate;
            public TextView mCheckLocation;
            public TextView mDutyTime;
            public TextView mOffDutyTime;

            public ViewHolder(View v) {
                super(v);
                mDate = (TextView) v.findViewById(R.id.ls_check_card_item_time);
                mCheckLocation = (TextView) v.findViewById(R.id.ls_check_card_item_location);
                mDutyTime = (TextView) v.findViewById(R.id.ls_check_card_item_dutytime);
                mOffDutyTime = (TextView) v.findViewById(R.id.ls_check_card_item_offdutytime);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
