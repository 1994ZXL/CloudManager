package com.example.zxl.cloudmanager.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZXL on 2016/9/26.
 */
public abstract class CustomRecyclerAdapter<T> extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolderHelper> implements View.OnClickListener {
    private List<T> mData;
    private Context mContext;
    protected int mLayoutResId;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CustomRecyclerAdapter(Context context, List<T> data, int layoutResId) {
        mData = data;
        mContext = context;
        mLayoutResId = layoutResId;
    }

    @Override
    public ViewHolderHelper onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutResId, viewGroup, false);
        ViewHolderHelper viewHolder = new ViewHolderHelper(v);
        v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerAdapter.ViewHolderHelper holder, int position) {
        T data = mData.get(position);
        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener(this);
        display(holder, data);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    protected abstract void display(ViewHolderHelper viewHolder, T data);

    public class ViewHolderHelper extends RecyclerView.ViewHolder {
        private SparseArray<View> views;

        public ViewHolderHelper(View v) {
            super(v);
            views = new SparseArray<View>();
        }

        private <T extends View> T converToViewFromId(int resId) {
            View view = views.get(resId);
            if (null == view) {
                view = itemView.findViewById(resId);
            }
            views.put(resId, view);
            return (T)view;
        }

        public ViewHolderHelper setText(int resId, String value) {
            TextView itemView = converToViewFromId(resId);
            if (TextUtils.isEmpty(value)) {
                itemView.setText("");
            } else {
                itemView.setText(value);
            }
            return this;
        }

        public ViewHolderHelper setImageButton(int resId, View.OnClickListener listener) {
            ImageButton view = converToViewFromId(resId);
            view.setOnClickListener(listener);
            return this;
        }

        public ViewHolderHelper setImageButtonGone(int resId) {
            ImageButton view = converToViewFromId(resId);
            view.setVisibility(View.GONE);
            return this;
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }
}
