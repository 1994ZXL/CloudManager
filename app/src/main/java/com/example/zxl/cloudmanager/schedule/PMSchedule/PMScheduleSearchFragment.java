package com.example.zxl.cloudmanager.schedule.PMSchedule;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;

/**
 * Created by ZXL on 2016/9/5.
 */
public class PMScheduleSearchFragment extends Fragment {
    private static final String TAG = "PMSchSearchFragment";

    private TextView mBeginTime;
    private TextView mEndTime;
    private EditText mScheduleFrom;
    private EditText mScheduleTo;
    private Button mSearchBtn;
    private TextView mBack;

    private String percentFrom;
    private String percentTo;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pm_schedule_search, container, false);

        init(view);
        control();

        return view;
    }

    private void init(View view) {
        mBeginTime = (TextView) view.findViewById(R.id.pm_schedule_search_beginTime);
        mEndTime = (TextView) view.findViewById(R.id.pm_schedule_search_endTime);
        mScheduleFrom = (EditText) view.findViewById(R.id.pm_schedule_percent_from);
        mScheduleTo = (EditText) view.findViewById(R.id.pm_schedule_percent_to);
        mSearchBtn = (Button) view.findViewById(R.id.pm_schedule_percent_searchBtn);
        mBack = (TextView) view.findViewById(R.id.pm_schedule_search_back);
    }

    private void control() {
        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBeginTime);
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mEndTime);
            }
        });

        mScheduleFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                percentFrom = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mScheduleTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                percentTo = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMScheduleListFragment();
                Bundle bundle = new Bundle();

                if (null != mBeginTime.getText())
                    bundle.putInt(Link.report_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTime.getText().toString()));
                else bundle.putInt(Link.report_time_from, -1);

                if (null != mEndTime.getText())
                    bundle.putInt(Link.report_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTime.getText().toString()));
                else bundle.putInt(Link.report_time_to, -1);

                if (null != percentFrom)
                    bundle.putInt(Link.percent_from, Integer.parseInt(percentFrom));

                if (null != percentTo)
                    bundle.putInt(Link.percent_to, Integer.parseInt(percentTo));

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
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
