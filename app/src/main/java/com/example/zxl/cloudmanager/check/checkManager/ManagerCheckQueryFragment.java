package com.example.zxl.cloudmanager.check.checkManager;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/14.
 */
public class ManagerCheckQueryFragment extends Fragment {
    private static final String TAG = "MCQFragment";

    private TextView mBeginTimeBtn;
    private TextView mEndTimeBtn;
    private EditText mName;
    private Button mQueryButton;

    private String name;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_manager_check_query, container, false);

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

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, mBeginTimeBtn);
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, mEndTimeBtn);
            }
        });

        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerCheckListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.mem_name, name);

                if (null != mBeginTimeBtn.getText()) {
                    bundle.putInt(Link.att_date_from, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mBeginTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.att_date_from, -1);
                }

                if (null != mEndTimeBtn.getText()) {
                    bundle.putInt(Link.att_date_to, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mEndTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.att_date_to, -1);
                }

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (TextView)v.findViewById(R.id.check_begin_time_button);
        mEndTimeBtn = (TextView) v.findViewById(R.id.check_end_time_button);
        mName = (EditText) v.findViewById(R.id.manager_check_name_edittext);
        mQueryButton = (Button) v.findViewById(R.id.main_fragment_manager_check_query_button);
    }
}
