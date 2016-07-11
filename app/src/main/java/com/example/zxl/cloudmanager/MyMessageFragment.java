package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyMessageFragment extends Fragment {
    private Spinner sexSpinner;
    private static final String[] list={"男", "女"};
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_message, parent, false);
        getActivity().getActionBar().setTitle("我的信息");

        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);
//        sexSpinner.setOnItemClickListener(new SprinnerSelectedListener());

        return v;
    }

    private void init(View v) {
        sexSpinner = (Spinner)v.findViewById(R.id.main_fragment_my_message_sexSprinner);
    }

    class SprinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        }
    }
}
