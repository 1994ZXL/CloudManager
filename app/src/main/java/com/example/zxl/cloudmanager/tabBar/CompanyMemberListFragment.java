package com.example.zxl.cloudmanager.tabBar;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyMemberListFragment extends Fragment {


    public CompanyMemberListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_member_list, container, false);
    }

}