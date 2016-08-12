package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private static final String EXTRA_TYPE = "user_type";

    private TextView usernameTextView, passwordTextView;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String name;
    private String password;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.login_fragment, parent, false);

        usernameTextView = (TextView)v.findViewById(R.id.loginFragment_username_textview);
        passwordTextView = (TextView)v.findViewById(R.id.loginFragment_password_textview);
        usernameEditText = (EditText)v.findViewById(R.id.loginFragment_username_edittext);
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEditText = (EditText)v.findViewById(R.id.loginFragment_password_edittext);
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton = (Button)v.findViewById(R.id.loginFragment_loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyObj.put(Link.user_name, name);
                    keyObj.put(Link.password, password);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key: " + key );

                mHttpc.post(Link.localhost + "user&act=login", mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode == 200) {
                            try {
                                if (response.getBoolean("result")) {
                                    JSONArray array = response.getJSONArray("data1");
                                    Log.d(TAG, "array: " + array);
                                    for (int i = 0; i < array.length(); i++) {
                                        User.newInstance().setUser(array.getJSONObject(i));
                                    }
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                            }
                        } else if (statusCode == 400) {
                            try {
                                if (response.getString("msg") != "user_name_not_exist") {
                                    if (response.getString("msg") == "password_error"){
                                        Toast.makeText(getActivity(),
                                                R.string.password_error,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else if (response.getString("msg") == "user_name_not_exist") {
                                    Toast.makeText(getActivity(),
                                            R.string.user_name_not_exist,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d(TAG, "请求失败  " + throwable);
                    
                    }

                });

            }
        });

        return v;
    }
}
