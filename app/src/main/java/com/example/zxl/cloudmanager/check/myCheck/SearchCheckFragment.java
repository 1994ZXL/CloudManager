package com.example.zxl.cloudmanager.check.myCheck;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.ArrayList;
import java.util.Date;

public class SearchCheckFragment extends Fragment {
    private Check check;

    private TextView beginTimeButton;
    private TextView endTimeButton;

    private Button mSearchBtn;
    private TextView mBack;
    private TextView mClean;

    private static final String TAG = "SearchCheckFragment";

    private ArrayList<Check> mChecks = new ArrayList<Check>();

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_check, container, false);

        init(v);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mClean.setVisibility(View.INVISIBLE);

        beginTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(SearchCheckFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(SearchCheckFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return v;
    }

    private void search() {
        mChecks = CheckLab.newInstance(mFragment.getActivity()).get();

        Fragment fragment = new MyCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putString("where", "SearchCheckFragment");
        if (null != beginTimeButton.getText()) {
            bundle.putInt(Link.att_date_start, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(beginTimeButton.getText().toString()));
        } else {
            bundle.putInt(Link.att_date_start, -1);
        }
        if (null != endTimeButton.getText()) {
            bundle.putInt(Link.att_date_end, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(endTimeButton.getText().toString()));
        } else {
            bundle.putInt(Link.att_date_end, -1);
        }
        bundle.putInt(Link.att_date_from, -1);
        bundle.putInt(Link.att_date_to, -1);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.hide(mFragment);
            transaction.replace(R.id.blankActivity, fragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(fragment);
            transaction.commit();
        }
    }

    private void init(View v){
        beginTimeButton = (TextView)v.findViewById(R.id.check_begin_time_button);
        endTimeButton = (TextView) v.findViewById(R.id.check_end_time_button);
        mSearchBtn = (Button) v.findViewById(R.id.search_check_search_button);

        mBack = (TextView) v.findViewById(R.id.fragment_search_check_back);
        mClean = (TextView) v.findViewById(R.id.fragment_search_check_clean);
    }

}
