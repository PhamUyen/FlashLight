package com.uyenpham.diploma.flashlight.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.utils.Const;

/**
 * Created by Ka on 2/4/2018.
 */

public class SettingPatternFlashActivity extends Activity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private SwitchCompat switchCall;
    private SwitchCompat switchSMS;
    private Contact contact;
    private App app;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvBack;
    private int type;
    private LinearLayout lnSecond;
    private ImageView imvProfile;
    private TextView tvTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setsound);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.imvBack).setOnClickListener(this);
        tvBack = findViewById(R.id.tvBack);
        switchCall = findViewById(R.id.switchCall);
        switchSMS = findViewById(R.id.switchSMS);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);
        lnSecond = findViewById(R.id.lnSecond);
        imvProfile = findViewById(R.id.imvProfile);
        tvTitle = findViewById(R.id.tvTitle);
        tvBack.setOnClickListener(this);
        switchSMS.setOnCheckedChangeListener(this);
        switchCall.setOnCheckedChangeListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Const.KEY_BUNDLE);
        type = bundle.getInt(Const.KEY_TYPE);

        if (Const.TYPE_CONTACT == type) {
            tvTitle.setText("SMS");
            tvBack.setText("Contact");
            tvNumber.setVisibility(View.VISIBLE);
            lnSecond.setVisibility(View.VISIBLE);
            contact = (Contact) bundle.getSerializable(Const.KEY_CONTACT);
            if (contact != null) {
                tvName.setText(contact.getName());
                tvNumber.setText(contact.getNumber());
                switchCall.setChecked(contact.isFlashCall()==1);
                switchSMS.setChecked(contact.isFlashSMS() ==1);
            }
        } else {
            tvTitle.setText("Notification");
            tvName.setText(bundle.getString(Const.KEY_NAME));
            imvProfile.setImageBitmap((Bitmap) bundle.getParcelable(Const.KEY_IMAGE));
            switchSMS.setChecked(bundle.getInt(Const.KEY_FLASH) ==1);
            tvBack.setText("Application");
            lnSecond.setVisibility(View.GONE);
            tvNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
            case R.id.imvBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        compoundButton.setChecked(b);
        int type = 1;
        if (b) {
            switch (compoundButton.getId()) {
                case R.id.switchCall:
                    type = Const.TYPE_PATERN_CALL;
                    break;
                case R.id.switchSMS:
                    type = Const.TYPE_PATERN_SMS;
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Const.KEY_TYPE, type);
            bundle.putSerializable(Const.KEY_CONTACT, contact);
            Intent intent = new Intent(SettingPatternFlashActivity.this, ChoosePatternActivity.class);
            intent.putExtra(Const.KEY_BUNDLE, bundle);
            startActivity(intent);
        }
    }
}
