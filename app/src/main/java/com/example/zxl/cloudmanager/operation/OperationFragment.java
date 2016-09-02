package com.example.zxl.cloudmanager.operation;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zxl.cloudmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OperationFragment extends Fragment {

    private EditText mProjectName;
    private Button mSearchBtn;

    public OperationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_operation, container, false);

        init(v);
        return v;
    }
    private void init(View v){
        mProjectName = (EditText) v.findViewById(R.id.my_operation_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.my_operation_search_button);
    }
}
