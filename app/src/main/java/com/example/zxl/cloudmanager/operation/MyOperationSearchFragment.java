package com.example.zxl.cloudmanager.operation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/9/9.
 */
public class MyOperationSearchFragment extends Fragment{
    private static final String TAG = "MyOperationSearch";

    private Spinner mProjectName;
    private Button mSearchBtn;
    private TextView mBack;

    private ArrayAdapter<String> projectAdapter;
    private ArrayList<String> project_name = new ArrayList<String>(); //项目
    private ArrayList<String> pm_id = new ArrayList<String>(); //项目id
    private String project_id;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_operation_search, container, false);

        init(v);
        control();

        return v;
    }
    private void init(View v){
        mProjectName = (Spinner) v.findViewById(R.id.my_operation_name_edittext);
        mSearchBtn = (Button) v.findViewById(R.id.my_operation_search_button);
        mBack = (TextView) v.findViewById(R.id.my_operation_search_back);
    }

    private void control() {
        try {
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        JSONArray memArray = rjo.getJSONArray("data2");
                        Log.d(TAG, "workArray: " + workArray);
                        Log.d(TAG, "memArray: " + memArray);

                        for (int i = 0; i < workArray.length(); i++) {
                            if (workArray.getJSONObject(i).has("project_name"))
                                project_name.add(workArray.getJSONObject(i).getString("project_name"));
                            if (workArray.getJSONObject(i).has("pm_id"))
                                pm_id.add(workArray.getJSONObject(i).getString("pm_id"));
                        }

                        if (null != mFragment.getActivity()){
                            projectAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, project_name);
                            projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mProjectName.setAdapter(projectAdapter);
                            mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    project_id = pm_id.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyOperationListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.work_pm, project_id);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
