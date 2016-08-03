package com.example.zxl.cloudmanager.checkManager.travel;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.zxl.cloudmanager.checkManager.leave.LeaveDeallFragment;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

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

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;

        travels = TravelLab.newInstance(getActivity()).getTravels();
        TravelAdapter adapter = new TravelAdapter(travels);
        setListAdapter(adapter);

        mHttpc.post("http://192.168.1.109/yunmgr_v1.0/api/uc.php?app=manage_trip&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            travels.add(new Travel(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "Travels: " + travels);
                        travels = TravelLab.newInstance(getActivity()).getTravels();
                        TravelAdapter adapter = new TravelAdapter(travels);
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

            /*mPullToRefreshView = (PullToRefreshView) convertView.findViewById(R.id.cm_travel_pull_to_refresh);
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
            Travel travel = getItem(position);

            TextView name = (TextView) convertView.findViewById(R.id.main_fragment_travel_name);
            name.setText(travel.getMem_id());

            TextView beginTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_beginTime);
            beginTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(travel.getStart_time()));

            TextView endTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_endTime);
            endTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(travel.getOver_time()));

            TextView state = (TextView) convertView.findViewById(R.id.main_fragment_travel_state);
            state.setText(travel.getStatus());

            return convertView;
        }
    }
}
