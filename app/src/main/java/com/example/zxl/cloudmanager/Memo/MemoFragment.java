package com.example.zxl.cloudmanager.Memo;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.CustomRecyclerAdapter;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Memo;
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
 * Created by ZXL on 2016/7/7.
 */
public class MemoFragment extends Fragment {
    private static final String TAG = "MemoFragment";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter<Memo> mAdpter;
    private List<Memo> memos = new ArrayList<Memo>();

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private TextView mBack;
    private TextView mSearch;
    private Button mAdd;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    private static AsyncHttpClient mHttpcDelete = new AsyncHttpClient();
    private RequestParams mParamsDelete = new RequestParams();
    private JSONObject keyObjDelete = new JSONObject();
    private String keyDelete = "";

    private Fragment mFragment;

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
                if (null != saveInstanceState.getString(Link.title))
                    keyObj.put(Link.title, saveInstanceState.getString(Link.title));
                if (-1 != saveInstanceState.getInt(Link.start_time))
                    keyObj.put(Link.start_time, saveInstanceState.getInt(Link.start_time));
                if (-1 != saveInstanceState.getInt(Link.my_note_over_time))
                    keyObj.put(Link.my_note_over_time, saveInstanceState.getInt(Link.my_note_over_time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());

            keyObj.put("sort", "create_time desc");
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
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                if (statusCode == 200) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray array = rjo.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                memos.add(new Memo(array.getJSONObject(i)));
                            }

                            mRecyclerView = (RecyclerView)v.findViewById(R.id.memo_recyclerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setHasFixedSize(true);
                            mCardView = (CardView)v.findViewById(R.id.fragment_my_memo);
                            mAdpter = new CustomRecyclerAdapter<Memo>(mFragment.getActivity(), memos, R.layout.memo_card_item) {
                                @Override
                                protected void display(final ViewHolderHelper viewHolder, final Memo data) {
                                    if (data.getCreate_time() == 0)
                                        viewHolder.setText(R.id.memo_card_create_time,"--");
                                    else viewHolder.setText(R.id.memo_card_create_time, DateForGeLingWeiZhi.fromGeLinWeiZhi(data.getCreate_time()));

                                    viewHolder.setText(R.id.memo_card_title, data.getTitle())
                                            .setText(R.id.memo_card_content, data.getContent())
                                            .setImageButton(R.id.memo_card_delete, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    final int i = memos.indexOf(data);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
                                                    builder.setTitle("提示");
                                                    builder.setMessage("是否要删除");
                                                    builder.setNegativeButton("取消", null);
                                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                                        @Override
                                                        public void onClick(DialogInterface dialog,int which){
                                                            try {
                                                                keyObjDelete.put(Link.note_id, memos.get(i).getNote_id());
                                                                keyDelete = DESCryptor.Encryptor(keyObjDelete.toString());
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            mParamsDelete.put("key", keyDelete);
                                                            Log.d(TAG,"key:" + keyDelete);

                                                            mHttpcDelete.post(Link.localhost + "my_note&act=drop", mParamsDelete, new JsonHttpResponseHandler() {
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
                                                            memos.remove(i);
                                                            mRecyclerView.scrollToPosition(i - 1);
                                                            mAdpter.notifyDataSetChanged();
                                                        }
                                                    });
                                                    builder.show();
                                                }
                                            });
                                }
                            };
                            mAdpter.setOnItemClickListener(new CustomRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, Object data) {
                                    Fragment fragment = MemoDetailFragment.newInstance(data);
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

                            mRecyclerView.setAdapter(mAdpter);

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

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = layoutInflater.inflate(R.layout.main_fragment_my_memo, parent, false);

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        url = Link.my_note + Link.get_list;
        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.my_memo_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        memos.clear();
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
                        memos.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });

        mBack = (TextView) v.findViewById(R.id.my_memo_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.my_memo_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MemoSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        mAdd = (Button) v.findViewById(R.id.memo_add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MemoAddFragment();
                    fragment.setArguments(saveInstanceState);
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        return v;
    }
}
