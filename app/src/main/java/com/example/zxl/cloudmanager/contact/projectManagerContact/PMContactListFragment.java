package com.example.zxl.cloudmanager.contact.projectManagerContact;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.contact.publicSearchContact.PSContactActivity;
import com.example.zxl.cloudmanager.model.Contact;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.pulltorefresh.MyListener;
import com.example.zxl.cloudmanager.pulltorefresh.PullToRefreshLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/9/12.
 */
public class PMContactListFragment extends Fragment {
    private static final String TAG = "PMContactList";

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private List<Contact> contacts = new ArrayList<Contact>();
    private MyAdapter myAdapter;

    private Fragment mFragment;

    private static int mCurl_page;

    private PullToRefreshLayout mPullToRefreshLayout;

    private TextView mBack;
    private TextView mSearch;
    private Button mAdd;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private String url;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
        mCurl_page = 1;
    }

    private void loadDate(final Bundle saveInstanceState, int curl_page, final View v) {
        if (null != saveInstanceState) {
            try {
                if (null != saveInstanceState.getString(Link.contact_name))
                    keyObj.put(Link.contact_name, saveInstanceState.getString(Link.contact_name));
                if (null != saveInstanceState.getString(Link.contact_phone))
                    keyObj.put(Link.contact_phone, saveInstanceState.getInt(Link.contact_phone));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            keyObj.put(Link.pm_id, saveInstanceState.getString(Link.pm_id));
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            keyObj.put("sort", "project_name desc");
            keyObj.put("page_count", 20);
            keyObj.put("curl_page", curl_page);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        Log.d(TAG, "Url: " + url);

        mHttpc.post(Link.localhost + url, mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray array = rjo.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            contacts.add(new Contact(array.getJSONObject(i)));
                        }
                        Log.d(TAG, "contacts: " + contacts);

                        mRecyclerView = (RecyclerView) v.findViewById(R.id.pm_contact_list_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(mFragment.getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        myAdapter = new MyAdapter(mFragment.getActivity(), contacts);
                        mRecyclerView.setAdapter(myAdapter);
                        mCardView = (CardView) v.findViewById(R.id.pm_contact_card_item);
                        myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, Object data) {
                                Fragment fragment = PMContactDetailFragment.newInstance(data);

                                if (null != saveInstanceState) {
                                    fragment.setArguments(new Bundle(saveInstanceState));
                                }

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

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = layoutInflater.inflate(R.layout.pm_contact_list, parent, false);

        mAdd = (Button) v.findViewById(R.id.pm_contact_list_add);
        mBack = (TextView) v.findViewById(R.id.pm_contact_list_back);
        mSearch = (TextView) v.findViewById(R.id.pm_contact_list_search);

        if (mFragment.getActivity().getClass() == PMContactActivity.class)
            url = Link.pm_contact + Link.get_list;
        else if (mFragment.getActivity().getClass() == PSContactActivity.class) {
            url = Link.pmcontact + Link.get_list;
            mAdd.setVisibility(View.GONE);
        }

        savedInstanceState = getArguments();
        final Bundle saveInstanceState = savedInstanceState;

        loadDate(saveInstanceState, mCurl_page, v);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.pm_contact_list_refresh);
        mPullToRefreshLayout.setOnRefreshListener(new MyListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        contacts.clear();
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        contacts.clear();
                        mCurl_page++;
                        loadDate(saveInstanceState, mCurl_page, v);
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                if (null == fragment) {
                    FragmentManager fm = getFragmentManager();
                    fragment = new ContactSearchFragment();
                    fm.beginTransaction().replace(R.id.blankActivity, fragment).commit();
                }
            }
        });


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMContactDetailFragment();
                Bundle bundle = new Bundle(saveInstanceState);
                fragment.setArguments(bundle);
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
        private List<Contact> contacts;
        private Context mContext;

        public MyAdapter(Context context, List<Contact> contacts) {
            this.contacts = contacts;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pm_contact_card_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            Contact contact = contacts.get(i);

            viewHolder.mName.setText(contact.getContact_name());
            viewHolder.mPhone.setText(contact.getPhone());

            viewHolder.itemView.setTag(contacts.get(i));
        }

        @Override
        public int getItemCount() {
            return contacts == null ? 0 : contacts.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag());
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mName;
            private TextView mPhone;

            public ViewHolder(View v) {
                super(v);
                mName = (TextView) v.findViewById(R.id.pm_contact_card_item_name);
                mPhone = (TextView) v.findViewById(R.id.pm_contact_card_item_phone);
            }
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            mOnItemClickListener = listener;
        }
    }
}
