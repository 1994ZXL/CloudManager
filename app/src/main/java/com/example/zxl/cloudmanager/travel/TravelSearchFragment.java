package com.example.zxl.cloudmanager.travel;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxl.cloudmanager.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class TravelSearchFragment extends Fragment {


    public TravelSearchFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TravelSearchFragment newInstance(int columnCount) {
        TravelSearchFragment fragment = new TravelSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelsearch, container, false);


        return view;
    }


}
