package com.example.zxl.cloudmanager.bug.myBug;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.bug.publicSearchBug.BugSearchFragment;
import com.example.zxl.cloudmanager.model.Link;
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
public class MyBugFragment extends Fragment {
    private static final String TAG = "MyBugFragment";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Bug> bugs = new ArrayList<Bug>();
    private MyAdapter myAdapter;

    private Button mAddTextView;
    private TextView mBack;
    private TextView mSearch;

    private Fragment mFragment;

    public static final int REFRESH_DELAY = 4000;

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
    public void onPause() {
        super.onPause();
        bugs.clear();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_bug, parent, false);

        mAddTextView = (Button) v.findViewById(R.id.my_bug_add);
        mAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyBugAddFragment();
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

        mBack = (TextView) v.findViewById(R.id.my_bug_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.my_bug_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new BugSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        saveInstanceState = getArguments();
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.project_name))
                    keyObj.put(Link.project_name, saveInstanceState.getInt(Link.project_name));
                if (-1 != saveInstanceState.getInt(Link.submit_time_from))
                    keyObj.put(Link.submit_time_from, saveInstanceState.getInt(Link.submit_time_from));
                if (-1 != saveInstanceState.getInt(Link.submit_time_to))
                    keyObj.put(Link.submit_time_to, saveInstanceState.getInt(Link.submit_time_to));
                if (null != saveInstanceState.getString(Link.mofify_time_from))
                    keyObj.put(Link.mofify_time_from, saveInstanceState.getInt(Link.mofify_time_from));
                if (null != saveInstanceState.getString(Link.modify_time_to))
                    keyObj.put(Link.modify_time_to, saveInstanceState.getInt(Link.modify_time_to));
                if (null != saveInstanceState.getString(Link.submitter))
                    keyObj.put(Link.submitter, saveInstanceState.getInt(Link.submitter));
                if (null != saveInstanceState.getString(Link.modifier))
                    keyObj.put(Link.modifier, saveInstanceState.getInt(Link.modifier));
                keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
                keyObj.put(Link.level, saveInstanceState.getInt(Link.level));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());

            keyObj.put("sort", "modify_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", 1);

            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "my_bug&act=get_list" , mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                bugs.add(new Bug(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView)v.findViewById(R.id.bug_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new MyAdapter(mFragment.getActivity(), bugs);
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

                        } else {

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
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

            viewHolder.mProjectName.setText(bug.getProject_name());
            viewHolder.mBugVersion.setText(bug.getLevel());
            viewHolder.mBugState.setText(bug.getStatus());
            if (bug.getSubmit_time() != 0)
                viewHolder.mFoundTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(bug.getSubmit_time()));
            if (bug.getModify_time() != 0)
                viewHolder.mModifyTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(bug.getModify_time()));

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
            public TextView mProjectName;
            public TextView mBugVersion;
            public TextView mBugState;
            public TextView mFoundTime;
            public TextView mModifyTime;

            public ViewHolder(View v) {
                super(v);
                mProjectName = (TextView)v.findViewById(R.id.main_fragment_my_bug_projectName);
                mBugVersion = (TextView)v.findViewById(R.id.bug_card_item_bug_version);
                mBugState = (TextView)v.findViewById(R.id.bug_card_item_bug_state);
                mFoundTime = (TextView)v.findViewById(R.id.bug_card_item_found_time);
                mModifyTime = (TextView)v.findViewById(R.id.bug_card_item_edit_time);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }

}
