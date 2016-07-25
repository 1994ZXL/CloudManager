package com.example.zxl.cloudmanager.checkManager.travel;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class ManagerTravelListFragment extends ListFragment {
    private ArrayList<Travel> travels;
    private Button mSearchBtn;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        travels = TravelLab.newInstance(getActivity()).getTravels();
        TravelAdapter adapter = new TravelAdapter(travels);
        setListAdapter(adapter);
    }

    private class TravelAdapter extends ArrayAdapter<Travel> {

        public TravelAdapter(ArrayList<Travel> travels){
            super(getActivity(), 0, travels);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_travel, null);
                mSearchBtn = (Button) convertView.findViewById(R.id.my_travel_list_search_button);
                mSearchBtn.setVisibility(View.INVISIBLE);
            }

            Travel travel = getItem(position);

            TextView name = (TextView) convertView.findViewById(R.id.main_fragment_travel_name);
            name.setText(travel.getName());

            TextView time = (TextView) convertView.findViewById(R.id.main_fragment_travel_beginTime);
            time.setText(travel.getBeginTime());

            TextView state = (TextView) convertView.findViewById(R.id.main_fragment_travel_state);
            state.setText(travel.getTravelState());

            return convertView;
        }
    }
}
