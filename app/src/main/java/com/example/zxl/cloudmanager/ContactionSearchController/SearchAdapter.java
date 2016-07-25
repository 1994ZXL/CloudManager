package com.example.zxl.cloudmanager.ContactionSearchController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.EmployerContaction;

import java.util.List;

/**
 * Created by ZQQ on 2016/7/25.
 */
public class SearchAdapter extends ArrayAdapter<EmployerContaction> {
    private int resource;

    public SearchAdapter(Context context, int resource,
                                List<EmployerContaction> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmployerContaction contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource,
                    null);
        }
        TextView tv_contact_name = (TextView) convertView
                .findViewById(R.id.tv_contact_name);
        tv_contact_name.setText(contact.getName());
        return convertView;
    }
}
