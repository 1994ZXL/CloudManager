package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private ImageView memoImage;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment, parent, false);

        init(v);
        clickListener();


        return v;
    }

    private void init(View v) {
        memoImage = (ImageView)v.findViewById(R.id.main_fragment_my_memo_image);

    }

    private void clickListener() {
        memoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "memImage被点击");
                Fragment fragment = new MemoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContiner, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
