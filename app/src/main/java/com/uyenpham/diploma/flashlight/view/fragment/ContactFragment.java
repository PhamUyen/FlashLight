package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.view.activity.SettingPatternFlashActivity;
import com.uyenpham.diploma.flashlight.view.adapter.ContactAdapter;
import com.uyenpham.diploma.flashlight.view.adapter.IRecycleListener;

import java.util.ArrayList;

/**
 * Created by Ka on 2/4/2018.
 */

public class ContactFragment extends android.support.v4.app.Fragment implements IRecycleListener ,SearchView.OnQueryTextListener{
    private ArrayList<Contact> listContact;
    private RecyclerView rcvContact;
    private ContactAdapter adapter;
    private RelativeLayout rltNotFound;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_contact, null);
        rcvContact = view.findViewById(R.id.rcvContact);
        rltNotFound = view.findViewById(R.id.rltNotFound);
        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.onActionViewCollapsed();
                setAdapter(listContact,getActivity());
                return true;
            }
        });
        initData();
        getContactList();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        listContact.clear();
        listContact = FlashlightApplication.getInstance().getDatabase().getAllContact();
        setAdapter(listContact,getActivity());
    }
    private void initData() {
        listContact = new ArrayList<>();
        adapter = new ContactAdapter(listContact, getActivity());
        adapter.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvContact.setLayoutManager(linearLayoutManager);
        rcvContact.setHasFixedSize(true);
        rcvContact.setAdapter(adapter);
    }

    private void getContactList() {
        listContact = FlashlightApplication.getInstance().getDatabase().getAllContact();
        if(listContact.size() >0){
            rltNotFound.setVisibility(View.GONE);
            rcvContact.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }else {
            rltNotFound.setVisibility(View.VISIBLE);
            rcvContact.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.KEY_CONTACT, adapter.getListContact().get(position));
        bundle.putInt(Const.KEY_TYPE,Const.TYPE_CONTACT);
        Intent intent = new Intent(getActivity(), SettingPatternFlashActivity.class);
        intent.putExtra(Const.KEY_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toString().toLowerCase();

        ArrayList<Contact>filteredList = new ArrayList<>();

        for (int i = 0; i < listContact.size(); i++) {

            final String text = listContact.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredList.add(listContact.get(i));
            }
        }
        setAdapter(filteredList, getActivity());
        return true;
    }
    private void setAdapter(ArrayList<Contact> list, Context context) {
        adapter = new ContactAdapter(list, context);
        rcvContact.setAdapter(adapter);
        adapter.setListener(this);
    }
}
