package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;
import com.uyenpham.diploma.flashlight.view.activity.MapActivity;
import com.uyenpham.diploma.flashlight.view.customview.CustomTimeDialog;
import com.uyenpham.diploma.flashlight.view.customview.TextSeekbarView;

import static com.uyenpham.diploma.flashlight.utils.Const.KEY_TIME_DELAY;

/**
 * Created by Ka on 2/3/2018.
 */

public class SwitchFlashFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View
        .OnClickListener {
    private SeekBar seekBar;
    private TextSeekbarView textSeekbarView;
    private int progress;
    private int blinkDelay =0;
    private ImageView btnSwitch;
    public static boolean isFlash =false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_main, null);

        seekBar = view.findViewById(R.id.seekbar);
        textSeekbarView = view.findViewById(R.id.seekbarText);
        btnSwitch = view.findViewById(R.id.button_switch);
        view.findViewById(R.id.rltCompass).setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);
        btnSwitch.setOnClickListener(this);
        view.findViewById(R.id.btnSetting).setOnClickListener(this);
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        progress = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setProgress(convertProgress(progress) * 10);
        textSeekbarView.setEnableText(convertProgress(progress));
        blinkDelay =1000 / convertProgress(progress);
        turnOnFlash(blinkDelay);
    }

    private int convertProgress(int progress) {
        if (progress > 0 && progress <= 10) {
            return 1;
        } else if (progress > 10 && progress <= 20) {
            return 2;
        } else if (progress > 20 && progress <= 30) {
            return 3;
        } else if (progress > 30 && progress <= 40) {
            return 4;
        } else if (progress > 40 && progress <= 50) {
            return 5;
        } else if (progress > 50 && progress <= 60) {
            return 6;
        } else if (progress > 60 && progress <= 70) {
            return 7;
        } else if (progress > 70 && progress <= 80) {
            return 8;
        } else if (progress > 80 && progress <= 90) {
            return 9;
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_switch:
                if(isFlash){
                    FlashUtil.stopFlickerFlash();
                    btnSwitch.setImageResource(R.mipmap.switch_off);
                }else {
                    turnOnFlash(blinkDelay);
                }
                break;
            case R.id.btnSetting:
                final CustomTimeDialog customDialog = new CustomTimeDialog(getActivity());
                customDialog.setListener(new CustomTimeDialog.OnOKClickListener() {
                    @Override
                    public void onClick(String tag) {
                        Toast.makeText(getActivity(), tag, Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveInt(getActivity(),KEY_TIME_DELAY,convertTimeDelay(tag));
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                break;
            case R.id.rltCompass:
                startActivity(new Intent(getActivity(), MapActivity.class));
                break;
            default:
                break;
        }
    }

    private void turnOnFlash(int delay){
        btnSwitch.setImageResource(R.mipmap.switch_on);
        if(blinkDelay == 0){
            FlashUtil.flashOn();
        }else {
            FlashUtil.setBlinkDelay(delay);
            FlashUtil.flickerFlash(getActivity());
        }
        setTimeOff();
    }
    private int convertTimeDelay(String timeStr){
        int time;
        if(compareString(timeStr,"10s")){
            time = 10*1000;
        }else if(compareString(timeStr, "20s")){
            time = 20*1000;
        }else if(compareString(timeStr, "1m")){
            time = 60*1000;
        }else if(compareString(timeStr, "3m")){
            time =3*60*1000;
        }else if(compareString(timeStr, "5m")){
            time = 5*60*1000;
        }else if(compareString(timeStr, "10m")){
            time = 10*60*1000;
        }else if(compareString(timeStr, "30m")){
            time =30*60*1000;
        }else {
            time =0;
        }
        return time;
    }
    private   boolean compareString(String str1, String str2){
        return str1.equalsIgnoreCase(str2);
    }

    private  void setTimeOff() {
        int delay = PreferenceUtils.getInt(getActivity(), KEY_TIME_DELAY, 0);
        if (delay != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FlashUtil.stopFlickerFlash();
                    btnSwitch.setImageResource(R.mipmap.switch_off);
                }
            }, delay);
        }
    }
}
