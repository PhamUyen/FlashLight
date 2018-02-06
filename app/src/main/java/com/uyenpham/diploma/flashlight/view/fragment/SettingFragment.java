package com.uyenpham.diploma.flashlight.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uyenpham.diploma.flashlight.R;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_setting, null);
        return view;
    }
}
