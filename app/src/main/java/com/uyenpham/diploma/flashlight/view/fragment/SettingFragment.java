package com.uyenpham.diploma.flashlight.view.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import java.util.Calendar;

public class SettingFragment extends Fragment implements View.OnClickListener, SeekBar
        .OnSeekBarChangeListener {
    SwitchCompat swBlick, swBettery, swnight, swPower, swCall, swSMS, swNoti, swDefault;
    private LinearLayout lnBattery;
    private LinearLayout lnSetTime;
    private TextView tvTimeOFf, tvTimeOn;
    private TextView tvPercentBattery;
    private SeekBar slkbBattery;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_setting, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
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

        tvTimeOFf = view.findViewById(R.id.tvTimeOff);
        tvTimeOn = view.findViewById(R.id.tvTimeOn);
        tvPercentBattery = view.findViewById(R.id.tvPercentBattery);
        slkbBattery = view.findViewById(R.id.seekbarBattery);
        slkbBattery.setOnSeekBarChangeListener(this);
        tvPercentBattery.setText(slkbBattery.getProgress() + "%");
        tvTimeOn.setOnClickListener(this);
        tvTimeOFf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rltBattery:
                if (swBettery.isChecked()) {
                    swBettery.setChecked(false);
                    lnBattery.setVisibility(View.GONE);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_LOW_BATTERY, false);
                } else {
                    lnBattery.setVisibility(View.VISIBLE);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_LOW_BATTERY, true);
                    swBettery.setChecked(true);
                }
                break;
            case R.id.rltBlink:
                if (swBlick.isChecked()) {
                    swBlick.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_FLASH_WHEN_LOCK, false);
                } else {
                    swBlick.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_FLASH_WHEN_LOCK, true);
                }
                break;
            case R.id.rltPower:
                if (swPower.isChecked()) {
                    swPower.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_TURN_BY_POWER, false);
                } else {
                    swPower.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_TURN_BY_POWER, true);
                }
                break;
            case R.id.rltNightMode:
                if (swnight.isChecked()) {
                    swnight.setChecked(false);
                    lnSetTime.setVisibility(View.GONE);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_NIGHT_MODE, false);
                } else {
                    lnSetTime.setVisibility(View.VISIBLE);
                    swnight.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_NIGHT_MODE, true);
                }
                break;
            case R.id.rltCall:
                if (swCall.isChecked()) {
                    swCall.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_CALL, false);
                } else {
                    swCall.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_CALL, true);
                    ;
                }
                break;
            case R.id.rltSMS:
                if (swSMS.isChecked()) {
                    swSMS.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_SMS, false);
                } else {
                    swSMS.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_SMS, true);
                }
                break;
            case R.id.rltNoti:
                if (swNoti.isChecked()) {
                    swNoti.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_Noti, false);
                } else {
                    swNoti.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_ALLOW_FLASH_Noti, true);
                }
                break;
            case R.id.rltDefault:
                if (swDefault.isChecked()) {
                    swDefault.setChecked(false);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_SET_DEFAULT, false);
                } else {
                    swDefault.setChecked(true);
                    PreferenceUtils.saveBoolean(getActivity(), Const.KEY_SET_DEFAULT, true);
                }
                break;
            case R.id.tvTimeOff:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog
                        .OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int
                            selectedMinute) {
                        String timeOff = selectedHour + ":" + selectedMinute;
                        tvTimeOFf.setText(timeOff);
                        PreferenceUtils.saveString(getActivity(),Const.KEY_TIME_OFF,timeOff);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Stop Time");
                mTimePicker.show();
                break;
            case R.id.tvTimeOn:
                Calendar mcurrent = Calendar.getInstance();
                int hour2 = mcurrent.get(Calendar.HOUR_OF_DAY);
                int minute2 = mcurrent.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog
                        .OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int
                            selectedMinute) {
                        String timeOn = selectedHour + ":" + selectedMinute;
                        tvTimeOn.setText(timeOn);
                        PreferenceUtils.saveString(getActivity(),Const.KEY_TIME_OFF,timeOn);
                    }
                }, hour2, minute2, false);
                timePickerDialog.setTitle("Start Time");
                timePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvPercentBattery.setText(i + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        PreferenceUtils.saveInt(getActivity(), Const.KEY_PERCENT_BATTERY, slkbBattery.getProgress());
    }
}
