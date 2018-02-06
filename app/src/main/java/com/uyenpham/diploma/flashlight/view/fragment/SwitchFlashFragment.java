package com.uyenpham.diploma.flashlight.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.SeekBar;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;
import com.uyenpham.diploma.flashlight.view.activity.MapActivity;
import com.uyenpham.diploma.flashlight.view.customview.CustomTimeDialog;
import com.uyenpham.diploma.flashlight.view.customview.TextSeekbarView;

import static android.content.Context.SENSOR_SERVICE;
import static com.uyenpham.diploma.flashlight.utils.Const.KEY_TIME_DELAY;

/**
 * Created by Ka on 2/3/2018.
 */

public class SwitchFlashFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View
        .OnClickListener, SensorEventListener {
    private SeekBar seekBar;
    private TextSeekbarView textSeekbarView;
    private TextView tvCoordinates;
    private TextView tvNotiNoCompass;
    private ImageView imvCompass;
    private int progress;
    private int blinkDelay = 0;
    private ImageView btnSwitch;
    public static boolean isFlash = false;
    // record the compass picture angle turned
    private float currentDegree = 0f;
    // device sensor manager
    private SensorManager mSensorManager;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_main, null);

        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    private void initView(View view) {
        seekBar = view.findViewById(R.id.seekbar);
        textSeekbarView = view.findViewById(R.id.seekbarText);
        btnSwitch = view.findViewById(R.id.button_switch);
        tvCoordinates = view.findViewById(R.id.tvCoordinates);
        tvNotiNoCompass = view.findViewById(R.id.tvNotiNoCompass);
        imvCompass = view.findViewById(R.id.imvCompass);
        view.findViewById(R.id.rltCompass).setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);
        btnSwitch.setOnClickListener(this);
        view.findViewById(R.id.btnSetting).setOnClickListener(this);
    }

    private void initData() {
        checkCompass();
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
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
        blinkDelay = 1000 / convertProgress(progress);
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
                if (isFlash) {
                    FlashUtil.stopFlickerFlash();
                    btnSwitch.setImageResource(R.mipmap.btn_off);
                } else {
                    turnOnFlash(blinkDelay);
                }
                break;
            case R.id.btnSetting:
                final CustomTimeDialog customDialog = new CustomTimeDialog(getActivity());
                customDialog.setListener(new CustomTimeDialog.OnOKClickListener() {
                    @Override
                    public void onClick(String tag) {
                        Toast.makeText(getActivity(), tag, Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveInt(getActivity(), KEY_TIME_DELAY, convertTimeDelay(tag));
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

    private void turnOnFlash(int delay) {
        btnSwitch.setImageResource(R.mipmap.btn_on);
        if (blinkDelay == 0) {
            FlashUtil.flashOn();
        } else {
            FlashUtil.setBlinkDelay(delay);
            FlashUtil.flickerFlash(getActivity());
        }
        setTimeOff();
    }

    private int convertTimeDelay(String timeStr) {
        int time;
        if (compareString(timeStr, "10s")) {
            time = 10 * 1000;
        } else if (compareString(timeStr, "20s")) {
            time = 20 * 1000;
        } else if (compareString(timeStr, "1m")) {
            time = 60 * 1000;
        } else if (compareString(timeStr, "3m")) {
            time = 3 * 60 * 1000;
        } else if (compareString(timeStr, "5m")) {
            time = 5 * 60 * 1000;
        } else if (compareString(timeStr, "10m")) {
            time = 10 * 60 * 1000;
        } else if (compareString(timeStr, "30m")) {
            time = 30 * 60 * 1000;
        } else {
            time = 0;
        }
        return time;
    }

    private boolean compareString(String str1, String str2) {
        return str1.equalsIgnoreCase(str2);
    }

    private void setTimeOff() {
        int delay = PreferenceUtils.getInt(getActivity(), KEY_TIME_DELAY, 0);
        if (delay != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FlashUtil.stopFlickerFlash();
                    btnSwitch.setImageResource(R.mipmap.btn_off);
                }
            }, delay);
        }
    }

    private void checkCompass() {
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
            // This device does not have a compass, turn off the compass feature
            tvNotiNoCompass.setVisibility(View.VISIBLE);
        } else {
            tvNotiNoCompass.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);
        tvCoordinates.setText(getDerection(degree)+ " "+(int)(180-degree) +(char) 0x00B0);
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        imvCompass.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private String getDerection(float de){
        String derection ="";
        if(de ==0){
            derection = "N";
        }else if(0<de && de <90){
            derection = "NE";
        }else if(de ==90){
            derection ="E";
        }else if(90<de && de <180){
            derection = "ES";
        }else if(de == 180){
            derection ="S";
        }else if(180 <de && de <270){
            derection ="SW";
        }else if(de == 270){
            derection ="W";
        }else {
            derection ="NW";
        }
        return derection;
    }
}
