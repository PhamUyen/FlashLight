package com.uyenpham.diploma.flashlight.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;

import java.util.ArrayList;

import static com.uyenpham.diploma.flashlight.view.fragment.SwitchFlashFragment.isFlash;

/**
 * Created by Ka on 2/4/2018.
 */

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.ReHolder> {
    private ArrayList<FlashPatternt> listPattern;
    private Context context;
    private IPatterntListener listener;

    public PatternAdapter(ArrayList<FlashPatternt> arrResult, Context context) {
        this.listPattern = arrResult;
        this.context = context;
    }

    public void setListener(IPatterntListener listener) {
        this.listener = listener;
    }

    @Override
    public ReHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pattern,
                parent, false);
        return new ReHolder(root);
    }

    @Override
    public void onBindViewHolder(final ReHolder holder, final int position) {
        final FlashPatternt patternt = listPattern.get(position);
        holder.tvName.setText(patternt.getName());
        holder.tvTime.setText(patternt.getTimeStr());
        holder.rbPattern.setChecked(patternt.getChecked() == 1);
        holder.imvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlash) {
                    FlashUtil.stopFlickerFlash();
                    holder.imvPlay.setImageResource(R.mipmap.play);
                } else {
                    tryFlash(patternt.getTime(), holder.imvPlay);
                }
            }
        });
        holder.viewRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onRadioChecked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPattern.size();
    }

    class ReHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvTime;
        ImageView imvPlay;
        RadioButton rbPattern;
        View viewRb;

        public ReHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNamePattern);
            tvTime = itemView.findViewById(R.id.tvTimePattern);
            imvPlay = itemView.findViewById(R.id.imvPlay);
            rbPattern = itemView.findViewById(R.id.rbPattern);
            viewRb = itemView.findViewById(R.id.viewRb);
        }
    }

    private void tryFlash(int time, final ImageView imv) {
        setTimeOff(time, imv);
        FlashUtil.flickerFlash(context);
        imv.setImageResource(R.mipmap.pause);
        isFlash = true;
    }

    private void setTimeOff(int time, final ImageView imv) {
        if (time != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FlashUtil.stopFlickerFlash();
                    imv.setImageResource(R.mipmap.play);
                }
            }, time);
        }
    }
    public void  setStateRadioButton(int position){
        for (FlashPatternt object : listPattern) {
            object.setChecked(0);
        }
        FlashPatternt patternt = listPattern.get(position);
        patternt.setChecked(1);
        notifyDataSetChanged();
    }

    public interface IPatterntListener {
        void onRadioChecked(int position);
    }
}
