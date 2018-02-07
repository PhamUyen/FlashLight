package com.uyenpham.diploma.flashlight.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import java.util.ArrayList;

/**
 * Created by Ka on 2/7/2018.
 */

public class SplashActivity extends AppCompatActivity {
    private DatabaseHelper mDatabae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CommonFuntions.hideActionBar(this);

        initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },4000);
    }

    private void initData() {
        mDatabae = FlashlightApplication.getInstance().getDatabase();
        saveListContact();
        saveListApp();
        if (PreferenceUtils.getBoolean(this, Const.KEY_FIRST_INSTALL, true)) {
            //save
            saveListDefautPattern();
            PreferenceUtils.saveBoolean(this, Const.KEY_FIRST_INSTALL, false);
        }
    }

    private void saveListContact() {
        ArrayList<Contact> listContact = CommonFuntions.link2ListContact(mDatabae.getAllContact()
                , CommonFuntions.getListContact(this));
        mDatabae.deleteAllContact();
        for (Contact contact : listContact) {
            mDatabae.insertContact(contact);
        }
    }

    private void saveListApp() {
        ArrayList<App> listApp = CommonFuntions.link2ListApp(mDatabae.getAllApp()
                , CommonFuntions.getListApp(this));
        mDatabae.deleteAllApp();
        for (App app : listApp) {
            mDatabae.insertApp(app);
        }
    }

    private void saveListDefautPattern() {
        ArrayList<FlashPatternt> list = new ArrayList<>();
        list.add(new FlashPatternt(1, "Call pattern1", "Thời lượng: liên tục", 0, Const.TYPE_PATERN_CALL));
        list.add(new FlashPatternt(2, "Call pattern2", "Thời lượng : 5 giây", 5 * 1000, Const.TYPE_PATERN_CALL));
        list.add(new FlashPatternt(3, "Notification pattern1", "Thời lượng : 2 giây",2 * 1000, Const.TYPE_PATERN_SMS));
        list.add(new FlashPatternt(4, "Notification pattern2", "Thời lượng : 4 giây",4 * 1000, Const.TYPE_PATERN_SMS));

        for(FlashPatternt patternt : list){
            mDatabae.insertPattern(patternt);
        }
    }
}
