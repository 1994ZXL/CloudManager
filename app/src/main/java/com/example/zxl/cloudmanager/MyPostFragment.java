package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zxl.cloudmanager.leaderSearch.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;
import com.example.zxl.cloudmanager.myPost.MyPostSearchFragment;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostFragment extends ListFragment {

    private ArrayList<Post> mPosts = new ArrayList<Post>();

    private Fragment mFragment;

    private static final String SEARCH_KEY = "search_key";
    private int searchKey;
    private ArrayList<Integer> key = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        mFragment = this;

        saveInstanceState = getArguments();
        if (null == saveInstanceState) {
            mPosts = PostLab.newInstance(mFragment.getActivity()).getPosts();
        } else {
            key = getArguments().getIntegerArrayList(SEARCH_KEY);
        }

        for (int i = 0; i < key.size(); i++) {
            mPosts.add(PostLab.newInstance(mFragment.getActivity()).getPosts().get(key.get(i)));
        }

        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Post post= ((PostAdapter)getListAdapter()).getItem(position);

        Fragment fragment = MyPostDetailFragment.newInstance(post);
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

    private class PostAdapter extends ArrayAdapter<Post> {

        public PostAdapter(ArrayList<Post> posts){
            super(getActivity(), 0, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_my_post, null);
            }

            Post p = getItem(position);

            TextView postName = (TextView) convertView.findViewById(R.id.main_fragment_my_post_name);
            postName.setText(p.getName());

            TextView postTime = (TextView) convertView.findViewById(R.id.main_fragment_my_post_time);
            postTime.setText(p.getPostTime());

            ImageButton searchBtn = (ImageButton) convertView.findViewById(R.id.main_fragment_my_post_searchBtn);
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new MyPostSearchFragment();
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

            return convertView;
        }
    }
}
