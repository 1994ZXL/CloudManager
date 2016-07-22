package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxl.cloudmanager.leave.LeaveSearchFragment;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.UseCase;
import com.example.zxl.cloudmanager.model.UseCaseLab;
import com.example.zxl.cloudmanager.model.UserLab;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyUseCaseFragment extends Fragment {
    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private ArrayList<UseCase> useCases = new ArrayList<UseCase>();
    private MyAdapter myAdapter;

    private Fragment mFragment;
    private Button mSearchBtn;

    private static final String SEARCH_KEY = "search_key";
    private static final String TAG = "MyUseCaseFragment";
    private int searchKey;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_usecase, parent, false);

        getActivity().getActionBar().setTitle("我的用例");

        saveInstanceState = getArguments();
        if (null == saveInstanceState) {
            searchKey = -1;
        } else {
            searchKey = getArguments().getInt(SEARCH_KEY);
        }

        if (-1 == searchKey) {
            useCases = UseCaseLab.newInstance(mFragment.getActivity()).getUseCase();
        } else {
            useCases.add(UseCaseLab.newInstance(mFragment.getActivity()).getUseCase().get(searchKey));
        }

        Log.d(TAG, "searchKey: " + searchKey);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.usecase_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(this.getActivity(), useCases);
        mRecyclerView.setAdapter(myAdapter);
        mCardView = (CardView)v.findViewById(R.id.fragment_my_usecase);
        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Fragment fragment = MyUseCaseDetailFragment.newInstance(data);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });

        mSearchBtn =(Button) v.findViewById(R.id.my_usecase_search_button) ;
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment = new UsecaseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });
        return v;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<UseCase> useCases;
        private Context mContext;

        public MyAdapter (Context context, List<UseCase> useCases) {
            this.useCases = useCases;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.usecase_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            UseCase useCase = useCases.get(i);

            viewHolder.mProjectName.setText(useCase.getName());
            viewHolder.mUseCaseNumber.setText(useCase.getUsecasetNumber());
            viewHolder.mVersoinNumber.setText(useCase.getVersionNumber());
            viewHolder.mTextMan.setText(useCase.getTestMan());
            viewHolder.mExploitMan.setText(useCase.getExploitMan());
            viewHolder.mAutorizedTime.setText(useCase.getAutorizedTime());

            viewHolder.itemView.setTag(useCases.get(i));
        }

        @Override
        public int getItemCount() {
            return useCases == null ? 0 : useCases.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mProjectName;
            public TextView mUseCaseNumber;
            public TextView mVersoinNumber;
            public TextView mTextMan;
            public TextView mExploitMan;
            public TextView mAutorizedTime;

            public ViewHolder(View v) {
                super(v);
                mProjectName = (TextView)v.findViewById(R.id.usecase_card_item_project_soft);
                mUseCaseNumber = (TextView)v.findViewById(R.id.usecase_card_item_usecase_number);
                mVersoinNumber = (TextView)v.findViewById(R.id.usecase_card_item_version_number);
                mTextMan = (TextView)v.findViewById(R.id.usecase_card_item_text_man);
                mExploitMan = (TextView)v.findViewById(R.id.usecase_card_item_exploit_man);
                mAutorizedTime = (TextView)v.findViewById(R.id.usecase_card_item_autorized_time);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
