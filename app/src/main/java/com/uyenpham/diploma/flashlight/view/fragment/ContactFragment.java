package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
//        rcvContact.addItemDecoration(new RecyclerView.ItemDecoration() ;
        rcvContact.setAdapter(adapter);
    }

    private void getContactList() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Contact contact = new Contact(name, phoneNo, 0, 0, 1, 1);
                        listContact.add(contact);
                        adapter.notifyDataSetChanged();
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
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
