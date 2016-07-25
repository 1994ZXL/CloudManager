package com.example.zxl.cloudmanager.ContactionSearchController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.EmployerContaction;

import java.util.List;

/**
 * Created by ZQQ on 2016/7/25.
 */
public class ContactionAdapter extends ArrayAdapter<EmployerContaction> {

    private SectionIndexer mIndexer;
    private int resource;

    public ContactionAdapter(Context context, int resource, List<EmployerContaction> objects,
                             SectionIndexer mIndexer) {
        super(context, resource, objects);
        this.resource = resource;
        this.mIndexer = mIndexer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmployerContaction contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource,
                    null);
        }
        TextView tv_index = (TextView) convertView.findViewById(R.id.tv_index);
        int sectionIndex = mIndexer.getSectionForPosition(position);
        int positionIndex = mIndexer.getPositionForSection(sectionIndex);

        if (position == positionIndex) {
            tv_index.setVisibility(View.VISIBLE);
            tv_index.setText(contact.getPhonebookLabel());
        } else {
            tv_index.setVisibility(View.GONE);
        }

        TextView tv_contact_name = (TextView) convertView
                .findViewById(R.id.tv_contact_name);
        tv_contact_name.setText(contact.getName());
        return convertView;
    }
}
