package com.example.zxl.cloudmanager.Memo;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoSearchFragment extends Fragment {

    private EditText mMemoTitle;
    private TextView mBeginTimeBtn;
    private TextView mEndTimeBtn;

    private Button mSearchBtn;

    public MemoSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memo_search, container, false);

        init(v);
        return v;
    }

    private void init(View v){
        mMemoTitle = (EditText) v.findViewById(R.id.my_memo_title_edittext);
//        mBeginTimeBtn = (TextView) v.findViewById(R.id.memo_submit_begin_time_button);
//        mEndTimeBtn = (TextView) v.findViewById(R.id.memo_submit_end_time_button);

        mSearchBtn = (Button) v.findViewById(R.id.my_memo_search_button);
    }
}
