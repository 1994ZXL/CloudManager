package com.example.zxl.cloudmanager.Memo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Memo;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MemoDetailFragment extends Fragment {
    private static final String TAG = "MemoDetailFragment";
    private static Memo sMemos;

    private EditText mTitle;
    private TextView mCreateTime;
    private EditText mContent;
    private TextView mBack;
    private TextView mEdit;

    private String title;
    private String content;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    private Fragment mFragment;

    public static MemoDetailFragment newInstance(Object data) {
        sMemos = (Memo) data;
        MemoDetailFragment fragment = new MemoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memodetail, container, false);
        savedInstanceState = getArguments();

        init(v);
        control(savedInstanceState);

        return  v;
    }

    private void init(View v) {
        mTitle = (EditText) v.findViewById(R.id.memo_details_title);
        mCreateTime = (TextView) v.findViewById(R.id.memo_details_create_time);
        mContent = (EditText) v.findViewById(R.id.memo_details_content);
        mBack = (TextView) v.findViewById(R.id.memo_details_back);
        mEdit = (TextView) v.findViewById(R.id.memo_details_save);
        url = Link.my_note + Link.edit;
    }

    private void control(final Bundle bundle) {
        mTitle.setText(sMemos.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCreateTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sMemos.getCreate_time()));
        mContent.setText(sMemos.getContent());
        mContent.addTextChangedListener(new TextWatcher() {
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
                    keyObj.put(Link.note_id, sMemos.getNote_id());
                    if (null != content)
                        keyObj.put(Link.content, content);
                    else keyObj.put(Link.content, sMemos.getContent());
                    if (null != title)
                        keyObj.put(Link.title, title);
                    else keyObj.put(Link.title, sMemos.getTitle());
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
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
                Fragment fragment = new MemoFragment();
                fragment.setArguments(bundle);
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
            }
        });
    }

}
