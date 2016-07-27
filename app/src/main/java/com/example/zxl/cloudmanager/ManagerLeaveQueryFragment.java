package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.checkManager.leave.LeaveListFragment;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.LeaveQueryLab;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerLeaveQueryFragment extends ListFragment{
    private ArrayList<Leave> mLeaves;
    private static final String[] leaveTypeList={"病假", "事假", "婚假", "丧假", "产假", "年休假"};
    private static final String[] leaveStateList={"已批准", "待批准", "未被批准"};
    private ArrayAdapter<String> spinnerAdapter;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;

        mLeaves = LeaveQueryLab.newInstance(getActivity()).getLeaveQuery();
        LeaveAdapter adapter = new LeaveAdapter(mLeaves);
        setListAdapter(adapter);
    }

    private class LeaveAdapter extends ArrayAdapter<Leave> {

        public LeaveAdapter(ArrayList<Leave> Leaves){
            super(getActivity(), 0, Leaves);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_manager_leave_query_item, null);
            }


            Leave leave = getItem(position);
            TextView textView = (TextView) convertView.findViewById(R.id.main_fragment_manager_leave_query);
            Spinner spinner = (Spinner) convertView.findViewById(R.id.main_fragment_manager_leave_query_Sprinner);
            Button button = (Button) convertView.findViewById(R.id.main_fragment_manager_leave_query_button);

            if (0 == position) {
                textView.setText(R.string.apply_time);
                spinner.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            } else if (1 == position) {
                textView.setText(R.string.leave_type);
                spinnerAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, leaveTypeList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
                button.setVisibility(View.INVISIBLE);
            } else if (2 == position) {
                textView.setText(R.string.leave_state);
                spinnerAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, leaveStateList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new LeaveListFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                }
            });

            return convertView;
        }
    }
}
