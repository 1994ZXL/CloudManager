package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseFragment;
import com.example.zxl.cloudmanager.travel.TravelSearchFragment;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyTravelFragment extends ListFragment {
    private ArrayList<Travel> travels = new ArrayList<Travel>();
    private Button searchBtn;
    private WebView webView;

    private static final String TAG = "MyTravelFragment";
    private static final String SEARCH_KEY = "search_key";
    private ArrayList<Integer> key = new ArrayList<Integer>();//下标

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;

        saveInstanceState = getArguments();
        if (null == saveInstanceState) {
            travels = TravelLab.newInstance(mFragment.getActivity()).getTravels();
        } else {
            key = getArguments().getIntegerArrayList(SEARCH_KEY);
            for (int i = 0; i < key.size(); i++) {
                travels.add(TravelLab.newInstance(mFragment.getActivity()).getTravels().get(key.get(i)));
            }
        }

        TravelAdapter adapter = new TravelAdapter(travels);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Travel travel= ((TravelAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, "item被点击");

        Fragment fragment = MyTravelDetailFragment.newInstance(travel);
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
                    fragment = new TravelSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
