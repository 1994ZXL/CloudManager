package com.example.zxl.cloudmanager.Memo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/9/12.
 */
public class MemoAddFragment extends Fragment {
    private static final String TAG = "MemoAddFragment";

    private EditText mTitle;
    private EditText mContent;
    private TextView mBack;
    private TextView mAdd;

    private String title;
    private String content;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.memo_add_fragment, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mTitle = (EditText) v.findViewById(R.id.memo_add_title);
        mContent = (EditText) v.findViewById(R.id.memo_add_content);
        mBack = (TextView) v.findViewById(R.id.memo_add_back);
        mAdd = (TextView) v.findViewById(R.id.memo_add_add);
        url = Link.my_note + Link.add;
    }

    private void control() {
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
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == title ||null == content) {
                    Toast.makeText(getActivity(),
                            "填写信息不全",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObj.put(Link.title, title);
                        keyObj.put(Link.content, content);
                        keyObj.put(Link.user_id, User.newInstance().getUser_id());
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
            }
        });
    }
}
