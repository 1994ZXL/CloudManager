package com.example.zxl.cloudmanager.check.checkManager;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.Date;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerCheckEditFragment extends Fragment{
    private static final String TAG = "MCEdtiFragment";

    private static Check sCheck;

    private Button mS_att_time;
    private Button mS_time;
    private Button mE_att_time;
    private Button mE_time;
    private Button mSaveBtn;

    private Date s_att_time;
    private String satttime;
    private Date s_time;
    private String stime;
    private Date e_att_time;
    private String eatttime;
    private Date e_time;
    private String etime;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFragment = this;
    }

    public static ManagerCheckEditFragment newInstance(Check check) {
        sCheck = check;
        ManagerCheckEditFragment fragment = new ManagerCheckEditFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_manager_check_edit, parent, false);

        mS_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_stipulate_dutytime);
        mS_att_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_duty_sign_time);
        mE_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_stipulate_offdutytime);
        mE_att_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_offduty_sign_time);
        mSaveBtn = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_save_edit_button);

        if (sCheck.getS_time() != 0){
            mS_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getS_time()));
        } else {
            mS_time.setText("——");
        }

        if (sCheck.getE_time() != 0) {
            mE_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getE_time()));
        } else {
            mE_time.setText("——");
        }

        if (sCheck.getS_att_time() == 0) {
            mS_att_time.setText("——");
        }else {
            mS_att_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getS_att_time()));
        }

        if (sCheck.getE_att_time() != 0) {
            mE_att_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getE_att_time()));
        } else {
            mE_att_time.setText("——");
        }

        mS_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(ManagerCheckEditFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
            }
        });

        mS_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(ManagerCheckEditFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
            }
        });

        mE_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(ManagerCheckEditFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
            }
        });

        mE_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(ManagerCheckEditFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerCheckListFragment();
                Bundle bundle = new Bundle();



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
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 12) {
            s_att_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateSattDate();
        } else if (requestCode == 13) {
            s_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateSDate();
        } else if (requestCode == 14) {
            e_att_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEattDate();
        } else if (requestCode == 15) {
            e_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEDate();
        }
    }

    private void updateSattDate(){
        satttime = android.text.format.DateFormat.format("yyyy年MM月dd", s_att_time).toString();
        Log.d(TAG, "bgtime: " + satttime);
        mS_att_time.setText(satttime);
    }
    private void updateEattDate(){
        if (e_att_time.after(s_att_time)) {
            eatttime = android.text.format.DateFormat.format("yyyy年MM月dd", e_att_time).toString();
            Log.d(TAG, "edtime: " + eatttime);
            mE_att_time.setText(eatttime);
            Log.d(TAG, "endTimeButton: " + mE_att_time.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSDate(){
        stime = android.text.format.DateFormat.format("yyyy年MM月dd", s_time).toString();
        Log.d(TAG, "bgtime: " + stime);
        mS_time.setText(stime);
    }
    private void updateEDate(){
        if (e_time.after(s_time)) {
            etime = android.text.format.DateFormat.format("yyyy年MM月dd", e_time).toString();
            Log.d(TAG, "edtime: " + etime);
            mE_time.setText(etime);
            Log.d(TAG, "endTimeButton: " + mE_time.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
