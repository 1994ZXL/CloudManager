package com.example.zxl.cloudmanager.publicSearch.PSAddressBook;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSearchFragment extends Fragment {

    private EditText mProjectNameET;
    private EditText mCustomerNameET;
    private Spinner mCustomerSexSpinner;
    private EditText mCustomerPhoneET;
    private EditText mCustomerQqET;
    private EditText mCustomerWechatET;

    private Button mSearchBtn;

    private ArrayAdapter<String> adapter;
    private static final String[] list={"男", "女"};

    public ListSearchFragment() {
        // Required empty public constructor
    }
    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_project_list, container, false);
        init(v);

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCustomerSexSpinner.setAdapter(adapter);
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new PSListDetailFragment();
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

        return  v;
    }

    private void init(View v){
        mProjectNameET = (EditText) v.findViewById(R.id.list_project_name_edittext);
        mCustomerNameET= (EditText) v.findViewById(R.id.list_customer_name_EditText);
        mCustomerPhoneET = (EditText) v.findViewById(R.id.list_customer_phone_editText);
        mCustomerQqET = (EditText) v.findViewById(R.id.list_cutomer_qq_editText);
        mCustomerWechatET = (EditText) v.findViewById(R.id.list_customer_wechat_editText);
        mCustomerSexSpinner = (Spinner) v.findViewById(R.id.list_customer_sex_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.project_list_check_button);
    }
}
