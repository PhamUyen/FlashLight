package com.uyenpham.diploma.flashlight.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

public class SettingFragment extends Fragment implements View.OnClickListener {
    SwitchCompat swBlick, swBettery, swnight, swPower, swCall, swSMS, swNoti, swDefault;
    private LinearLayout lnBattery;
    private LinearLayout lnSetTime;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_setting, null);

        initView(view);
        return view;
    }

    private void initView(View view){
        view.findViewById(R.id.rltBlink).setOnClickListener(this);
        view.findViewById(R.id.rltPower).setOnClickListener(this);
        view.findViewById(R.id.rltBattery).setOnClickListener(this);
        view.findViewById(R.id.rltNightMode).setOnClickListener(this);
        view.findViewById(R.id.rltCall).setOnClickListener(this);
        view.findViewById(R.id.rltSMS).setOnClickListener(this);
        view.findViewById(R.id.rltNoti).setOnClickListener(this);
        view.findViewById(R.id.rltDefault).setOnClickListener(this);

        swBettery = view.findViewById(R.id.swBattery);
        swBlick = view.findViewById(R.id.swBlink);
        swNoti = view.findViewById(R.id.swNoti);
        swCall = view.findViewById(R.id.swCall);
        swSMS = view.findViewById(R.id.swSMS);
        swnight = view.findViewById(R.id.swNight);
        swPower = view.findViewById(R.id.swPower);
        swDefault = view.findViewById(R.id.swDefault);
        lnBattery = view.findViewById(R.id.lnSeekbar);
        lnSetTime = view.findViewById(R.id.lnsetTime);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rltBattery:
                if(swBettery.isChecked()){
                    swBettery.setChecked(false);
                    lnBattery.setVisibility(View.GONE);
                }else {
                    lnBattery.setVisibility(View.VISIBLE);
                    swBettery.setChecked(true);
                }
                break;
            case R.id.rltBlink:
                if(swBettery.isChecked()){
                    swBettery.setChecked(false);
                }else {
                    swBettery.setChecked(true);
                }
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_FLASH,true);
                break;
            case R.id.rltPower:
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_TURN_BY_POWER,true);
                break;
            case R.id.rltNightMode:
                if(swnight.isChecked()){
                    swnight.setChecked(false);
                    lnSetTime.setVisibility(View.GONE);
                }else {
                    lnSetTime.setVisibility(View.VISIBLE);
                    swnight.setChecked(true);
                }
                break;
            case R.id.rltCall:
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_CALL,true);
                break;
            case R.id.rltSMS:
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_SMS,true);
                break;
            case R.id.rltNoti:
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_Noti,true);
                break;
            case R.id.rltDefault:
                PreferenceUtils.saveBoolean(getActivity(), Const.KEY_SET_DEFAULT,true);
                break;
            default:
                break;
        }
    }
}
