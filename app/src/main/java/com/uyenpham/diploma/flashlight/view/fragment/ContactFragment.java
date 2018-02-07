package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ContactFragment extends android.support.v4.app.Fragment implements IRecycleListener {
    private ArrayList<Contact> listContact;
    private RecyclerView rcvContact;
    private ContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_contact, null);
        rcvContact = view.findViewById(R.id.rcvContact);
        initData();
        getContactList();
        return view;
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.KEY_CONTACT, listContact.get(position));
        bundle.putInt(Const.KEY_TYPE,Const.TYPE_CONTACT);
        Intent intent = new Intent(getActivity(), SettingPatternFlashActivity.class);
        intent.putExtra(Const.KEY_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
