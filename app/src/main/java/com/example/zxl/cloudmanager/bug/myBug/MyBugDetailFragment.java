package com.example.zxl.cloudmanager.bug.myBug;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.post.myPost.MyPostFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/22.
 */
public class MyBugDetailFragment extends Fragment {
    private static final String TAG = "MyBugDetailFragment";

    private static Bug sBug = new Bug();

    private TextView mProjectName;
    private TextView mBugVersion;
    private TextView mBugState;
    private EditText mBugContent;
    private TextView mFoundTime;
    private TextView mFoundMan;
    private TextView mEditTime;
    private TextView mEditMan;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String content;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static MyBugDetailFragment newInstance(Object data) {
        sBug = (Bug) data;
        MyBugDetailFragment myBugDetailFragment = new MyBugDetailFragment();
        return myBugDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.bug_details, parent, false);
        init(view);
        contorl();
        return view;
    }

    private void init(View view) {
        mProjectName = (TextView) view.findViewById(R.id.bug_details_projectName);
        mBugVersion = (TextView) view.findViewById(R.id.bug_details_level);
        mBugState = (TextView) view.findViewById(R.id.bug_details_state);
        mBugContent = (EditText) view.findViewById(R.id.bug_details_content);
        mFoundTime = (TextView) view.findViewById(R.id.bug_details_found_time);
        mFoundMan = (TextView) view.findViewById(R.id.bug_details_found_man);
        mEditTime = (TextView) view.findViewById(R.id.bug_details_edit_time);
        mEditMan = (TextView) view.findViewById(R.id.bug_details_edit_man);

    }

    private void contorl() {
        mProjectName.setText(sBug.getProject_name());
        mBugVersion.setText(sBug.getLevel());
        mBugState.setText(sBug.getStatus());
        mBugContent.setText(sBug.getContent());
        mBugContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (sBug.getSubmit_time() != 0)
            mFoundTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getSubmit_time()));
        mFoundMan.setText(sBug.getSubmitter());
        if (sBug.getModify_time() != 0)
            mEditTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getModify_time()));
        mFoundMan.setText(sBug.getSubmitter());
        mEditMan.setText(sBug.getEditMan());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_message:
                try {
                    keyObj.put(Link.pmbug_id, sBug.getPmbug_id());
                    keyObj.put(Link.content, content);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "my_bug&act=edit", mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getActivity(),
                                    response.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                        }
                    }

                });
                Fragment fragment = new MyBugFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
