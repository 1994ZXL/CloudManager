package com.example.zxl.cloudmanager.checkManager.travel;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zxl.cloudmanager.MyTravelDetailFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class ManagerTravelListFragment extends ListFragment {
    private static final String TAG = "CMTravelFragment";
    private ArrayList<Travel> travels;
    private Button mSearchBtn;
    private Fragment mFragment;

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 4000;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;

        travels = TravelLab.newInstance(getActivity()).getTravels();
        TravelAdapter adapter = new TravelAdapter(travels);
        setListAdapter(adapter);

        mPullToRefreshView = (PullToRefreshView) getActivity().findViewById(R.id.pull_to_refresh);
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
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Travel travel= ((TravelAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, "item被点击");


        Fragment fragment = new TravelDetailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.addToBackStack(null);
            transaction.hide(mFragment);
            transaction.add(R.id.cmTravelActivity, fragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(fragment);
            transaction.commit();
        }
    }

    private class TravelAdapter extends ArrayAdapter<Travel> {

        public TravelAdapter(ArrayList<Travel> travels){
            super(getActivity(), 0, travels);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_travel, null);
            }

            Travel travel = getItem(position);

            TextView name = (TextView) convertView.findViewById(R.id.main_fragment_travel_name);
            name.setText(travel.getName());

            TextView beginTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_beginTime);
            beginTime.setText(travel.getBeginTime());

            TextView endTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_endTime);
            endTime.setText(travel.getBeginTime());

            TextView state = (TextView) convertView.findViewById(R.id.main_fragment_travel_state);
            state.setText(travel.getTravelState());

            return convertView;
        }
    }
}
