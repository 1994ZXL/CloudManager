package com.example.zxl.cloudmanager.check.leader;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.check.myCheck.MyCheckFragment;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.ArrayList;
import java.util.Date;

public class LSearchCheckFragment extends Fragment {
    private Check check;

    private Button beginTimeButton;
    private Button endTimeButton;

    private Button mSearchBtn;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;

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
        getActivity().getActionBar().setTitle("考勤查询");
        init(v);

        beginTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(LSearchCheckFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(LSearchCheckFragment.this, 13);
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

        Fragment fragment = new LSCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putString("where", "SearchCheckFragment");
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
        beginTimeButton = (Button)v.findViewById(R.id.check_begin_time_button);
        endTimeButton = (Button) v.findViewById(R.id.check_end_time_button);
        mSearchBtn = (Button) v.findViewById(R.id.search_check_search_button);
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
        beginTimeButton.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + beginTimeButton.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            endTimeButton.setText(edtime);
            Log.d(TAG, "endTimeButton: " + endTimeButton.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
