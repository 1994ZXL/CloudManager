package com.example.zxl.cloudmanager.Memo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.Memo.MemoDetailFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/7.
 */
public class MemoFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private List<Memo> memos = new ArrayList<Memo>();
    private String[] titles = {"第一条", "第二条", "第三条", "第四条", "第五条"};
    private String[] content = {"p1", "p2", "p3", "p4", "p5"};

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_memo, parent, false);

        memos.add(new Memo("第一条", "p1"));
        getActivity().getActionBar().setTitle("我的备忘录");

        mRecyclerView = (RecyclerView)v.findViewById(R.id.memo_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), memos);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_memo);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = new MemoDetailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragmentContiner, fragment);
                transaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.memo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_memo:
               if (myAdapter.getItemCount() != titles.length) {
                   memos.add(new Memo(titles[myAdapter.getItemCount()], content[myAdapter.getItemCount()]));
                   mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                   myAdapter.notifyDataSetChanged();
               }
                return true;
            case R.id.menu_item_delete_memo:
                if (myAdapter.getItemCount() != 0) {
                    memos.remove(myAdapter.getItemCount() -1);
                    mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                    myAdapter.notifyDataSetChanged();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<Memo> memos;
        private Context mContext;

        public MyAdapter (Context context, List<Memo> memos) {
            this.memos = memos;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_card_item, viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Memo m = memos.get(i);
            viewHolder.mTitle.setText(m.getmMemoTitle());
            viewHolder.mContent.setText(m.getmContent());
            viewHolder.itemView.setTag(memos.get(i));
        }

        @Override
        public int getItemCount() {
            return memos == null ? 0 : memos.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTitle;
            public TextView mContent;
            public ViewHolder(View v) {
                super(v);
                mTitle = (TextView)v.findViewById(R.id.memo_title_textview);
                mContent = (TextView)v.findViewById(R.id.memo_content_textview);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
