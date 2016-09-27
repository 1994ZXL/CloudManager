package com.example.zxl.cloudmanager.manageMember.projectManagerManageMember;

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
import com.example.zxl.cloudmanager.manageMember.publicSearchManageMember.PSManageMemberActivity;
import com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage.PMManageProjectDetailFragment;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.PMMember;
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

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/9/13.
 */
public class PMManageMemberListFragment extends Fragment {
    private static final String TAG = "PMListFragment";
    private static int mCurl_page;

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<PMMember> pmMembers = new ArrayList<PMMember>();
    private CustomRecyclerAdapter<PMMember> mAdapter;

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

    @Override
    public void onPause() {
        super.onPause();
        pmMembers.clear();
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.mem_name))
                    keyObj.put(Link.mem_name, saveInstanceState.getString(Link.mem_name));
                keyObj.put(Link.pm_id, saveInstanceState.getString(Link.pm_id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            keyObj.put("sort", "pmmem_id desc");
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
                            pmMembers.add(new PMMember(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "pmMembers: " + pmMembers);

                        mRecyclerView = (RecyclerView)v.findViewById(R.id.pm_manage_member_list_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        mCardView = (CardView)v.findViewById(R.id.pm_manage_member_card_item);
                        mAdapter = new CustomRecyclerAdapter<PMMember>(mFragment.getActivity(), pmMembers, R.layout.pm_manage_member_card_item) {
                            @Override
                            protected void display(ViewHolderHelper viewHolder, final PMMember data) {
                                viewHolder.setText(R.id.pm_manage_member_card_item_member_name, data.getMem_name())
                                        .setText(R.id.pm_manage_member_card_item_project_name, data.getProject_name())
                                        .setText(R.id.pm_manage_member_card_item_role, data.getRole())
                                        .setText(R.id.pm_manage_member_card_item_member_res, data.getMember_res())
                                        .setImageButton(R.id.pm_manage_member_card_item_delete, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                final int i = pmMembers.indexOf(data);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
                                                builder.setTitle("提示");
                                                builder.setMessage("是否要删除");
                                                builder.setNegativeButton("取消", null);
                                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                                    @Override
                                                    public void onClick(DialogInterface dialog,int which){
                                                        try {
                                                            keyObjDelete.put(Link.pmmem_id, pmMembers.get(i).getPmmem_id());
                                                            keyDelete = DESCryptor.Encryptor(keyObjDelete.toString());
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        mParamsDelete.put("key", keyDelete);
                                                        Log.d(TAG,"key:" + keyDelete);

                                                        mHttpcDelete.post(Link.localhost + "pm_manage_member&act=drop", mParamsDelete, new JsonHttpResponseHandler() {
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
                                                        pmMembers.remove(i);
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
                                Fragment fragment = PMManageMemberDetailFragment.newInstance(data);
                                fragment.setArguments(saveInstanceState);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                if (!fragment.isAdded()) {
                                    transaction.addToBackStack(null);
                                    transaction.hide(mFragment);
                                    transaction.replace(R.id.blankActivity, fragment);
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
        final View v = layoutInflater.inflate(R.layout.pm_manage_member_list, parent, false);

        mAdd = (Button) v.findViewById(R.id.pm_manage_member_list_add);
        mSearch = (TextView) v.findViewById(R.id.pm_manage_member_list_search);
        mBack = (TextView) v.findViewById(R.id.pm_manage_member_list_back);

        if (mFragment.getActivity().getClass() == PMManageMemberActivity.class)
            url = Link.pm_manage_member + Link.get_list;
        else if (mFragment.getActivity().getClass() == PSManageMemberActivity.class) {
            url = Link.pm_member + Link.get_list;
            mAdd.setVisibility(View.GONE);
        }

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.pm_manage_member_list_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        pmMembers.clear();
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
                        pmMembers.clear();
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
                    fragment = new ManageMemberSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMManageMemberDetailFragment();
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
