package com.example.zxl.cloudmanager.check.myCheck;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;

import java.util.Date;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyCheckDetailFragment extends Fragment{
    private TextView name,checkLocation,checkManager;
    private TextView chekdate,stipulationOnDuty,stipulaitonOffDuty,onDuty,offDuty,earlyMin, lateMin;
    private TextView leave, overTime, travel;

    private static final String EXTRA_OBJECT = "check";

    private static Check mCheck = new Check();

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static MyCheckDetailFragment newInstance(Object data) {
        mCheck = (Check) data;
        MyCheckDetailFragment fragment = new MyCheckDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.check_details, parent, false);

        init(v);
        control();

        return v;
    }

    private void init(View view) {
        name = (TextView)view.findViewById(R.id.check_details_name);
        checkLocation = (TextView)view.findViewById(R.id.check_details_check_location);
        checkManager = (TextView)view.findViewById(R.id.check_details_check_manager);
        chekdate = (TextView)view.findViewById(R.id.check_details_check_date);
        stipulationOnDuty = (TextView)view.findViewById(R.id.check_details_stipulate_dutytime);
        stipulaitonOffDuty = (TextView)view.findViewById(R.id.check_details_stipulate_offdutytime);
        onDuty = (TextView)view.findViewById(R.id.check_details_duty_sign_time);
        offDuty = (TextView)view.findViewById(R.id.check_details_offduty_sign_time);
        earlyMin = (TextView)view.findViewById(R.id.check_details_early_min);
        lateMin = (TextView)view.findViewById(R.id.check_details_late_min);
        leave = (TextView)view.findViewById(R.id.check_details_leave);
        overTime = (TextView)view.findViewById(R.id.check_details_work_overtime);
        travel = (TextView)view.findViewById(R.id.check_details_work_travel);
    }

    private void control() {
        name.setText(mCheck.getMem_name());
        checkLocation.setText(mCheck.getPuncher_name());
        checkManager.setText(mCheck.getMaster_name());

        if (mCheck.getAtt_date() == 0) {
            chekdate.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getAtt_date()));
        } else {
            chekdate.setText("——");
        }

        if (mCheck.getS_att_time() == 0){
            stipulationOnDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getS_att_time()));
        } else {
            stipulationOnDuty.setText("——");
        }

        if (mCheck.getE_att_time() == 0) {
            stipulaitonOffDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getE_att_time()));
        } else {
            stipulaitonOffDuty.setText("——");
        }

        if (mCheck.getS_att_time() == 0) {
            onDuty.setText("——");
        }else {
            onDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getS_att_time()));
        }

        if (mCheck.getE_att_time() == 0) {
            offDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getE_att_time()));
        } else {
            offDuty.setText("——");
        }

        if (mCheck.getEarly_min() == 0) {
            earlyMin.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getEarly_min()));
        } else {
            earlyMin.setText("——");
        }

        if (mCheck.getLate_min() == 0) {
            lateMin.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getLate_min()));
        } else {
            lateMin.setText("——");
        }

        if (mCheck.isHas_my_leave()){
            leave.setText("是");
        } else {
            leave.setText("否");
        }

        if (mCheck.isHas_my_work()) {
            overTime.setText("是");
        } else {
            overTime.setText("否");
        }

        if (mCheck.isHas_my_trip()) {
            travel.setText("是");
        } else {
            travel.setText("否");
        }
    }
}
