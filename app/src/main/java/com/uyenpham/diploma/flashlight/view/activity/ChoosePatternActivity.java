package com.uyenpham.diploma.flashlight.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;

import static com.uyenpham.diploma.flashlight.view.fragment.SwitchFlashFragment.isFlash;

/**
 * Created by Ka on 2/4/2018.
 */

public class ChoosePatternActivity extends AppCompatActivity implements CompoundButton
        .OnCheckedChangeListener, View.OnClickListener {
    private RadioButton rbPattern1;
    private RadioButton rbPattern2;
    private ImageView imvPlay1;
    private ImageView imvPlay2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        initView();
    }

    private void initView() {
        rbPattern1 = findViewById(R.id.rbPattern1);
        rbPattern2 = findViewById(R.id.rbPattern2);
        imvPlay1 = findViewById(R.id.imvPlay1);
        imvPlay2 = findViewById(R.id.imvPlay2);

        rbPattern1.setOnCheckedChangeListener(this);
        rbPattern2.setOnCheckedChangeListener(this);
        imvPlay1.setOnClickListener(this);
        imvPlay2.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.rbPattern1:
                if (b) {
                    rbPattern1.setChecked(true);
                    rbPattern2.setChecked(false);
                } else {
                    rbPattern2.setChecked(true);
                    rbPattern1.setChecked(false);
                }
                break;
            case R.id.rbPattern2:
                if (b) {
                    rbPattern2.setChecked(true);
                    rbPattern1.setChecked(false);
                } else {
                    rbPattern1.setChecked(true);
                    rbPattern2.setChecked(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imvPlay1:
                if(isFlash){
                    FlashUtil.stopFlickerFlash();
                    imvPlay1.setImageResource(R.mipmap.play);
                }else {
                    tryFlash(0,imvPlay1);
                }
                break;
            case R.id.imvPlay2:
                if(isFlash){
                    FlashUtil.stopFlickerFlash();
                    imvPlay1.setImageResource(R.mipmap.play);
                }else {
                    tryFlash(5*1000,imvPlay2);
                }
                break;
        }
    }
    private void tryFlash(int time,final ImageView imv){
        setTimeOff(time,imv);
        FlashUtil.flickerFlash(this);
        imv.setImageResource(R.mipmap.pause);
        isFlash =true;
    }
    private  void setTimeOff(int time, final ImageView imv) {
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
}
