package com.example.zxl.cloudmanager.travel.myTravel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelActivity;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelAddFragment;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelDetailFragment;
import com.example.zxl.cloudmanager.travel.leader.LeaderTravelSearchActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyTravelFragment extends Fragment {
    private ArrayList<Travel> travels = new ArrayList<Travel>();

    private CardView mCardView;
    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    private static final String TAG = "MyTravelFragment";

    private TextView mBack;
    private TextView mSearch;
    private TextView mTitle;
    private Button mAdd;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;
    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.mem_name))
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));
                if (-1 != saveInstanceState.getInt(Link.start_time_s))
                    keyObj.put(Link.start_time_s, saveInstanceState.getInt(Link.start_time_s));
                if (-1 != saveInstanceState.getInt(Link.start_time_e))
                    keyObj.put(Link.start_time_e, saveInstanceState.getInt(Link.start_time_e));
                if (-1 != saveInstanceState.getInt(Link.over_time_s))
                    keyObj.put(Link.over_time_s, saveInstanceState.getInt(Link.over_time_s));
                if (-1 != saveInstanceState.getInt(Link.over_time_e))
                    keyObj.put(Link.over_time_e, saveInstanceState.getInt(Link.over_time_e));
                if (-1 != saveInstanceState.getInt(Link.status))
                    keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put(Link.mem_job, User.newInstance().getMem_job());
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            keyObj.put(Link.is_puncher, User.newInstance().getIs_puncher());
            keyObj.put(Link.is_pmmaster, User.newInstance().getIs_pmmaster());
            keyObj.put(Link.is_pmleader, User.newInstance().getIs_pmleader());
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getInt("code") == 200) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                travels.add(new Travel(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView) v.findViewById(R.id.travel_recyclerView);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), travels);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView) v.findViewById(R.id.travel_card_item);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = new Fragment();
                                    if (mFragment.getActivity().getClass() == ManagerTravelActivity.class) {
                                        fragment = ManagerTravelDetailFragment.newInstance(data);
                                    } else if (mFragment.getActivity().getClass() == LeaderTravelSearchActivity.class) {
                                        fragment = MyTravelDetailFragment.newInstance(data);
                                    } else if (mFragment.getActivity().getClass() == MyTravelActivity.class) {
                                        fragment = MyTravelDetailFragment.newInstance(data);
                                    }
                                    fragment.setArguments(saveInstanceState);
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

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.my_travel_recyclerview, container, false);

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        mAdd = (Button) v.findViewById(R.id.travel_list_add);

        mBack = (TextView) v.findViewById(R.id.my_travel_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mTitle = (TextView) v.findViewById(R.id.leader_post_title);


        mSearch = (TextView) v.findViewById(R.id.my_travel_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyTravelSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        if (mFragment.getActivity().getClass() == ManagerTravelActivity.class) {
            url = Link.manage_trip + Link.get_list;
            mTitle.setText("出差");
        } else if (mFragment.getActivity().getClass() == LeaderTravelSearchActivity.class) {
            url = Link.trip_list + Link.get_list;
            mTitle.setText("出差");
            mAdd.setVisibility(View.GONE);
        } else if (mFragment.getActivity().getClass() == MyTravelActivity.class) {
            url = Link.my_trip + Link.get_list;
            mAdd.setVisibility(View.GONE);
        }

        loadDate(saveInstanceState, mCurl_page, v);

        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.travel_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        travels.clear();
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
                        travels.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerTravelAddFragment();
                fragment.setArguments(saveInstanceState);
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
        private ArrayList<Travel> travels;
        private Context mContext;

        public MyAdapter(Context context, ArrayList<Travel> travels) {
            this.travels = travels;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_fragment_travel_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Travel travel = travels.get(i);

            viewHolder.mName.setText(travel.getMem_name());
            viewHolder.mStatus.setText(travel.getStatus());
            viewHolder.mReason.setText(travel.getTrip_reason());
            viewHolder.mBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(travel.getStart_time()));
            viewHolder.mEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(travel.getEnd_time()));

            viewHolder.itemView.setTag(travels.get(i));
        }

        @Override
        public int getItemCount() {
            return travels == null ? 0 : travels.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mName;
            private TextView mStatus;
            private TextView mReason;
            private TextView mBeginTime;
            private TextView mEndTime;

            public ViewHolder(View v) {
                super(v);
                mName = (TextView) v.findViewById(R.id.my_travel_name);
                mStatus = (TextView) v.findViewById(R.id.my_travel_status);
                mReason = (TextView) v.findViewById(R.id.my_travel_reason);
                mBeginTime = (TextView) v.findViewById(R.id.my_travel_beginTime);
                mEndTime = (TextView) v.findViewById(R.id.my_travel_endTime);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}
