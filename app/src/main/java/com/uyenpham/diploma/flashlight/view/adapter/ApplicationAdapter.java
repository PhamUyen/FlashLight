package com.uyenpham.diploma.flashlight.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;

import java.util.ArrayList;

/**
 * Created by Ka on 2/4/2018.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ReHolder> {
    private ArrayList<App> listApp;
    private IRecycleListener listener;
    private Context context;

    public ApplicationAdapter(ArrayList<App> listApp, Context context) {
        this.listApp = listApp;
        this.context = context;
    }

    public void setListener(IRecycleListener listener) {
        this.listener = listener;
    }

    @Override
    public ApplicationAdapter.ReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,
                parent, false);
        return new ApplicationAdapter.ReHolder(root);
    }

    @Override
    public void onBindViewHolder(ApplicationAdapter.ReHolder holder, final int position) {
        App app = listApp.get(position);
        holder.tvName.setText(app.getName());
        if (app.isFlash() ==1) {
            holder.imvStatus.setImageResource(R.mipmap.flash_on);
            holder.tvNumber.setText(""+app.getPatternFlash());
        } else {
            holder.imvStatus.setImageResource(R.mipmap.flash_off);
            holder.tvNumber.setText(context.getResources().getString(R.string.txt_none));
        }
        holder.imvIconApp.setImageBitmap(app.getIcon());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listApp.size();
    }

    class ReHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        ImageView imvStatus;
        RelativeLayout item;
        ImageView imvIconApp;

        public ReHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            imvStatus = itemView.findViewById(R.id.imvStatus);
            item = itemView.findViewById(R.id.itemContact);
            imvIconApp = itemView.findViewById(R.id.imvProfile);
        }
    }
}
