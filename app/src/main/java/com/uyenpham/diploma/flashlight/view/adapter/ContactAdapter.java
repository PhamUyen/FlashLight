package com.uyenpham.diploma.flashlight.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.Contact;

import java.util.ArrayList;

/**
 * Created by Ka on 2/4/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ReHolder> {
    private ArrayList<Contact> listContact;
    private Context context;
    private IRecycleListener listener;

    public ContactAdapter(ArrayList<Contact> arrResult, Context context) {
        this.listContact = arrResult;
        this.context = context;
    }

    public void setListener(IRecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public ReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,
                parent, false);
        return new ReHolder(root);
    }

    @Override
    public void onBindViewHolder(ReHolder holder, final int position) {
        Contact contact = listContact.get(position);
        holder.tvNumber.setText(contact.getNumber());
        holder.tvName.setText(contact.getName());
        if(contact.isFlashCall() || contact.isFlashSMS()){
            holder.imvStatus.setImageResource(R.mipmap.flash_on);
        }else {
            holder.imvStatus.setImageResource(R.mipmap.flash_off);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onClick(view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listContact.size();
    }

    class ReHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        ImageView imvStatus;
        RelativeLayout item;

        public ReHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            imvStatus = itemView.findViewById(R.id.imvStatus);
            item = itemView.findViewById(R.id.itemContact);
        }
    }
}
