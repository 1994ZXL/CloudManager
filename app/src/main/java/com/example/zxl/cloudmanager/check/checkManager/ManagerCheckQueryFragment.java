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
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
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

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mName;
    private Button mQueryButton;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
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
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(ManagerCheckQueryFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckQueryFragment");
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(ManagerCheckQueryFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckQueryFragment");
            }
        });

        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerCheckListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.mem_name, name);

                if (null != bgtime) {
                    bundle.putInt(Link.att_date_start, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.att_date_start, -1);
                }

                if (null != edtime) {
                    bundle.putInt(Link.att_date_end, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.att_date_end, -1);
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
        mBeginTimeBtn = (Button)v.findViewById(R.id.check_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.check_end_time_button);
        mName = (EditText) v.findViewById(R.id.manager_check_name_edittext);
        mQueryButton = (Button) v.findViewById(R.id.main_fragment_manager_check_query_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mBeginTimeBtn.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + mBeginTimeBtn.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mEndTimeBtn.setText(edtime);
            Log.d(TAG, "endTimeButton: " + mEndTimeBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
