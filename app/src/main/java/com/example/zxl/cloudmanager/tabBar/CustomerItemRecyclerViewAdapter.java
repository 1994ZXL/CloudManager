package com.example.zxl.cloudmanager.tabBar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.tabBar.dummy.CustomerLab.DummyItem;

import java.util.List;

public class CustomerItemRecyclerViewAdapter extends RecyclerView.Adapter<CustomerItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final CustomerItemFragment.OnListFragmentInteractionListener mListener;

    public CustomerItemRecyclerViewAdapter(List<DummyItem> items, CustomerItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_customeritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mCustomerView.setText(mValues.get(position).content);
        holder.mTelephoneView.setText(mValues.get(position).telephone);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mCustomerView;
        public final TextView mTelephoneView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.customer_id);
            mCustomerView = (TextView) view.findViewById(R.id.customer_name);
            mTelephoneView = (TextView) view.findViewById(R.id.customer_phone_number);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCustomerView.getText() + "'";
        }
    }
}
