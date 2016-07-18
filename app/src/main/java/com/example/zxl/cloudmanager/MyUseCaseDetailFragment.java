package com.example.zxl.cloudmanager;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.UseCase;
import com.example.zxl.cloudmanager.model.UseCaseLab;

import java.util.ArrayList;


/**
 * Created by ZXL on 2016/7/13.
 */
public class MyUseCaseDetailFragment extends ListFragment {

    private ArrayList<UseCase> useCases;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        useCases = UseCaseLab.newInstance(getActivity()).getUseCase();
        UseCaseAdapter adapter = new UseCaseAdapter(useCases);
        setListAdapter(adapter);
    }

    private class UseCaseAdapter extends ArrayAdapter<UseCase> {

        public UseCaseAdapter(ArrayList<UseCase> useCases){
            super(getActivity(), 0, useCases);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.usecase_details, null);
            }

            UseCase useCase = getItem(position);

            TextView key = (TextView) convertView.findViewById(R.id.usecase_details_key);
            key.setText(useCase.getTitle());

            TextView value = (TextView) convertView.findViewById(R.id.usecase_details_value);
            value.setText(useCase.getContent());

            return convertView;
        }
    }
}
