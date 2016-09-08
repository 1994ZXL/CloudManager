package com.example.zxl.cloudmanager.Memo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Memo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MemoDetailFragment extends Fragment {

    private Memo memo = new Memo();
    private EditText mDetailTitleET;
    private TextView mDetailDateBtn;
    private EditText mDetailContentET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private Date getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memodetail, container, false);
        mDetailTitleET = (EditText)v.findViewById(R.id.memo_detail_title_edittext);

        mDetailDateBtn = (TextView)v.findViewById(R.id.memo_detail_date_button);
        mDetailDateBtn.setEnabled(true);//设置按钮可点
        mDetailDateBtn.setText(android.text.format.DateFormat.format("yyyy.MM.dd", getTime()));
        mDetailDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 11);
                fragment.setTargetFragment(MemoDetailFragment.this, 11);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MemoDatePicker");
            }
        });

        mDetailContentET = (EditText)v.findViewById(R.id.memo_detail_content_edittext);
        return  v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == 11) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            memo.setmDate(date);
            updateDate();
        }
    }

    //更新时间设置
    private void updateDate(){
        mDetailDateBtn.setText(android.text.format.DateFormat.format("yyyy.MM.dd", memo.getmDate()));
    }

}
