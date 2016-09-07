package com.example.zxl.cloudmanager.travel.checkManagerTravel;


import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Travel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerTravelDetailFragment extends Fragment {
    private static final String TAG = "MTDetailFragment";

    private static Travel mTravel = new Travel();

    private TextView mEmployerName;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mTravelReason;
    private TextView mTravelAdd;
    private TextView mTravelAddress;
    private Spinner mStatus;

    private TextView mBack;
    private TextView mEdit;

    private ArrayAdapter<String> statusAdapter;
    private String[] statusList;
    private int status;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    public static ManagerTravelDetailFragment newInstance(Object data) {
        mTravel = (Travel) data;
        ManagerTravelDetailFragment fragment = new ManagerTravelDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_detail, container, false);



        init(v);
        control();

        return v;
    }

    private void init(View v){
        mBeginTime = (TextView) v.findViewById(R.id.employer_travel_beginTime);
        mEndTime = (TextView) v.findViewById(R.id.employer_travel_endTime);
        mEmployerName = (TextView) v.findViewById(R.id.manager_travel_employer_name_spinner);
        mTravelReason = (TextView) v.findViewById(R.id.manager_travel_reason);
        mTravelAdd = (TextView) v.findViewById(R.id.manager_travel_add);
        mTravelAddress = (TextView) v.findViewById(R.id.manager_travel_address);
        mStatus = (Spinner) v.findViewById(R.id.manager_travel_state);
        if (mTravel.getStatus() == "确认")
            statusList = new String[] {"确认" ,"取消"};
        if (mTravel.getStatus() == "取消")
            statusList = new String[] {"取消" ,"确认"};

        mBack = (TextView) v.findViewById(R.id.manager_travel_detail_back);
        mEdit = (TextView) v.findViewById(R.id.manager_travel_detail_edit);
    }

    private void control() {
        mEmployerName.setText(mTravel.getMem_name());
        mBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mTravel.getStart_time()));
        mEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mTravel.getEnd_time()));
        mTravelReason.setText(mTravel.getTrip_reason());
        mTravelAdd.setText(mTravel.getAddress());
        mTravelAddress.setText(mTravel.getDetail_addr());

        statusAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(statusAdapter);
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //出差状态 2：确认，3：取消
                if (statusList[i] == "确认")
                    status = 2;
                if (statusList[i] == "取消")
                    status = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyObj.put(Link.trip_id, mTravel.getTrip_id());
                    keyObj.put(Link.status, status);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + Link.manage_trip + Link.edit, mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    }
                });

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
    }
}
