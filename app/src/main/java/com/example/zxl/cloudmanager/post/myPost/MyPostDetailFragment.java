package com.example.zxl.cloudmanager.post.myPost;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/26.
 */
public class MyPostDetailFragment extends Fragment {

    private static final String TAG = "MPDetailFragment";
    private TextView mName;
    private EditText mContent;
    private TextView mSubmitTime;
    private TextView mCreateTime;

    private static Post sPost = new Post();

    private static final String MYPOST_CONTENT = "日报内容";

    private static int mPosition;

    private Fragment mFragment;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String content;

    public static MyPostDetailFragment newInstance(Post post) {
        sPost = post;
        MyPostDetailFragment fragment = new MyPostDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        //ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
       // mBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_details, container, false);

        init(view);
        control();

        return view;
    }

    private void init(View view) {
        mName = (TextView) view.findViewById(R.id.post_details_name);
        mContent = (EditText) view.findViewById(R.id.post_details_content);
        mSubmitTime = (TextView) view.findViewById(R.id.post_details_submit_time);
        mCreateTime = (TextView) view.findViewById(R.id.post_details_create_time);
    }

    private void control() {
        mName.setText(sPost.getMem_name());
        mContent.setText(sPost.getContent());
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
        mSubmitTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getReport_time()));
        mCreateTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sPost.getCreate_time()));
    }

    @Override
    public void onPause() {
        super.onPause();
        /*ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
        mBtn.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_message:
                try {
                    keyObj.put(Link.daily_id, sPost.getDaily_id());
                    keyObj.put(Link.content, content);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "my_daily&act=edit", mParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
