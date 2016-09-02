package com.example.zxl.cloudmanager.check.leader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.check.myCheck.MyCheckFragment;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.model.UserLab;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/8/15.
 */
public class LeaderCheckSearchFragment extends Fragment {
    private Check check;

    private EditText mName;
    private TextView beginTimeButton;
    private TextView endTimeButton;
    private Button mSearchBtn;

    private String name;

    private static final String TAG = "LCSearchFragment";

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
        View v = inflater.inflate(R.layout.leader_check_search, container, false);

        init(v);

        mName.addTextChangedListener(new TextWatcher() {
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
        beginTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, beginTimeButton);
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, endTimeButton);
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

        Fragment fragment = new LSCheckFragment();
        Bundle bundle = new Bundle();
        if (null != name) {
            bundle.putString(Link.mem_name, name);
        }
        if (null != beginTimeButton.getText()) {
            bundle.putInt(Link.att_date_from, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(beginTimeButton.getText().toString()));
        } else {
            bundle.putInt(Link.att_date_from, -1);
        }
        if (null != endTimeButton.getText()) {
            bundle.putInt(Link.att_date_to, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(endTimeButton.getText().toString()));
        } else {
            bundle.putInt(Link.att_date_to, -1);
        }
        bundle.putInt(Link.att_date_start, -1);
        bundle.putInt(Link.att_date_end, -1);
        bundle.putString(Link.comp_id, User.newInstance().getComp_id());

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
        mName = (EditText) v.findViewById(R.id.leader_check_search_name);
        beginTimeButton = (TextView)v.findViewById(R.id.leader_check_search_beginTime);
        endTimeButton = (TextView) v.findViewById(R.id.leader_check_search_endTime);
        mSearchBtn = (Button) v.findViewById(R.id.leader_check_search_button);
    }

}
