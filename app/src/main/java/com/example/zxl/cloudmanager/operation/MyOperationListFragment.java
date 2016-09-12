package com.example.zxl.cloudmanager.operation;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Operation;
import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.overtime.checkManagerOverTime.ManagerOvertimeDetailFragment;
import com.example.zxl.cloudmanager.overtime.myOvertime.MyOvertimeDetailFragment;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
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
 * A simple {@link Fragment} subclass.
 */
public class MyOperationListFragment extends Fragment {
    private static final String TAG = "MyOperationList";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<Operation> operations = new ArrayList<Operation>();
    private MyAdapter myAdapter;

    private Fragment mFragment;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private TextView mBack;
    private TextView mSearch;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null == saveInstanceState) {
            Log.d(TAG, "没有选择条件");
        } else {
            try {
                if (null != saveInstanceState.getString(Link.project_name))
                    keyObj.put(Link.project_name, saveInstanceState.getString(Link.project_name));

                keyObj.put(Link.user_id, User.newInstance().getUser_id());
                keyObj.put(Link.pm_id, saveInstanceState.getString(Link.pm_id));

                keyObj.put("sort", "goon_time desc");
                keyObj.put("page_count", 20);
                keyObj.put("curl_page", curl_page);

                key = DESCryptor.Encryptor(keyObj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mParams.put("key", key);
            Log.d(TAG, "key: " + key);
        }

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                operations.add(new Operation(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "operations: " + operations);
                            mRecyclerView = (RecyclerView)v.findViewById(R.id.my_operation_recycler);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), operations);
                            mRecyclerView.setAdapter(myAdapter);
                            mCardView = (CardView) v.findViewById(R.id.my_operation_card_item);
                            myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MyOperationDetailsFragment.newInstance(data);
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
                        try {
                            Toast.makeText(getActivity(),
                                    rjo.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.my_operation_list, container, false);

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        url = Link.my_operation + Link.get_list;
        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_operation_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        operations.clear();
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        operations.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });

        mBack = (TextView) v.findViewById(R.id.my_operation_list_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.my_operation_list_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyOperationSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
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
        private List<Operation> operations;
        private Context mContext;

        public MyAdapter (Context context, List<Operation> operations) {
            this.operations = operations;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_operation_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Operation mOperation = operations.get(i);

            viewHolder.mProjectName.setText(mOperation.getProject_name());
            viewHolder.mContactName.setText(mOperation.getContact_name());
            viewHolder.mStatus.setText(mOperation.getProject_state());
            viewHolder.mGoonTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mOperation.getGoon_time()));

            viewHolder.itemView.setTag(operations.get(i));
        }

        @Override
        public int getItemCount() {
            return operations == null ? 0 : operations.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView mProjectName;
            private TextView mContactName;
            private TextView mStatus;
            private TextView mGoonTime;

            public ViewHolder(View v) {
                super(v);
                mProjectName = (TextView) v.findViewById(R.id.my_operation_card_projectname);
                mContactName = (TextView) v.findViewById(R.id.my_operation_card_contact_name);
                mStatus = (TextView) v.findViewById(R.id.my_operation_card_status);
                mGoonTime = (TextView) v.findViewById(R.id.my_operation_card_goon_time);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
