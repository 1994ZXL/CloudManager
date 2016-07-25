package com.example.zxl.cloudmanager.tabBar;


import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.zxl.cloudmanager.ContactionSearchController.ContactionAdapter;
import com.example.zxl.cloudmanager.ContactionSearchController.PinyinUtil;
import com.example.zxl.cloudmanager.ContactionSearchController.QuickIndexBar;
import com.example.zxl.cloudmanager.ContactionSearchController.SearchAdapter;
import com.example.zxl.cloudmanager.MainActivity;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.EmployerContaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyContactionFragment extends Fragment {

    private ListView lv_contact;
    private QuickIndexBar qib;
    private TextView tv_center;
    private EditText et_search;
    private TextView tv_no_contact;

    private ArrayAdapter<EmployerContaction> adapter;
    private ArrayAdapter<EmployerContaction> searchAdapter;
    private List<EmployerContaction> list = new ArrayList<EmployerContaction>();
    public static final Uri URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private SectionIndexer mIndexer;
    private SearchTask searchTask;

    /**
     * 定义字母表的排序规则
     */
    private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CompanyContactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_company_contaction, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        list.clear();
        lv_contact = (ListView) v.findViewById(R.id.lv_contact);
        qib = (QuickIndexBar) v.findViewById(R.id.qib);
        tv_center = (TextView) v.findViewById(R.id.tv_center);
        et_search = (EditText) v.findViewById(R.id.et_search);
        tv_no_contact = (TextView) v.findViewById(R.id.tv_no_contact);
        getContactArray();
        adapter = new ContactionAdapter(getActivity(), R.layout.list_item,
                list, mIndexer);
        searchAdapter = new SearchAdapter(getActivity(),
                R.layout.list_item, filterList);
        lv_contact.setAdapter(adapter);
        qib.setOnIndexChangeListener(new QuickIndexBar.OnIndexChangeListener() {
            @Override
            public void onIndexChange(int section) {
                int position = mIndexer.getPositionForSection(section);
                lv_contact.setSelection(position);
                tv_center.setText(QuickIndexBar.INDEX_ARRAYS[section]);
                tv_center.setVisibility(View.VISIBLE);
            }

            @Override
            public void onActionUp() {
                tv_center.setVisibility(View.GONE);
            }
        });
        // 文本编辑框内容改变时
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = s.toString().trim()
                        .toLowerCase(Locale.US);
                if (searchTask != null
                        && searchTask.getStatus() != AsyncTask.Status.FINISHED) {
                    // 若之前有task，先取消搜索task
                    searchTask.cancel(true);
                }
                // 开启搜索task
                searchTask = new SearchTask();
                searchTask.execute(searchString);
            }
        });
    }

    // 搜索出的联系人列表
    List<EmployerContaction> filterList = new ArrayList<EmployerContaction>();
    // 是否为搜索模式
    boolean searchMode;

    class SearchTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            filterList.clear();
            searchMode = params[0].length() > 0;
            if (searchMode) {
                for (EmployerContaction contact : list) {
                    boolean isChinese = contact.getName()
                            .toLowerCase(Locale.US).indexOf(params[0]) > -1;
                    boolean isPinyin = contact.getPinyin().indexOf(
                            params[0]) > -1;
                    if (isPinyin || isChinese) {
                        filterList.add(contact);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (searchMode) {
                if (filterList.size() > 0) {
                    lv_contact.setAdapter(searchAdapter);
                } else {
                    lv_contact.setVisibility(View.GONE);
                    tv_no_contact.setVisibility(View.VISIBLE);
                }
                qib.setVisibility(View.GONE);
            } else {
                tv_no_contact.setVisibility(View.GONE);
                lv_contact.setVisibility(View.VISIBLE);
                lv_contact.setAdapter(adapter);
                qib.setVisibility(View.VISIBLE);
            }
        }

    }

    private List<EmployerContaction> getContactArray() {
        Cursor cursor = this.getActivity().getContentResolver().query(URI,
                new String[] { "display_name", "sort_key", "phonebook_label" },
                null, null, "phonebook_label");
        EmployerContaction contact;
        if (cursor.moveToFirst()) {
            do {
                contact = new EmployerContaction();
                String contact_name = cursor.getString(0);
                String phonebook_label = cursor.getString(2);
                contact.setPhonebookLabel(getPhonebookLabel(phonebook_label));
                contact.setPinyin(PinyinUtil.getPinYin(contact_name));
                contact.setName(contact_name);
                list.add(contact);
            } while (cursor.moveToNext());
        }
        // 实例化indexer
        mIndexer = new AlphabetIndexer(cursor, 2, alphabet);
        return list;
    }

    private String getPhonebookLabel(String phonebook_label) {
        if (phonebook_label.matches("[A-Z]")) {
            return phonebook_label;
        }
        return "#";
    }
}
