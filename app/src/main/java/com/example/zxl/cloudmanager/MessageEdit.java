package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.example.zxl.cloudmanager.model.Memo;
import com.example.zxl.cloudmanager.model.User;

/**
 * Created by ZXL on 2016/7/18.
 */
public class MessageEdit extends Fragment{
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

    public static MessageEdit newInstance(String title, String value) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_VALUE, value);
        args.putSerializable(EXTRA_TITLE, title);

        MessageEdit fragment = new MessageEdit();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.my_message_edit, parent, false);

        title = (String) getArguments().getSerializable(EXTRA_TITLE);
        getActivity().getActionBar().setTitle(title);

//        toolbar = (Toolbar) view.findViewById(R.id.my_message_edit_toolbar);
        value = (String) getArguments().getSerializable(EXTRA_VALUE);
        mEdit = (EditText) view.findViewById(R.id.my_message_editText);
        mEdit.setText(value);
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                User.newInstance(mFragment.getActivity()).setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        mEditTextView = (TextView) view.findViewById(R.id.toolbar_edit);
//        mEditTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.popBackStack();
//            }
//        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_edit, menu);
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
