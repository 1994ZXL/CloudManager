package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.zxl.cloudmanager.model.Mission;
import com.example.zxl.cloudmanager.model.User;

/**
 * Created by ZXL on 2016/7/18.
 */
public class Edit extends Fragment{
    private EditText mEdit;
    private TextView mEditTextView;
    private static String EXTRA_VALUE = "message";
    private static String EXTRA_TITLE = "title";
    private Toolbar toolbar;

    private String value;
    private String title;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static Edit newInstance(String title, String value) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_VALUE, value);
        args.putSerializable(EXTRA_TITLE, title);

        Edit fragment = new Edit();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.my_message_edit, parent, false);

        title = (String) getArguments().getSerializable(EXTRA_TITLE);
        getActivity().getActionBar().setTitle(title);

        value = (String) getArguments().getSerializable(EXTRA_VALUE);
        mEdit = (EditText) view.findViewById(R.id.my_message_editText);
        mEdit.setText(value);
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeValue(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void changeValue(CharSequence charSequence) {
        String title = (String) getArguments().getSerializable(EXTRA_TITLE);
        if (title == "名字修改") {
//            User.newInstance(mFragment.getActivity()).setUser_name(charSequence.toString());
//        } else if (title == "手机修改") {
//            User.newInstance(mFragment.getActivity()).setPhone(charSequence.toString());
//        } else if (title == "QQ修改") {
//            User.newInstance(mFragment.getActivity()).setQq(charSequence.toString());
//        } else if (title == "微信修改") {
//            User.newInstance(mFragment.getActivity()).setWechat(charSequence.toString());
//        } else if (title == "地址修改") {
//            User.newInstance(mFragment.getActivity()).setAddress(charSequence.toString());
        } else if (title == "任务内容修改") {
            Mission.newInstance(mFragment.getActivity()).setContent(charSequence.toString());
        } else if (title == "内容详情修改") {
            Mission.newInstance(mFragment.getActivity()).setDetailContent(charSequence.toString());
        } else if (title == "任务人员修改") {
            Mission.newInstance(mFragment.getActivity()).setMissionWorker(charSequence.toString());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_message:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
