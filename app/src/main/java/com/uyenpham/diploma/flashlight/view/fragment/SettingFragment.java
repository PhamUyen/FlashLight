package com.uyenpham.diploma.flashlight.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uyenpham.diploma.flashlight.R;

public class SettingFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_setting, null);
        view.findViewById(R.id.rltBlink).setOnClickListener(this);
        view.findViewById(R.id.rltPower).setOnClickListener(this);
        view.findViewById(R.id.rltBattery).setOnClickListener(this);
        view.findViewById(R.id.rltNightMode).setOnClickListener(this);
        view.findViewById(R.id.rltCall).setOnClickListener(this);
        view.findViewById(R.id.rltSMS).setOnClickListener(this);
        view.findViewById(R.id.rltNoti).setOnClickListener(this);
        view.findViewById(R.id.rltDefault).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
