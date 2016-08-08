package com.example.zxl.cloudmanager;

import android.util.Log;

import com.example.zxl.cloudmanager.model.Leave;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/8/1.
 */
public class HttpClient {
    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams;

    private static ArrayList<Leave> mLeaves = new ArrayList<Leave>();

    public static final String TAG = "HttpClient";

    public ArrayList<Leave> getJSONArray() {
        mHttpc.post("http://192.168.0.109/yunmgr_v1.0/api/uc.php?app=manage_leave&act=get_list", mParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            mLeaves.add(new Leave(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "mLeaves: " + mLeaves);
                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }


        });
        return mLeaves;
    }
}
