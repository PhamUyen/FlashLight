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

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;

/**
 * Created by Ka on 2/4/2018.
 */

public class SettingPatternFlashActivity extends Activity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private SwitchCompat switchCall;
    private SwitchCompat switchSMS;
    private Contact contact;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvBack;
    private int type;
    private App app;
    private int patternApp;
    private DatabaseHelper databaseHelper;
    private String idApp;
    private LinearLayout lnSecond;
    private ImageView imvProfile;
    private TextView tvTitle;
    private TextView tvNextCall;
    private TextView tvNextSMS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setsound);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.imvBack).setOnClickListener(this);
        findViewById(R.id.tvBack).setOnClickListener(this);
        tvBack = findViewById(R.id.tvBack);
        switchCall = findViewById(R.id.switchCall);
        switchSMS = findViewById(R.id.switchSMS);
        tvName = findViewById(R.id.tvName);
        tvNumber = findViewById(R.id.tvNumber);
        lnSecond = findViewById(R.id.lnSecond);
        imvProfile = findViewById(R.id.imvProfile);
        tvTitle = findViewById(R.id.tvTitle);
        tvNextCall = findViewById(R.id.tvNextCall);
        tvNextSMS = findViewById(R.id.tvNextSMS);
        tvBack.setOnClickListener(this);
    }

    private void initData() {
        databaseHelper = FlashlightApplication.getInstance().getDatabase();
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
                switchCall.setChecked(contact.isFlashCall() == 1);
                switchSMS.setChecked(contact.isFlashSMS() == 1);
                if(contact.isFlashSMS() == 1){
                    tvNextSMS.setText(databaseHelper.getPattertByID(contact.getPatternSMS()).getName());
                }
                if(contact.isFlashCall() == 1){
                    tvNextCall.setText(databaseHelper.getPattertByID(contact.getPatternCall()).getName());
                }
            }
        } else {
            tvTitle.setText("Notification");
            tvName.setText(bundle.getString(Const.KEY_NAME));
            imvProfile.setImageBitmap((Bitmap) bundle.getParcelable(Const.KEY_IMAGE));
            switchSMS.setChecked(bundle.getInt(Const.KEY_FLASH) == 1);
            patternApp = bundle.getInt(Const.KEY_ID_PATTERN);
            idApp = bundle.getString(Const.KEY_ID_APP);
            app = new App(idApp, bundle.getString(Const.KEY_NAME), (Bitmap) bundle.getParcelable
                    (Const.KEY_IMAGE), bundle.getInt(Const.KEY_FLASH), 3);
            tvBack.setText("Application");
            lnSecond.setVisibility(View.GONE);
            tvNumber.setVisibility(View.GONE);
            if(bundle.getInt(Const.KEY_FLASH) == 1){
                FlashPatternt patternt = databaseHelper.getPattertByID(patternApp);
                tvNextSMS.setText(patternt.getName());
            }
        }
        switchSMS.setOnCheckedChangeListener(this);
        switchCall.setOnCheckedChangeListener(this);
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
        int typeContact = 1;
        int idPattern = 0;

        if (b) {
            Bundle bundle = new Bundle();
            if (Const.TYPE_APP == type) {
                typeContact = Const.TYPE_PATERN_SMS;
                idPattern = patternApp;
                bundle.putString(Const.KEY_ID_OBJ, idApp);
                app.setFlash(1);
                CommonFuntions.resetListApp(app, databaseHelper);
            } else {
                bundle.putString(Const.KEY_ID_OBJ, contact.getId());
                switch (compoundButton.getId()) {
                    case R.id.switchCall:
                        typeContact = Const.TYPE_PATERN_CALL;
                        idPattern = contact.getPatternCall();
                        contact.setFlashCall(1);
                        CommonFuntions.resetListContact(contact, databaseHelper);
                        break;
                    case R.id.switchSMS:
                        typeContact = Const.TYPE_PATERN_SMS;
                        idPattern = contact.getPatternSMS();
                        contact.setFlashSMS(1);
                        CommonFuntions.resetListContact(contact, databaseHelper);
                        break;
                }
            }

            bundle.putInt(Const.KEY_TYPE_PATTERN, typeContact);
            bundle.putInt(Const.KEY_TYPE, type);
            bundle.putInt(Const.KEY_ID_PATTERN, idPattern);
            Intent intent = new Intent(SettingPatternFlashActivity.this, ChoosePatternActivity
                    .class);
            intent.putExtra(Const.KEY_BUNDLE, bundle);
            startActivityForResult(intent, 9999);
        } else {
            if (type == Const.TYPE_APP) {
                app.setFlash(0);
                CommonFuntions.resetListApp(app, databaseHelper);
            } else {
                switch (compoundButton.getId()) {
                    case R.id.switchCall:
                        contact.setFlashCall(0);
                        CommonFuntions.resetListContact(contact, databaseHelper);
                        break;
                    case R.id.switchSMS:
                        contact.setFlashSMS(0);
                        CommonFuntions.resetListContact(contact, databaseHelper);
                        break;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999 && resultCode == RESULT_OK && data != null) {
            int id = data.getIntExtra(Const.KEY_ID_PATTERN, 1);
            FlashPatternt patternt = databaseHelper.getPattertByID(id);
            if (patternt.getTpye() == Const.TYPE_PATERN_CALL) {
                tvNextCall.setText(patternt.getName());
            } else {
                tvNextSMS.setText(patternt.getName());
            }
        }
    }
}
