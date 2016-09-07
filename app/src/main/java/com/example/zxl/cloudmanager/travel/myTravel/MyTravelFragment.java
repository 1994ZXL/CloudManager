package com.example.zxl.cloudmanager.travel.myTravel;

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
import android.widget.Toast;


import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyTravelFragment extends ListFragment {
    private ArrayList<Travel> travels = new ArrayList<Travel>();

    private static final String TAG = "MyTravelFragment";

    private TextView mBack;
    private TextView mSearch;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    private Fragment mAimFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;


        saveInstanceState = getArguments();
        if (null != saveInstanceState) {
            try {
                if (-1 != saveInstanceState.getInt(Link.start_time_s))
                    keyObj.put(Link.start_time_s, saveInstanceState.getInt(Link.start_time_s));
                if (-1 != saveInstanceState.getInt(Link.start_time_e))
                    keyObj.put(Link.start_time_e, saveInstanceState.getInt(Link.start_time_e));
                if (-1 != saveInstanceState.getInt(Link.over_time_s))
                    keyObj.put(Link.over_time_s, saveInstanceState.getInt(Link.over_time_s));
                if (-1 != saveInstanceState.getInt(Link.over_time_e))
                    keyObj.put(Link.over_time_e, saveInstanceState.getInt(Link.over_time_e));
                if (-1 != saveInstanceState.getInt(Link.status))
                    keyObj.put(Link.status, saveInstanceState.getInt(Link.status));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put("page_count", 50);
            keyObj.put("curl_page", 1);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "my_trip&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getInt("code") == 200) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);
                            for (int i = 0; i < array.length(); i++) {
                                travels.add(new Travel(array.getJSONObject(i)));
                            }
                            TravelAdapter adapter = new TravelAdapter(travels);
                            setListAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_travel_list, container, false);

        mBack = (TextView) v.findViewById(R.id.my_travel_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch = (TextView) v.findViewById(R.id.my_travel_search);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new MyTravelSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });

        return v;
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
            name.setText(travel.getMem_name());

            TextView beginTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_beginTime);
            beginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(travel.getStart_time()));

            TextView endTime = (TextView) convertView.findViewById(R.id.main_fragment_travel_endTime);
            endTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(travel.getEnd_time()));

            TextView state = (TextView) convertView.findViewById(R.id.main_fragment_travel_state);
            state.setText(travel.getStatus());

            return convertView;
        }
    }
}
