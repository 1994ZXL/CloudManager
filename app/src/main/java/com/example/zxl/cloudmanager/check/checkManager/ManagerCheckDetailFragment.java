package com.example.zxl.cloudmanager.check.checkManager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;

/**
 * Created by ZXL on 2016/8/11.
 */
public class ManagerCheckDetailFragment extends Fragment{
    private TextView name,checkLocation,checkManager;
    private TextView chekdate,stipulationOnDuty,stipulaitonOffDuty,onDuty,offDuty,earlyMin, lateMin;
    private TextView leave, overTime, travel;

    private TextView mEdit;
    private TextView mBack;

    private static Check mCheck = new Check();

    private Fragment mFragment;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static ManagerCheckDetailFragment newInstance(Object data) {
        mCheck = (Check) data;
        ManagerCheckDetailFragment fragment = new ManagerCheckDetailFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manager_check_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_check:
                Fragment fragment = ManagerCheckEditFragment.newInstance(mCheck);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.manager_check_details, parent, false);

        init(v);
        control();

        return v;
    }

    private void init(View view) {
        name = (TextView)view.findViewById(R.id.manager_check_details_name);
        checkLocation = (TextView)view.findViewById(R.id.manager_check_details_check_location);
        checkManager = (TextView)view.findViewById(R.id.manager_check_details_check_manager);
        chekdate = (TextView)view.findViewById(R.id.manager_check_details_check_date);
        stipulationOnDuty = (TextView)view.findViewById(R.id.manager_check_details_stipulate_dutytime);
        stipulaitonOffDuty = (TextView)view.findViewById(R.id.manager_check_details_stipulate_offdutytime);
        onDuty = (TextView)view.findViewById(R.id.manager_check_details_duty_sign_time);
        offDuty = (TextView)view.findViewById(R.id.manager_check_details_offduty_sign_time);
        earlyMin = (TextView)view.findViewById(R.id.manager_check_details_early_min);
        lateMin = (TextView)view.findViewById(R.id.manager_check_details_late_min);
        leave = (TextView)view.findViewById(R.id.manager_check_details_leave);
        overTime = (TextView)view.findViewById(R.id.manager_check_details_work_overtime);
        travel = (TextView)view.findViewById(R.id.manager_check_details_work_travel);

        mEdit = (TextView)view.findViewById(R.id.manager_check_details_edit);
        mBack = (TextView)view.findViewById(R.id.manager_check_details_back);
    }

    private void control() {
        name.setText(mCheck.getMem_name());
        checkLocation.setText(mCheck.getPuncher_name());
        checkManager.setText(mCheck.getMaster_name());

        if (mCheck.getAtt_date() != 0) {
            chekdate.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mCheck.getAtt_date()+28800));
        } else {
            chekdate.setText("——");
        }

        if (mCheck.getS_time() != 0){
            stipulationOnDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mCheck.getS_time()+28800));
        } else {
            stipulationOnDuty.setText("——");
        }

        if (mCheck.getE_time() != 0) {
            stipulaitonOffDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mCheck.getE_time()+28800));
        } else {
            stipulaitonOffDuty.setText("——");
        }

        if (mCheck.getS_att_time() == 0) {
            onDuty.setText("——");
        }else {
            onDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mCheck.getS_att_time()+28800));
        }

        if (mCheck.getE_att_time() != 0) {
            offDuty.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mCheck.getE_att_time()+28800));
        } else {
            offDuty.setText("——");
        }

        if (mCheck.getEarly_min() != 0) {
            earlyMin.setText(String.valueOf(mCheck.getEarly_min()));
        } else {
            earlyMin.setText("——");
        }

        if (mCheck.getLate_min() != 0) {
            lateMin.setText(String.valueOf(mCheck.getLate_min()));
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

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = ManagerCheckEditFragment.newInstance(mCheck);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
