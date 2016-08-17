package com.example.zxl.cloudmanager.post.myPost;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostFragment extends ListFragment {

    private ArrayList<Post> mPosts = new ArrayList<Post>();

    private TextView mName, mCreateTime, mPostTime;


    private Fragment mFragment;

    private static final String TAG = "MyPostFragment";

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;
        getActivity().getActionBar().setTitle("日报");

        saveInstanceState = getArguments();
        if (null != saveInstanceState) {
            try {
                if (-1 != saveInstanceState.getInt(Link.create_time_t)) {
                    keyObj.put(Link.create_time_t, saveInstanceState.getInt(Link.create_time_t));
                }
                if (-1 != saveInstanceState.getInt(Link.create_time_f)) {
                    keyObj.put(Link.create_time_f, saveInstanceState.getInt(Link.create_time_f));
                }
                if (-1 != saveInstanceState.getInt(Link.report_time_t)) {
                    keyObj.put(Link.report_time_t, saveInstanceState.getInt(Link.report_time_t));
                }
                if (-1 != saveInstanceState.getInt(Link.report_time_f)) {
                    keyObj.put(Link.report_time_f, saveInstanceState.getInt(Link.report_time_f));
                }
                if (null != saveInstanceState.getString(Link.content)) {
                    keyObj.put(Link.content, saveInstanceState.getInt(Link.content));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put("sort", "create_time desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", 1);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "my_daily&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                mPosts.add(new Post(array.getJSONObject(i)));
                            }
                            Log.d(TAG, "mPosts: " + mPosts);
                            PostAdapter adapter = new PostAdapter(mPosts);
                            setListAdapter(adapter);
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
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Post post= ((PostAdapter)getListAdapter()).getItem(position);

        Fragment fragment = MyPostDetailFragment.newInstance(post);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.addToBackStack(null);
            transaction.hide(mFragment);
            transaction.add(R.id.postActivity, fragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(fragment);
            transaction.commit();
        }
    }

    private class PostAdapter extends ArrayAdapter<Post> {

        public PostAdapter(ArrayList<Post> posts){
            super(getActivity(), 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.leader_post_list, null);
            }

//            mPullToRefreshView = (PullToRefreshView) convertView.findViewById(R.id.my_post_pull_to_refresh);
//            mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    mPullToRefreshView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPullToRefreshView.setRefreshing(false);
//                        }
//                    }, REFRESH_DELAY);
//                }
//            });

            Post p = getItem(position);

            mName = (TextView) convertView.findViewById(R.id.main_fragment_my_post_name);
            mPostTime = (TextView) convertView.findViewById(R.id.main_fragment_my_post_post_time);
            mCreateTime = (TextView) convertView.findViewById(R.id.main_fragment_my_post_create_time);

            mName.setText(p.getMem_name());

            if(p.getReport_time() == 0) {
                mPostTime.setText("——");
            }else {
                mPostTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(p.getReport_time()));
            }
            mCreateTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(p.getCreate_time()));

            return convertView;
        }
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
                    fragment = new MyPostSearchFragment();
                    fm.beginTransaction().replace(R.id.postActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
