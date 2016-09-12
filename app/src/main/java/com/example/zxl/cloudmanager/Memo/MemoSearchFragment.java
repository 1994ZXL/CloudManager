package com.example.zxl.cloudmanager.Memo;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoSearchFragment extends Fragment {
    private static final String TAG = "MemoSearchFragment";

    private EditText mMemoTitle;
    private TextView mBeginTime;
    private TextView mEndTime;
    private Button mSearchBtn;
    private TextView mBack;

    private String title;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memo_search, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v){
        mMemoTitle = (EditText) v.findViewById(R.id.my_memo_search_title);
        mBeginTime = (TextView) v.findViewById(R.id.my_memo_search_begin_time);
        mEndTime = (TextView) v.findViewById(R.id.my_memo_search_end_time);
        mSearchBtn = (Button) v.findViewById(R.id.my_memo_search_button);
        mBack = (TextView) v.findViewById(R.id.my_memo_search_back);
    }

    private void control() {
        mMemoTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mBeginTime);
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mEndTime);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MemoFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.title, title);

                if (null != mBeginTime.getText())
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mBeginTime.getText().toString()));
                else bundle.putInt(Link.start_time, -1);

                if (null != mEndTime.getText())
                    bundle.putInt(Link.my_note_over_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mEndTime.getText().toString()));
                else bundle.putInt(Link.my_note_over_time, -1);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });
    }
}
