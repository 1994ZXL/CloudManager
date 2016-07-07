package com.example.zxl.cloudmanager.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Memo;

import java.util.Date;


public class MemoDetailFragment extends Fragment {

    private static final int REQUEST_DATE = 0;

    private Memo memo;
    private EditText mDetailTitleET;
    private Button mDetailDateBtn;
    private EditText mDetailContentET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memodetail, container, false);
        mDetailTitleET = (EditText)v.findViewById(R.id.memo_detail_title_edittext);

        mDetailDateBtn = (Button)v.findViewById(R.id.memo_detail_date_button);
        mDetailDateBtn.setEnabled(true);//设置按钮可点
        mDetailDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date());
                fragment.setTargetFragment(MemoDetailFragment.this,11);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME,1);
                fragment.show(getFragmentManager(),"MemoDatePicker");
            }
        });

        mDetailContentET = (EditText)v.findViewById(R.id.memo_detail_content_edittext);
        return  v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //memo.setDate(date);
            //updateDate();
        }
    }

}
