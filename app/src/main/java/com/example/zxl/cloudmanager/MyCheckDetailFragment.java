package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.Check;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyCheckDetailFragment extends Fragment{
    private TextView name,project,checkLocation,checkManager;
    private TextView stipulationOnDuty,stipulaitonOffDuty,onDuty,offDuty,onDutyDispose,offDutyDispose;
    private TextView state, leave, overTime, travel, late;

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
        project = (TextView)view.findViewById(R.id.check_details_project);
        checkLocation = (TextView)view.findViewById(R.id.check_details_check_location);
        checkManager = (TextView)view.findViewById(R.id.check_details_check_manager);
        stipulationOnDuty = (TextView)view.findViewById(R.id.check_details_stipulate_dutytime);
        stipulaitonOffDuty = (TextView)view.findViewById(R.id.check_details_stipulate_offdutytime);
        onDuty = (TextView)view.findViewById(R.id.check_details_duty_sign_time);
        offDuty = (TextView)view.findViewById(R.id.check_details_offduty_sign_time);
        onDutyDispose = (TextView)view.findViewById(R.id.check_details_duty_manage_time);
        offDutyDispose = (TextView)view.findViewById(R.id.check_details_offduty_manage_time);
        state = (TextView)view.findViewById(R.id.check_details_manage_state);
        leave = (TextView)view.findViewById(R.id.check_details_leave);
        overTime = (TextView)view.findViewById(R.id.check_details_work_overtime);
        travel = (TextView)view.findViewById(R.id.check_details_work_travel);
        late = (TextView)view.findViewById(R.id.check_details_late);
    }

    private void control() {
        name.setText(mCheck.getName());
        project.setText(mCheck.getProject());
        checkLocation.setText(mCheck.getCheckLocation());
        checkManager.setText(mCheck.getCheckManager());
        stipulationOnDuty.setText(mCheck.getStipulationOnDutyTime());
        stipulaitonOffDuty.setText(mCheck.getStipulationOffDutyTime());
        onDuty.setText(mCheck.getDutyTime());
        offDuty.setText(mCheck.getOffDutyTime());
        onDutyDispose.setText(mCheck.getOndutyDisposeTime());
        offDutyDispose.setText(mCheck.getOffDutyDisposeTime());
        state.setText(mCheck.getState());
        leave.setText(mCheck.getLeave());
        overTime.setText(mCheck.getOverTime());
        travel.setText(mCheck.getTravel());
        late.setText(mCheck.getLate());
    }
}
