package com.example.zxl.cloudmanager.contact.projectManagerContact;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
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
 * A simple {@link Fragment} subclass.
 */
public class ContactSearchFragment extends Fragment {
    private static final String TAG = "ContactSearchFragment";

    private Spinner mProjectName;
    private EditText mContactName;
    private EditText mContactPhone;
    private TextView mBack;
    private Button mSearchBtn;
    private Fragment mFragment;

    private ArrayAdapter<String> mProjectNameAdapter;
    private ArrayList<String> project_name_list = new ArrayList<String>(); //名字
    private ArrayList<String> project_name_id_list = new ArrayList<String>(); //名字id
    private String project_name_id;
    private String contact_name;
    private String contact_phone;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_list, container, false);

        init(v);
        control();

        return  v;
    }

    private void init(View v){
        mProjectName = (Spinner) v.findViewById(R.id.contact_search_project_name);
        mContactName = (EditText) v.findViewById(R.id.contact_search_contact_name);
        mContactPhone = (EditText) v.findViewById(R.id.contact_search_phone);
        mSearchBtn = (Button) v.findViewById(R.id.project_list_check_button);
        mBack = (TextView) v.findViewById(R.id.contact_search_back);
     }

    private void control() {
        try {
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "pm_contact&act=options_pmcontact", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        Log.d(TAG, "workArray: " + workArray);

                        for (int i = 0; i < workArray.length(); i++) {
                            if (workArray.getJSONObject(i).has("project_name"))
                                project_name_list.add(workArray.getJSONObject(i).getString("project_name"));
                            if (workArray.getJSONObject(i).has("pm_id"))
                                project_name_id_list.add(workArray.getJSONObject(i).getString("pm_id"));
                        }

                        if (null != mFragment.getActivity()){
                            mProjectNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, project_name_list);
                            mProjectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mProjectName.setAdapter(mProjectNameAdapter);
                            mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    project_name_id = project_name_id_list.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });

        mContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact_name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContactPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact_phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new PMContactListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.pm_id, project_name_id);
                bundle.putString(Link.contact_name, contact_name);
                bundle.putString(Link.contact_phone, contact_phone);

                fragment.setArguments(bundle);
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
    }
}
