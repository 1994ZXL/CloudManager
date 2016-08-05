package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.leaderSearch.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.leaderSearch.LeaderPostSearchFragment;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;
import com.example.zxl.cloudmanager.myPost.MyPostSearchFragment;
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

    private Fragment mFragment;

    private static final String TAG = "MyPostFragment";
    private static final String SEARCH_KEY = "search_key";
    private static final String WHERE = "where";

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;
        getActivity().getActionBar().setTitle("我的日报");

        saveInstanceState = getArguments();

        mHttpc.post(Link.localhost + "manage_trip&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
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

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {

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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_my_post, null);
            }

            /*mPullToRefreshView = (PullToRefreshView) convertView.findViewById(R.id.my_post_pull_to_refresh);
            mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPullToRefreshView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshView.setRefreshing(false);
                        }
                    }, REFRESH_DELAY);
                }
            });*/
            Post p = getItem(position);

            TextView postName = (TextView) convertView.findViewById(R.id.main_fragment_my_post_name);
            postName.setText(p.getMem_name());

            TextView postTime = (TextView) convertView.findViewById(R.id.main_fragment_my_post_time);
            postTime.setText(p.getReport_time());

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
