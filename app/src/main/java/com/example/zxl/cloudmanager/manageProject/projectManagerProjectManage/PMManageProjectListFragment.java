package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;

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

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Project;
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
 * Created by ZXL on 2016/7/12.
 */
public class PMManageProjectListFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Project> project = new ArrayList<Project>();
    private MyAdapter myAdapter;

    private Fragment mFragment;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static final String TAG = "PMListFragment";

    private Button mSearchBtn;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View view = layoutInflater.inflate(R.layout.pm_manager_list, parent, false);

        saveInstanceState = getArguments();
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.project_name)) {
                    keyObj.put(Link.project_name, saveInstanceState.getString(Link.project_name));
                }
                if (-1 != saveInstanceState.getInt(Link.ready_time)) {
                    keyObj.put(Link.ready_time, saveInstanceState.getInt(Link.ready_time));
                }
                if (-1 != saveInstanceState.getInt(Link.finished_time)) {
                    keyObj.put(Link.finished_time, saveInstanceState.getInt(Link.finished_time));
                }
                if (null != saveInstanceState.getString(Link.header)) {
                    keyObj.put(Link.header, saveInstanceState.getString(Link.header));
                }
                keyObj.put("sort", "ready_time desc");
                keyObj.put("page_count", 50);
                keyObj.put("curl_page", 1);

                key = DESCryptor.Encryptor(keyObj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "manage_pm&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            project.add(new Project(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "mPosts: " + project);
                        mRecyclerView = (RecyclerView)view.findViewById(R.id.pm_list_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        myAdapter = new MyAdapter(mFragment.getActivity(), project);
                        mRecyclerView.setAdapter(myAdapter);
                        mCardView = (CardView)view.findViewById(R.id.fragment_my_check);
                        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Object data) {
                                Fragment fragment = PMManageProjectDetailFragment.newInstance(data);
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
            viewHolder.mState.setText(project.getProject_state());
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
