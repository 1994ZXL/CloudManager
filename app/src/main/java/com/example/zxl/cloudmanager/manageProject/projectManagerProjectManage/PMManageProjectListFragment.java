package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.manageProject.publicSearchProjectManage.PSManageProjectActivity;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Project;
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
import java.util.List;
import java.util.concurrent.TimeoutException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/12.
 */
public class PMManageProjectListFragment extends Fragment {
    private static final String TAG = "PMListFragment";
    private static int mCurl_page;

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Project> project = new ArrayList<Project>();
    private CustomRecyclerAdapter<Project> mAdapter;

    private Fragment mFragment;
    private PullToRefreshLayout mPullToRefreshLayout;
    private TextView mBack;
    private TextView mSearch;
    private Button mAdd;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcDelete = new AsyncHttpClient();
    private RequestParams mParamsDelete = new RequestParams();
    private JSONObject keyObjDelete = new JSONObject();
    private String keyDelete = "";
    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.project_name)) {
                    keyObj.put(Link.project_name, saveInstanceState.getString(Link.project_name));
                }
                if (-1 != saveInstanceState.getInt(Link.ready_time)) {
                    keyObj.put(Link.ready_time, saveInstanceState.getInt(Link.ready_time));
                }
                if (-1 != saveInstanceState.getInt(Link.finshed_time)) {
                    keyObj.put(Link.finshed_time, saveInstanceState.getInt(Link.finshed_time));
                }
                keyObj.put(Link.project_state, saveInstanceState.getInt(Link.project_state));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put(Link.is_pmmaster, User.newInstance().getIs_pmmaster());
            keyObj.put(Link.user_type, User.newInstance().getUser_type());
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            keyObj.put("sort", "ready_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);

            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        Log.d(TAG, "Url: " + url);

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
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

                        mRecyclerView = (RecyclerView)v.findViewById(R.id.pm_list_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        mCardView = (CardView)v.findViewById(R.id.pm_manager_item);
                        mAdapter = new CustomRecyclerAdapter<Project>(mFragment.getActivity(), project, R.layout.pm_manager_item) {
                            @Override
                            protected void display(ViewHolderHelper viewHolder, final Project data) {
                                if (mFragment.getActivity().getClass() == PSManageProjectActivity.class) {
                                    viewHolder.setImageButtonGone(R.id.pm_item_delete);
                                }
                                viewHolder.setText(R.id.pm_item_name, data.getProject_name())
                                        .setText(R.id.pm_item_state, data.getProject_state())
                                        .setText(R.id.pm_item_manager, data.getGoon_technical_name())
                                        .setText(R.id.pm_item_contactor, data.getCustom_name())
                                        .setText(R.id.pm_item_company, data.getBelong_unit())
                                        .setImageButton(R.id.pm_item_delete, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                final int i = project.indexOf(data);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
                                                builder.setTitle("提示");
                                                builder.setMessage("是否要删除");
                                                builder.setNegativeButton("取消", null);
                                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog,int which){
                                                        try {
                                                            keyObjDelete.put(Link.pm_id, project.get(i).getPm_id());
                                                            keyDelete = DESCryptor.Encryptor(keyObjDelete.toString());
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        mParamsDelete.put("key", keyDelete);
                                                        Log.d(TAG,"key:" + keyDelete);

                                                        mHttpcDelete.post(Link.localhost + "manage_pm&act=drop", mParamsDelete, new JsonHttpResponseHandler() {
                                                            @Override
                                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                                try {
                                                                    Toast.makeText(getActivity(),
                                                                            response.getString("msg"),
                                                                            Toast.LENGTH_SHORT).show();
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                                Toast.makeText(getActivity(),
                                                                        R.string.edit_error,
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        project.remove(i);
                                                        mRecyclerView.scrollToPosition(i - 1);
                                                        mAdapter.notifyDataSetChanged();
                                                    }
                                                });
                                                builder.show();
                                            }
                                        });
                            }
                        };
                        mAdapter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Object data) {
                                Fragment fragment = PMManageProjectDetailFragment.newInstance(data);
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
                        mRecyclerView.setAdapter(mAdapter);

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
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = layoutInflater.inflate(R.layout.pm_manager_list, parent, false);

        mAdd = (Button) v.findViewById(R.id.pm_add);
        mSearch = (TextView) v.findViewById(R.id.pm_manage_project_search);
        mBack = (TextView) v.findViewById(R.id.pm_manage_project_back);

        if (mFragment.getActivity().getClass() == PSManageProjectActivity.class) {
            url = Link.pm_list + Link.get_list;
            mAdd.setVisibility(View.GONE);
        }
        else if (mFragment.getActivity().getClass() == PMManageProjectActivity.class)
            url = Link.manage_pm + Link.get_list;

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.pm_manage_project_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        project.clear();
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
                        project.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new ManageProjectSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMManageProjectDetailFragment();
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
}
